package io.graphql.configuration;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.graphql.GraphqlGatewayConfiguration;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;

public class ServerFactory extends GraphqlGatewayConfiguration {
  
  @JsonProperty
  private Integer port = getServerPort();

  @JsonProperty
  private String bindAddress = getServerBindAddress();

  @Nullable
  public Integer getPort() {
    return port;
  }

  @Nullable
  public void setPort(@Nullable Integer port) {
    this.port = port;
  }

  @Nullable
  public String getBindAddress() {
    return bindAddress;
  }

  public void setBindAddress(@Nullable String bindAddress) {
    this.bindAddress = bindAddress;
  }

  @JsonIgnore
  public Undertow build(PathHandler handler) {
    final Undertow.Builder builder = Undertow.builder();
        builder.addHttpListener(port, bindAddress);
        builder.setHandler(handler);
        return builder.build();
  }
}
