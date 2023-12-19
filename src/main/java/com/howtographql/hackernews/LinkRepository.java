package com.howtographql.hackernews;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class LinkRepository {

  private final MongoCollection<Document> links;

  public LinkRepository(MongoCollection<Document> links) { this.links = links; }

  public Link findById(String id) {
    Document doc = links.find(eq("_id", new ObjectId(id))).first();
    return link(doc);
  }

  public List<Link> getAllLinks() {
    List<Link> allLinks = new ArrayList<>();
    for (Document doc : links.find()) {
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
}
