package com.howtographql.hackernews;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

// This will handle all the queries.
public class Query implements GraphQLRootResolver {

  private final LinkRepository linkRepository;

  public Query(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  public List<Link> allLinks() {
    return linkRepository.getAllLinks();
  }
}