package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.pojos.AuthData;
import com.howtographql.hackernews.pojos.Link;
import com.howtographql.hackernews.pojos.SigninPayload;
import com.howtographql.hackernews.pojos.User;
import com.howtographql.hackernews.pojos.Vote;
import com.howtographql.hackernews.repositories.LinkRepository;
import com.howtographql.hackernews.repositories.UserRepository;
import com.howtographql.hackernews.repositories.VoteRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import java.time.Instant;
import java.time.ZoneOffset;

public class Mutation implements GraphQLRootResolver {

  private final LinkRepository linkRepository;
  private final UserRepository userRepository;
  private final VoteRepository voteRepository;

  public Mutation(LinkRepository linkRepository, UserRepository userRepository,
                  VoteRepository voteRepository) {
    this.linkRepository = linkRepository;
    this.userRepository = userRepository;
    this.voteRepository = voteRepository;
  }

  // Creates a link and insert it to links repository.
  public Link createLink(String url, String description,
                         DataFetchingEnvironment env) {
    // Get the context and get the userId.
    AuthContext context = env.getContext();
    Link newLink = new Link(url, description, context.getUser().getId());
    linkRepository.saveLink(newLink);
    return newLink;
  }

  public User createUser(String name, AuthData auth) {
    User newUser = new User(name, auth.getEmail(), auth.getPassword());
    return userRepository.saveUser(newUser);
  }

  public SigninPayload signinUser(AuthData auth) throws IllegalAccessError {
    // Get email of the user's email that is stored in the SigninPayload.
    User user = userRepository.findByEmail(auth.getEmail());
    if (user.getPassword().equals(auth.getPassword())) {
      return new SigninPayload(user.getId(), user);
    }
    throw new GraphQLException("Invalid credentials");
  }

  public Vote createVote(String linkId, String userId) {
    return voteRepository.saveVote(
        new Vote(Instant.now().atZone(ZoneOffset.UTC), userId, linkId));
  }
}
