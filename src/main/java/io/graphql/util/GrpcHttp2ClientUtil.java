package io.graphql.util;

import io.admin.timesheet.GetUserById;
import io.admin.timesheet.UserLoginServiceGrpc;
import io.admin.timesheet.UserResponse;
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

  public void callGetByUserLoginId(Long id, StreamObserver<UserResponse> responseOberver) {
    GetUserById userById = GetUserById.newBuilder().setId(id).build();
    asyncStub.getUserById(userById, responseOberver);
  }
}