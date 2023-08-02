package io.graphql.configuration;

import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.graphql.GraphqlGatewayConfiguration;

public class TimeclockServerFactory extends GraphqlGatewayConfiguration {
  
  @JsonProperty
  private Integer port = getTimeclockPort();

  @JsonProperty
  private String bindAddress = getTimeclockBindAddress();

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  @Nullable
  public String getBindAddress() {
    return bindAddress;
  }

  public void setBindAddress(@Nullable String bindAddress) {
    this.bindAddress = bindAddress;
  }

}
