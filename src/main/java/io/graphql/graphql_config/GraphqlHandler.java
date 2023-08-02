package io.graphql.graphql_config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Deque;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import io.graphql.sample.config.GraphqlSampleConfiguration;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;

public class GraphqlHandler implements HttpHandler {

  private Logger logger = LoggerFactory.getLogger(GraphqlHandler.class);

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    if (exchange.isInIoThread()) {
      exchange.dispatch(this);
      return;
    }
    if (Methods.POST.equals(exchange.getRequestMethod())) {
      exchange.getRequestReceiver().receiveFullBytes((exch, body) -> {
        if (body == null || body.length == 0) {
          badRequest(exchange, "Missing request body");
        }
        try {
          final GraphqlRequest request = parseJson(body, DefaultGraphqlRequest.class);
          if (request.query() == null) {
            badRequest(exchange, "query is missing");
          }
          executeResult(exch, request);
        } catch (JsonProcessingException ex) {
          ex.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }  
  }

  private void executeResult(HttpServerExchange exchange, GraphqlRequest request) throws IOException {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
    GraphqlSchema graphqlSchema = GraphqlSchema.newInstance();
    GraphQL myGraphqlSchema = graphqlSchema.getGraphQL();
    final ExecutionInput.Builder builder = ExecutionInput.newExecutionInput()
        .query(request.query())
        .operationName(request.operationName());
    final ExecutionInput executionInput = builder.build();
    ExecutionResult executionResult = myGraphqlSchema.execute(executionInput);
    Object data = executionResult.toSpecification();
    sendJson(exchange, data);
  }

  private void sendJson(HttpServerExchange exchange, Object data) throws JsonGenerationException, JsonMappingException, IOException {
    exchange.startBlocking();
    OutputStream outputStream = exchange.getOutputStream();
    writeToJson(new ObjectMapper(), outputStream, data);
  }

  private void writeToJson(ObjectMapper objectMapper, OutputStream outputStream, Object data) throws JsonGenerationException, JsonMappingException, IOException {
    objectMapper.writeValue(outputStream, data);
  }

  private static void badRequest(HttpServerExchange exchange, String message) {
    exchange.setStatusCode(StatusCodes.BAD_REQUEST);
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
    exchange.getResponseSender().send(message);
  }

  private <T> T parseJson(byte [] content, Class<T> valueType) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(content, valueType);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      throw e;
    }
  }
  
  @Nullable
  private static <T> T firstOrNull(@Nullable Deque<T> query) {
    if (query == null) {
      return null;
    }
    return query.getFirst();
  }
}
