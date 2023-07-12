package io.graphql.dao;

import java.util.Map;

import io.graphql.core.Greeting;

public class GreetingDaoRepository extends AbstractGreetingRepository {

    public GreetingDaoRepository(Map<Integer, Greeting> map) {
        super(map);
    }
    
}
