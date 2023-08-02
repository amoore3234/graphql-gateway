package io.graphql;

import java.io.InputStream;

import com.github.jsixface.YamlConfig;

public class GraphqlGatewayConfiguration {

  private String file = "config.yml";

  private String serverBindAddress = load(getFile()).getString("server.bindAddress");

  private Integer serverPort = load(getFile()).getInt("server.port");

  private String timeclockBindAddress = load(getFile()).getString("timeclock.bindAddress");

  private Integer timeclockPort = load(getFile()).getInt("timeclock.port");

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getServerBindAddress() {
    return serverBindAddress;
  }

  public void setServerBindAddress(String serverBindAddress) {
    this.serverBindAddress = serverBindAddress;
  }

  public Integer getServerPort() {
    return serverPort;
  }

  public void setServerPort(Integer serverPort) {
    this.serverPort = serverPort;
  }

  public String getTimeclockBindAddress() {
    return timeclockBindAddress;
  }

  public void setTimeclockBindAddress(String timeclockBindAddress) {
    this.timeclockBindAddress = timeclockBindAddress;
  }

  public Integer getTimeclockPort() {
    return timeclockPort;
  }

  public void setTimeclockPort(Integer timeclockPort) {
    this.timeclockPort = timeclockPort;
  }

  public YamlConfig load(String config) {
    InputStream resource = getClass().getClassLoader().getResourceAsStream(config);
    YamlConfig configuration = YamlConfig.load(resource);
    return configuration;
  }

}
