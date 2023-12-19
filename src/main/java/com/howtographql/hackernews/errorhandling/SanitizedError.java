package com.howtographql.hackernews.errorhandling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ExceptionWhileDataFetching;

public class SanitizedError extends ExceptionWhileDataFetching {

  public SanitizedError(ExceptionWhileDataFetching inner) {
    super(inner.getException());
  }

  @Override
  @JsonIgnore
  // JSON ingore to prevent exposing sensitive error information.
  public Throwable getException() {
    return super.getException();
  }
}
