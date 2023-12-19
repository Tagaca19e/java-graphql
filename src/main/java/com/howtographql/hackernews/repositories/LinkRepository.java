package com.howtographql.hackernews.repositories;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.LinkFilter;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class LinkRepository {

  private final MongoCollection<Document> links;

  public LinkRepository(MongoCollection<Document> links) { this.links = links; }

  public Link findById(String id) {
    Document doc = links.find(eq("_id", new ObjectId(id))).first();
    return link(doc);
  }

  public List<Link> getAllLinks(LinkFilter filter) {
    // Optional since filter could be nullable.
    Optional<Bson> mongoFilter =
        Optional.ofNullable(filter).map(this::buildFilter);

    List<Link> allLinks = new ArrayList<>();
    for (Document doc : mongoFilter.map(links::find).orElseGet(links::find)) {
      allLinks.add(link(doc));
    }
    return allLinks;
  }

  // Saves link to the database.
  public void saveLink(Link link) {
    Document doc = new Document();
    doc.append("url", link.getUrl());
    doc.append("description", link.getDescription());
    doc.append("postedBy", link.getUserId());
    links.insertOne(doc);
  }

  // Converts Document class to a Link class.
  private Link link(Document doc) {
    return new Link(doc.get("_id").toString(), doc.getString("url"),
                    doc.getString("description"), doc.getString("postedBy"));
  }

  // Builds a Bson from a LinkFilter.
  private Bson buildFilter(LinkFilter filter) {
    String descriptionPattern = filter.getDescriptionContains();
    String urlPattern = filter.getUrlContains();
    Bson descriptionCondition = null;
    Bson urlCondition = null;

    if (descriptionPattern != null && !descriptionPattern.isEmpty()) {
      descriptionCondition =
          regex("description", ".*" + descriptionPattern + ".*", "i");
    }

    if (urlPattern != null && !urlPattern.isEmpty()) {
      urlCondition = regex("url", ".*" + urlPattern + ".*", "i");
    }

    if (descriptionCondition != null && urlCondition != null) {
      return and(descriptionCondition, urlCondition);
    }
    return descriptionCondition != null ? descriptionCondition : urlCondition;
  }
}
