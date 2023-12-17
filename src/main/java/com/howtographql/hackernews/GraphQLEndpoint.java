package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
// Extends to override interfaces.
public class GraphQLEndpoint extends SimpleGraphQLServlet {

  private static final LinkRepository linkRepository;

  static {
    // TODO(etagaca): Connect this to a real database.
    MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
    linkRepository = new LinkRepository(mongo.getCollection("links"));
  }

  public GraphQLEndpoint() { super(buildSchema()); }

  private static GraphQLSchema buildSchema() {
    return SchemaParser.newParser()
        .file("schema.graphqls")
        .resolvers(new Query(linkRepository), new Mutation(linkRepository))
        .build()
        .makeExecutableSchema();
  }
}
