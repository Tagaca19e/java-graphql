package com.howtographql.hackernews;

import com.howtographql.hackernews.pojos.User;
import graphql.servlet.GraphQLContext;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthContext extends GraphQLContext {

  private final User user;

  // Set user in context to get user from context.
  public AuthContext(User user, Optional<HttpServletRequest> request,
                     Optional<HttpServletResponse> response) {
    super(request, response);
    this.user = user;
  }

  public User getUser() { return user; }
}
