package io.graphql.sample.dao;

import java.util.Map;

import io.graphql.sample.greeting.Greeting;

public class GreetingDaoRepository extends AbstractGreetingRepository {

    public GreetingDaoRepository(Map<Integer, Greeting> map) {
        super(map);
    }
    
}
