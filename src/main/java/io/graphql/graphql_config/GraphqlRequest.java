package io.graphql.graphql_config;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

import java.util.Map;

public interface GraphqlRequest {
    
    static GraphqlRequest of(String query, @Nullable String operationName, @Nullable Map<String, Object> variables) {
        requireNonNull(query, "query");
        if (query.isEmpty()) {
            throw new IllegalArgumentException("query is empty");
        }
        return new DefaultGraphqlRequest(query, operationName, null);
    }

    String query();

    @Nullable
    String operationName();

    @Nullable
    Map<String, Object> variables();
}
