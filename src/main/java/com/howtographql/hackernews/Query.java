package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.LinkFilter;
import com.howtographql.hackernews.repositories.LinkRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;

// This will handle all the queries.
public class Query {

  private final LinkRepository linkRepository;

  public Query(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  @GraphQLQuery
  public List<Link>
  allLinks(LinkFilter filter,
           @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
           @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
    return linkRepository.getAllLinks(filter, skip.intValue(),
                                      first.intValue());
  }
}
