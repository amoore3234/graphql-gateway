package io.graphql.sample.resolvers;

import java.util.Collection;

import graphql.schema.DataFetcher;
import io.graphql.sample.dao.GreetingRepository;
import io.graphql.sample.greeting.Greeting;

public class GreetingsDataFetcher {
  
  private GreetingRepository repository;

  public GreetingsDataFetcher(GreetingRepository repository) {
    this.repository = repository;
  }

  public DataFetcher<Collection<Greeting>> getGreetings() {
    return dataFetchingEnvironment -> {
      return repository.findGreetings();
    };
  }

  public DataFetcher<Greeting> findGreetingById() {
    return dataFetchingEnvironment -> {
      Long id = dataFetchingEnvironment.getArgument("id");
      return repository.findGreetingById(id);
    };
  }
}
