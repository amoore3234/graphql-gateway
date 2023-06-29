package io.graphql.sample.resolvers;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.schema.DataFetcher;
import io.graphql.sample.dao.GreetingRepository;
import io.graphql.sample.greeting.Greeting;

public class GreetingsMutation {
  
  private GreetingRepository repository;
  private final ObjectMapper objectMapper;

  public GreetingsMutation(GreetingRepository repository) {
    this.repository = repository;
    this.objectMapper = new ObjectMapper();
  }

  public DataFetcher<Greeting> createGreeting() {
    return dataFetchingEnvironment -> {
      Object greetingArg = dataFetchingEnvironment.getArgument("create");
      Greeting greeting = objectMapper.convertValue(greetingArg, Greeting.class);
      return repository.createGreeting(greeting);
    };
  }

  public DataFetcher<String> deleteGreeting() {
    return dataFetchingEnvironment -> {
      Long id = dataFetchingEnvironment.getArgument("id");
      final Greeting deletedGreeting = repository.findGreetingById(id);
      repository.deleteGreeting(id);
      return "The"  + " '" + deletedGreeting.getGreeting() + "' "
          + "greeting was deleted with an id of " + deletedGreeting.getId();
    };
  }

  public DataFetcher<Greeting> updateGreeting() {
    return dataFetchingEnvironment -> {
      Long id = dataFetchingEnvironment.getArgument("id");
      Object newGreetingArg = dataFetchingEnvironment.getArgument("update");
      Greeting newGreeting = objectMapper.convertValue(newGreetingArg, Greeting.class);
      return repository.updateGreeting(id, newGreeting);
    };
  }
}
