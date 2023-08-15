package io.graphql.resolvers;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.admin.timesheet.UserResponse;
import io.graphql.util.GrpcHttp2ClientUtil;
import io.grpc.stub.StreamObserver;

public class MutationDeleteUser implements DataFetcher<CompletableFuture<UserResponse>> {

  private final Logger log = LoggerFactory.getLogger(MutationDeleteUser.class);

  private GrpcHttp2ClientUtil http2ClientUtil;
  private UserResponse userLoginResponse = UserResponse.getDefaultInstance();

  public MutationDeleteUser(GrpcHttp2ClientUtil http2ClientUtil) {
    this.http2ClientUtil = http2ClientUtil;
  }

  @Override
  public CompletableFuture<UserResponse> get(DataFetchingEnvironment environment) throws Exception {
    final Long id = environment.getArgument("id");
    final CompletableFuture<UserResponse> completableFuture = new CompletableFuture<>();
    final StreamObserver<UserResponse> responseObserver = new StreamObserver<UserResponse>() {

      @Override
      public void onNext(UserResponse value) {
          userLoginResponse = UserResponse.newBuilder()
            .setId(value.getId())
            .setEmail(value.getEmail())
            .setPassword(value.getPassword())
            .build();
      }

      @Override
      public void onError(Throwable t) {
        log.error("Unable to retrieve user", t);
      }

      @Override
      public void onCompleted() {
        completableFuture.complete(userLoginResponse);
      }
    };

    http2ClientUtil.callDeleteUser(id, responseObserver);
    return completableFuture;
  }
}
