package io.graphql.sample.config;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.graphql.sample.dao.GreetingDaoRepository;
import io.graphql.sample.dao.GreetingRepository;
import io.graphql.sample.greeting.Greeting;
import io.graphql.sample.resolvers.GreetingsDataFetcher;
import io.graphql.sample.resolvers.GreetingsMutation;

public class GraphqlSampleConfiguration {
  
  @Nullable
  private Map<Integer, Greeting> map;
  private GraphQL graphQL;

  protected GraphqlSampleConfiguration() {
    map = new HashMap<Integer, Greeting>();
    greetings();
    graphQL = GraphQL.newGraphQL(createSchema()).build();
  }

  public GraphQL getGraphQL() {
    return graphQL;
  }

  private Map<Integer, Greeting> greetings() {
    final Greeting greetingOne = Greeting.newInstance();
    greetingOne.setId(1L);
    greetingOne.setGreeting("Hello!");
    final Greeting greetingTwo = Greeting.newInstance();
    greetingTwo.setId(2L);
    greetingTwo.setGreeting("Hi!");
    final Greeting greetingThree = Greeting.newInstance();
    greetingThree.setId(3L);
    greetingThree.setGreeting("How are you doing today!");
    map.put(1, greetingOne);
    map.put(2, greetingTwo);
    map.put(3, greetingThree);
    return map;
  }

  private GraphQLSchema createSchema() {
    final GreetingRepository greetingRepository = new GreetingDaoRepository(map);
    final GreetingsDataFetcher greetingsDataFetcher = new GreetingsDataFetcher(greetingRepository);
    final GreetingsMutation greetingsMutation = new GreetingsMutation(greetingRepository);
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(getSchemaFile());

    RuntimeWiring runtimeWiring =
      newRuntimeWiring()
        .scalar(Scalars.GraphQLLong)
        .type("Query", builder -> builder.dataFetcher("findGreetingById", greetingsDataFetcher.findGreetingById()))
        .type("Query", builder -> builder.dataFetcher("getGreetings", greetingsDataFetcher.getGreetings()))
        .type("Mutation", builder -> builder.dataFetcher("createGreeting", greetingsMutation.createGreeting()))
        .type("Mutation", builder -> builder.dataFetcher("deleteGreeting", greetingsMutation.deleteGreeting()))
        .type("Mutation", builder -> builder.dataFetcher("updateGreeting", greetingsMutation.updateGreeting()))
        .build();
    
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private Reader getSchemaFile() {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("greeting.graphql");
    return new InputStreamReader(stream);
  }

  public static GraphqlSampleConfiguration newInstance() {
    return new GraphqlSampleConfiguration();
  }

}
