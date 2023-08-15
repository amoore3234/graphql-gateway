package io.graphql.util;

import io.admin.timesheet.CreateUser;
import io.admin.timesheet.GetUserById;
import io.admin.timesheet.UpdateUser;
import io.admin.timesheet.UserLoginServiceGrpc;
import io.admin.timesheet.UserResponse;
import io.graphql.core.UserInput;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcHttp2ClientUtil {
  
  private final ManagedChannel channel;
  private final UserLoginServiceGrpc.UserLoginServiceStub asyncStub;

  public GrpcHttp2ClientUtil(String host, Integer port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
  }

  public GrpcHttp2ClientUtil(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    asyncStub = UserLoginServiceGrpc.newStub(channel);
  }

  public void callGetByUserLoginId(Long id, StreamObserver<UserResponse> responseObserver) {
    GetUserById userById = GetUserById.newBuilder().setId(id).build();
    asyncStub.getUserById(userById, responseObserver);
  }

  public void callCreateUser(UserInput user, StreamObserver<UserResponse> responseObserver) {
    CreateUser createUser = CreateUser.newBuilder()
        .setEmail(user.getEmail())
        .setPassword(user.getPassword())
        .build();
    asyncStub.createUser(createUser, responseObserver);
  }

  public void callUpdateUser(Long id, UserInput user,
      StreamObserver<UserResponse> responseObserver) {
    CreateUser createUser = CreateUser.newBuilder()
        .setEmail(user.getEmail())
        .setPassword(user.getPassword())
        .build();
    UpdateUser updateUser = UpdateUser.newBuilder()
        .setId(id)
        .setRequest(createUser)
        .build();
    asyncStub.updateUser(updateUser, responseObserver);
  }

  public void callDeleteUser(Long id, StreamObserver<UserResponse> responseObserver) {
    GetUserById userById = GetUserById.newBuilder().setId(id).build();
    asyncStub.deleteUserById(userById, responseObserver);
  }
}
