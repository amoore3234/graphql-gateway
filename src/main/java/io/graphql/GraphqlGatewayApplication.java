package io.graphql;

import javax.servlet.ServletException;

import io.graphql.graphql_config.GraphqlHandler;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.ResponseCodeHandler;

/**
 * Hello world!
 *
 */
public class GraphqlGatewayApplication {

  private static final int PORT = 8080;
  public static void main( String[] args ) throws ServletException {
    final GraphqlGatewayApplication app = new GraphqlGatewayApplication();

    final 
    PathHandler path = Handlers.path(Handlers.path(ResponseCodeHandler.HANDLE_404))
        .addPrefixPath("/graphql", new GraphqlHandler());
  
  }
}
