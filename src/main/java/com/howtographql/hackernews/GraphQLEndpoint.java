package com.howtographql.hackernews;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
// Extends to override interfaces.
public class GraphQLEndpoint extends SimpleGraphQLServlet {

  private static final LinkRepository linkRepository;

  static {
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase mongo = mongoClient.getDatabase("hackernews");
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
