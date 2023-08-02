package io.graphql.resolvers;

import io.admin.timesheet.UserResponse;
import io.graphql.util.GrpcHttp2ClientUtil;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class UserLoginDataFetcher implements DataFetcher<CompletableFuture<UserResponse>>{

  private GrpcHttp2ClientUtil http2ClientUtil;
  private UserResponse userLoginResponse = UserResponse.getDefaultInstance();

  public UserLoginDataFetcher(GrpcHttp2ClientUtil http2ClientUtil) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onError'");
      }

      @Override
      public void onCompleted() {
        completableFuture.complete(userLoginResponse);
      }
    };

    http2ClientUtil.callGetByUserLoginId(id, responseObserver);
    return completableFuture;
  }
  
}
