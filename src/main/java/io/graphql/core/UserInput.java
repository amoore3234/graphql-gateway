package io.graphql.core;

public class UserInput {
  
  private String email;

  private String password;

  protected UserInput() {}
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public static UserInput newInstance() {
    return new UserInput();
  }

}
