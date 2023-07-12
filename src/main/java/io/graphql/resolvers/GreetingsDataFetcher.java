package io.graphql.resolvers;

import java.util.Collection;

import graphql.schema.DataFetcher;
import io.graphql.core.Greeting;
import io.graphql.dao.GreetingRepository;

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
}
