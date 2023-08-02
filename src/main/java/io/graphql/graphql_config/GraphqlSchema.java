package io.graphql.graphql_config;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.graphql.configuration.TimeclockServerFactory;
import io.graphql.resolvers.UserLoginDataFetcher;
import io.graphql.util.GrpcHttp2ClientUtil;

public class GraphqlSchema {
  
  final TimeclockServerFactory timeclockServerFactory;
  private GraphQL graphQL;

  protected GraphqlSchema() {
    timeclockServerFactory = new TimeclockServerFactory();
    graphQL = GraphQL.newGraphQL(createSchema()).build();
  }

  public GraphQL getGraphQL() {
    return graphQL;
  }

  private GraphQLSchema createSchema() {
    String timeclockHost = timeclockServerFactory.getBindAddress();
    Integer timeclockPort = timeclockServerFactory.getPort();
    GrpcHttp2ClientUtil grpcHttp2ClientUtil = new GrpcHttp2ClientUtil(timeclockHost, timeclockPort);
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(getSchemaFile());

    RuntimeWiring runtimeWiring =
      newRuntimeWiring()
        .scalar(ExtendedScalars.GraphQLLong)
        .scalar(ExtendedScalars.DateTime)
        .scalar(ExtendedScalars.Date)
        .type("Query", builder -> builder.dataFetcher("getUserById", new UserLoginDataFetcher(grpcHttp2ClientUtil)))
        .build();
    
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private Reader getSchemaFile() {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("timeclock.graphql");
    return new InputStreamReader(stream);
  }

  public static GraphqlSchema newInstance() {
    return new GraphqlSchema();
  }

}
