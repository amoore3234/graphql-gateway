package io.graphql;

import javax.servlet.ServletException;

import io.graphql.graphql_config.HttpServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

/**
 * Hello world!
 *
 */
public class GraphqlGatewayApplication {

  private static final int PORT = 8080;
  public static void main( String[] args ) throws ServletException {
    DeploymentInfo serlvet = Servlets.deployment()
        .setClassLoader(GraphqlGatewayApplication.class.getClassLoader())
        .setContextPath("/")
        .setDeploymentName("graphqlGateway")
        .addServlet(Servlets.servlet("HttpServlet", HttpServlet.class));
    DeploymentManager manager = Servlets.defaultContainer().addDeployment(serlvet);
    manager.deploy();
    PathHandler path = Handlers.path(Handlers.path(ResponseCodeHandler.HANDLE_404))
        .addPrefixPath("/graphql", manager.start());
    Undertow server = Undertow.builder()
        .addHttpListener(PORT, "localhost")
        .setHandler(path)
        .build();
    server.start();
  }
}
