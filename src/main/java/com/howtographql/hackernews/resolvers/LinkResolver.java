package com.howtographql.hackernews.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.User;
import com.howtographql.hackernews.repositories.UserRepository;

public class LinkResolver implements GraphQLResolver<Link> {

  private final UserRepository userRepository;

  public LinkResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Get user id.
  public User postedBy(Link link) {
    if (link.getUserId() == null) {
      return null;
    }

    return userRepository.findById(link.getUserId());
  }
}
