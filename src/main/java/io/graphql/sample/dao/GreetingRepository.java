package io.graphql.sample.dao;

import java.util.Collection;

import io.graphql.sample.greeting.Greeting;

public interface GreetingRepository {
  
  /**
   * Finds a greeting by id.
   */
  Greeting findGreetingById(Long id);

  /**
   * Creates a new greeting.
   */
  Greeting createGreeting(Greeting greeting);

  /**
   * Updates an existing greeting.
   */
  Greeting updateGreeting(Long id, Greeting greeting);

  /**
   * Generates a list of greetings.
   */
  Collection<Greeting> findGreetings();

  /**
   * Deletes a greeting.
   */
  void deleteGreeting(Long id);
}
