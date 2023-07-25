package io.graphql.dao;

import java.util.Collection;

import io.graphql.core.Greeting;

public interface GreetingRepository {

  Greeting findGreetingById(Long id);
  Greeting createGreeting(Greeting greeting);
  Greeting updateGreeting(Long id, Greeting greeting);
  Collection<Greeting> findGreetings();
  void deleteGreeting(Long id);
}
