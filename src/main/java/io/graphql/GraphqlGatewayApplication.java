package io.graphql;

import javax.servlet.ServletException;

import com.github.jsixface.YamlConfig;

import io.graphql.configuration.ServerFactory;
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

  private static Undertow server;
  private static ServerFactory serverFactory = new ServerFactory();
  public static void main( String[] args ) throws ServletException {

    PathHandler path = Handlers.path(Handlers.path(ResponseCodeHandler.HANDLE_404))
        .addPrefixPath("/graphql", new GraphqlHandler());
    
    server = serverFactory.build(path);
    server.start();
  }

}
