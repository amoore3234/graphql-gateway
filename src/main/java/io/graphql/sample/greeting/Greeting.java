package io.graphql.sample.greeting;

import javax.annotation.Nullable;

public class Greeting {

  private Long id;
  
  @Nullable
  private String greeting;

  protected Greeting() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Nullable
  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(@Nullable String greeting) {
    this.greeting = greeting;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((greeting == null) ? 0 : greeting.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Greeting other = (Greeting) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (greeting == null) {
      if (other.greeting != null)
        return false;
    } else if (!greeting.equals(other.greeting))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Greeting [id=" + id + ", greeting=" + greeting + "]";
  }

  public static Greeting newInstance() {
    return new Greeting();
  }

}
