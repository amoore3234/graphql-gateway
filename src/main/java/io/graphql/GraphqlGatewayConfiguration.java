package io.graphql;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jsixface.YamlConfig;

import io.graphql.configuration.ServerFactory;

public class GraphqlGatewayConfiguration {
  
  @JsonProperty("server")
  private ServerFactory server = new ServerFactory();

  public ServerFactory getServer() {
    return server;
  }

  public YamlConfig load(String config) {
    InputStream resource = getClass().getClassLoader().getResourceAsStream(config);
    YamlConfig configuration = YamlConfig.load(resource);
    return configuration;
  }
}
