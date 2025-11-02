package com.github.xuning888.helloim.message.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 消息服务接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: msg.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MessageServiceGrpc {

  private MessageServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helloim.message.api.MessageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
      com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getPullOfflineMsgMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PullOfflineMsg",
      requestType = com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest.class,
      responseType = com.github.xuning888.helloim.message.api.Msg.ChatMessageList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
      com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getPullOfflineMsgMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest, com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getPullOfflineMsgMethod;
    if ((getPullOfflineMsgMethod = MessageServiceGrpc.getPullOfflineMsgMethod) == null) {
      synchronized (MessageServiceGrpc.class) {
        if ((getPullOfflineMsgMethod = MessageServiceGrpc.getPullOfflineMsgMethod) == null) {
          MessageServiceGrpc.getPullOfflineMsgMethod = getPullOfflineMsgMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest, com.github.xuning888.helloim.message.api.Msg.ChatMessageList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PullOfflineMsg"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.message.api.Msg.ChatMessageList.getDefaultInstance()))
              .setSchemaDescriptor(new MessageServiceMethodDescriptorSupplier("PullOfflineMsg"))
              .build();
        }
      }
    }
    return getPullOfflineMsgMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
      com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getGetLatestOfflineMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLatestOfflineMessages",
      requestType = com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest.class,
      responseType = com.github.xuning888.helloim.message.api.Msg.ChatMessageList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
      com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getGetLatestOfflineMessagesMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest, com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getGetLatestOfflineMessagesMethod;
    if ((getGetLatestOfflineMessagesMethod = MessageServiceGrpc.getGetLatestOfflineMessagesMethod) == null) {
      synchronized (MessageServiceGrpc.class) {
        if ((getGetLatestOfflineMessagesMethod = MessageServiceGrpc.getGetLatestOfflineMessagesMethod) == null) {
          MessageServiceGrpc.getGetLatestOfflineMessagesMethod = getGetLatestOfflineMessagesMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest, com.github.xuning888.helloim.message.api.Msg.ChatMessageList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLatestOfflineMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.message.api.Msg.ChatMessageList.getDefaultInstance()))
              .setSchemaDescriptor(new MessageServiceMethodDescriptorSupplier("GetLatestOfflineMessages"))
              .build();
        }
      }
    }
    return getGetLatestOfflineMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessageServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessageServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessageServiceStub>() {
        @java.lang.Override
        public MessageServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessageServiceStub(channel, callOptions);
        }
      };
    return MessageServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessageServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessageServiceBlockingStub>() {
        @java.lang.Override
        public MessageServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessageServiceBlockingStub(channel, callOptions);
        }
      };
    return MessageServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessageServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessageServiceFutureStub>() {
        @java.lang.Override
        public MessageServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessageServiceFutureStub(channel, callOptions);
        }
      };
    return MessageServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 消息服务接口
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 拉取离线消息
     * </pre>
     */
    default void pullOfflineMsg(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPullOfflineMsgMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取最新离线消息
     * </pre>
     */
    default void getLatestOfflineMessages(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLatestOfflineMessagesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MessageService.
   * <pre>
   * 消息服务接口
   * </pre>
   */
  public static abstract class MessageServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MessageServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MessageService.
   * <pre>
   * 消息服务接口
   * </pre>
   */
  public static final class MessageServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MessageServiceStub> {
    private MessageServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessageServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 拉取离线消息
     * </pre>
     */
    public void pullOfflineMsg(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPullOfflineMsgMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取最新离线消息
     * </pre>
     */
    public void getLatestOfflineMessages(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLatestOfflineMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MessageService.
   * <pre>
   * 消息服务接口
   * </pre>
   */
  public static final class MessageServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MessageServiceBlockingStub> {
    private MessageServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessageServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 拉取离线消息
     * </pre>
     */
    public com.github.xuning888.helloim.message.api.Msg.ChatMessageList pullOfflineMsg(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPullOfflineMsgMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取最新离线消息
     * </pre>
     */
    public com.github.xuning888.helloim.message.api.Msg.ChatMessageList getLatestOfflineMessages(com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLatestOfflineMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MessageService.
   * <pre>
   * 消息服务接口
   * </pre>
   */
  public static final class MessageServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MessageServiceFutureStub> {
    private MessageServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessageServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessageServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 拉取离线消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> pullOfflineMsg(
        com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPullOfflineMsgMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取最新离线消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.message.api.Msg.ChatMessageList> getLatestOfflineMessages(
        com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLatestOfflineMessagesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PULL_OFFLINE_MSG = 0;
  private static final int METHODID_GET_LATEST_OFFLINE_MESSAGES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PULL_OFFLINE_MSG:
          serviceImpl.pullOfflineMsg((com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList>) responseObserver);
          break;
        case METHODID_GET_LATEST_OFFLINE_MESSAGES:
          serviceImpl.getLatestOfflineMessages((com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.message.api.Msg.ChatMessageList>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getPullOfflineMsgMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
              com.github.xuning888.helloim.message.api.Msg.ChatMessageList>(
                service, METHODID_PULL_OFFLINE_MSG)))
        .addMethod(
          getGetLatestOfflineMessagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.message.api.Msg.PullOfflineMsgRequest,
              com.github.xuning888.helloim.message.api.Msg.ChatMessageList>(
                service, METHODID_GET_LATEST_OFFLINE_MESSAGES)))
        .build();
  }

  private static abstract class MessageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.message.api.Msg.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MessageService");
    }
  }

  private static final class MessageServiceFileDescriptorSupplier
      extends MessageServiceBaseDescriptorSupplier {
    MessageServiceFileDescriptorSupplier() {}
  }

  private static final class MessageServiceMethodDescriptorSupplier
      extends MessageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MessageServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
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
              .setSchemaDescriptor(new MessageServiceFileDescriptorSupplier())
              .addMethod(getPullOfflineMsgMethod())
              .addMethod(getGetLatestOfflineMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
