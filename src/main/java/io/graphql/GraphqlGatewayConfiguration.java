package io.graphql;

import java.io.InputStream;

import com.github.jsixface.YamlConfig;

public class GraphqlGatewayConfiguration {

  private String file = "config.yml";

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public YamlConfig load(String config) {
    InputStream resource = getClass().getClassLoader().getResourceAsStream(config);
    YamlConfig configuration = YamlConfig.load(resource);
    return configuration;
  }

}
