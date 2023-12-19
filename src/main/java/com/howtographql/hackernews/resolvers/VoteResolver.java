package com.howtographql.hackernews.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.User;
import com.howtographql.hackernews.pojos.Vote;
import com.howtographql.hackernews.repositories.LinkRepository;
import com.howtographql.hackernews.repositories.UserRepository;

public class VoteResolver implements GraphQLResolver<Vote> {
  
  private final LinkRepository linkRepository;
  private final UserRepository userRepository;

  public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
    this.linkRepository = linkRepository;
    this.userRepository = userRepository;
  }

  public User user(Vote vote) {
    return userRepository.findById(vote.getUserId());
  }
  
  public Link link(Vote vote) {
    return linkRepository.findById(vote.getLinkId());
  }
}
