package com.howtographql.hackernews;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.LinkFilter;
import com.howtographql.hackernews.repositories.LinkRepository;

// This will handle all the queries.
public class Query implements GraphQLRootResolver {

  private final LinkRepository linkRepository;

  public Query(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  public List<Link> allLinks(LinkFilter filter) {
    return linkRepository.getAllLinks(filter);
  }
}
