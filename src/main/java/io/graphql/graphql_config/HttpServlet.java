package io.graphql.graphql_config;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;

public class HttpServlet extends GraphQLHttpServlet {

  @Override
  protected GraphQLConfiguration getConfiguration() {
    return GraphqlConfiguration.getInstance().getConfiguration();
  }
    
}
