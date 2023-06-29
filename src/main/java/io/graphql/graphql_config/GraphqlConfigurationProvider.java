package io.graphql.graphql_config;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class GraphqlConfigurationProvider {
  
  private GraphQL graphQL;

  protected GraphqlConfigurationProvider() {
    graphQL = GraphQL.newGraphQL(createSchema()).build();
  }

  public GraphQL getGraphQL() {
    return graphQL;
  }


  private GraphQLSchema createSchema() {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(getSchemaFile());

    RuntimeWiring runtimeWiring =
      newRuntimeWiring()
        .type(newTypeWiring("Query").dataFetcher("hello", new StaticDataFetcher("world")))
        .build();
    
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private Reader getSchemaFile() {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("schema.graphqls");
    return new InputStreamReader(stream);
  }

  public static GraphqlConfigurationProvider newInstance() {
    return new GraphqlConfigurationProvider();
  }

}
