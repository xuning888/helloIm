package com.github.xuning888.helloim.api.protobuf.message.v1;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: api/message/v1/message_service.proto")
public final class MessageServiceGrpc {

  private MessageServiceGrpc() {}

  public static final String SERVICE_NAME = "MessageService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest,
      com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> METHOD_PULL_OFFLINE_MSG =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MessageService", "PullOfflineMsg"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest,
      com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> METHOD_GET_LATEST_OFFLINE_MESSAGES =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MessageService", "GetLatestOfflineMessages"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest,
      com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> METHOD_CLEAN_OFFLINE_MESSAGE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest, com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MessageService", "CleanOfflineMessage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessageServiceStub newStub(io.grpc.Channel channel) {
    return new MessageServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MessageServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MessageServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MessageServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PULL_OFFLINE_MSG, responseObserver);
    }

    /**
     */
    public void getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_LATEST_OFFLINE_MESSAGES, responseObserver);
    }

    /**
     */
    public void cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CLEAN_OFFLINE_MESSAGE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PULL_OFFLINE_MSG,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest,
                com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>(
                  this, METHODID_PULL_OFFLINE_MSG)))
          .addMethod(
            METHOD_GET_LATEST_OFFLINE_MESSAGES,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest,
                com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>(
                  this, METHODID_GET_LATEST_OFFLINE_MESSAGES)))
          .addMethod(
            METHOD_CLEAN_OFFLINE_MESSAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest,
                com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse>(
                  this, METHODID_CLEAN_OFFLINE_MESSAGE)))
          .build();
    }
  }

  /**
   */
  public static final class MessageServiceStub extends io.grpc.stub.AbstractStub<MessageServiceStub> {
    private MessageServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceStub(channel, callOptions);
    }

    /**
     */
    public void pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PULL_OFFLINE_MSG, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_LATEST_OFFLINE_MESSAGES, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CLEAN_OFFLINE_MESSAGE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MessageServiceBlockingStub extends io.grpc.stub.AbstractStub<MessageServiceBlockingStub> {
    private MessageServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PULL_OFFLINE_MSG, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_LATEST_OFFLINE_MESSAGES, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CLEAN_OFFLINE_MESSAGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessageServiceFutureStub extends io.grpc.stub.AbstractStub<MessageServiceFutureStub> {
    private MessageServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessageServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessageServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> pullOfflineMsg(
        com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PULL_OFFLINE_MSG, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> getLatestOfflineMessages(
        com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_LATEST_OFFLINE_MESSAGES, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> cleanOfflineMessage(
        com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CLEAN_OFFLINE_MESSAGE, getCallOptions()), request);
    }
  }

  private static final int METHODID_PULL_OFFLINE_MSG = 0;
  private static final int METHODID_GET_LATEST_OFFLINE_MESSAGES = 1;
  private static final int METHODID_CLEAN_OFFLINE_MESSAGE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessageServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessageServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PULL_OFFLINE_MSG:
          serviceImpl.pullOfflineMsg((com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>) responseObserver);
          break;
        case METHODID_GET_LATEST_OFFLINE_MESSAGES:
          serviceImpl.getLatestOfflineMessages((com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>) responseObserver);
          break;
        case METHODID_CLEAN_OFFLINE_MESSAGE:
          serviceImpl.cleanOfflineMessage((com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class MessageServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.api.protobuf.message.v1.MessageServiceProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MessageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessageServiceDescriptorSupplier())
              .addMethod(METHOD_PULL_OFFLINE_MSG)
              .addMethod(METHOD_GET_LATEST_OFFLINE_MESSAGES)
              .addMethod(METHOD_CLEAN_OFFLINE_MESSAGE)
              .build();
        }
      }
    }
    return result;
  }
}
