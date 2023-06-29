package io.graphql.graphql_config;

import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

final class DefaultGraphqlRequest implements GraphqlRequest {

  @Nullable
  private String query;

  @Nullable
  private String operationName;

  @Nullable
  private Map<String, Object> variables;

  public DefaultGraphqlRequest() {}

  public DefaultGraphqlRequest(@Nullable String query, @Nullable String operationName,
      @Nullable Map<String, Object> variables) {
    this.query = query;
    this.operationName = operationName;
    this.variables = variables;
  }

  @JsonProperty
  @Override
  public String query() {
    return query != null ? query : "";
  }

  @JsonProperty
  public void query(@Nullable String query) {
    this.query = query;
  }

  @Nullable
  @JsonProperty
  @Override
  public String operationName() {
    return operationName;
  }

  @JsonProperty
  public void operationName(@Nullable String operationName) {
    this.operationName = operationName;
  }

  @Nullable
  @JsonProperty
  @Override
  public Map<String, Object> variables() {
    return variables;
  }

  @JsonProperty
  public void variables(@Nullable Map<String, Object> variables) {
    this.variables = variables;
  }
}
