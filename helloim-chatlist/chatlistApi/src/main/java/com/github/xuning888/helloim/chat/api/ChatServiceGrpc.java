package com.github.xuning888.helloim.chat.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: chat.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helloim.chat.api.ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest,
      com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> getServerSeqMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServerSeq",
      requestType = com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest.class,
      responseType = com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest,
      com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> getServerSeqMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest, com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> getServerSeqMethod;
    if ((getServerSeqMethod = ChatServiceGrpc.getServerSeqMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getServerSeqMethod = ChatServiceGrpc.getServerSeqMethod) == null) {
          ChatServiceGrpc.getServerSeqMethod = getServerSeqMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest, com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerSeq"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("ServerSeq"))
              .build();
        }
      }
    }
    return getServerSeqMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest,
      com.github.xuning888.helloim.chat.api.Chat.ImChat> getCreateOrActivateChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateOrActivateChat",
      requestType = com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest.class,
      responseType = com.github.xuning888.helloim.chat.api.Chat.ImChat.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest,
      com.github.xuning888.helloim.chat.api.Chat.ImChat> getCreateOrActivateChatMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest, com.github.xuning888.helloim.chat.api.Chat.ImChat> getCreateOrActivateChatMethod;
    if ((getCreateOrActivateChatMethod = ChatServiceGrpc.getCreateOrActivateChatMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getCreateOrActivateChatMethod = ChatServiceGrpc.getCreateOrActivateChatMethod) == null) {
          ChatServiceGrpc.getCreateOrActivateChatMethod = getCreateOrActivateChatMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest, com.github.xuning888.helloim.chat.api.Chat.ImChat>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateOrActivateChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.chat.api.Chat.ImChat.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("CreateOrActivateChat"))
              .build();
        }
      }
    }
    return getCreateOrActivateChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * 获取服务端seq
     * </pre>
     */
    default void serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerSeqMethod(), responseObserver);
    }

    /**
     * <pre>
     * 创建或激活会话
     * </pre>
     */
    default void createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateOrActivateChatMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatService.
   */
  public static abstract class ChatServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatService.
   */
  public static final class ChatServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取服务端seq
     * </pre>
     */
    public void serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getServerSeqMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 创建或激活会话
     * </pre>
     */
    public void createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateOrActivateChatMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatService.
   */
  public static final class ChatServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取服务端seq
     * </pre>
     */
    public com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getServerSeqMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 创建或激活会话
     * </pre>
     */
    public com.github.xuning888.helloim.chat.api.Chat.ImChat createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateOrActivateChatMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatService.
   */
  public static final class ChatServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取服务端seq
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> serverSeq(
        com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getServerSeqMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 创建或激活会话
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.chat.api.Chat.ImChat> createOrActivateChat(
        com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateOrActivateChatMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SERVER_SEQ = 0;
  private static final int METHODID_CREATE_OR_ACTIVATE_CHAT = 1;

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
        case METHODID_SERVER_SEQ:
          serviceImpl.serverSeq((com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse>) responseObserver);
          break;
        case METHODID_CREATE_OR_ACTIVATE_CHAT:
          serviceImpl.createOrActivateChat((com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat>) responseObserver);
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
          getServerSeqMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest,
              com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse>(
                service, METHODID_SERVER_SEQ)))
        .addMethod(
          getCreateOrActivateChatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest,
              com.github.xuning888.helloim.chat.api.Chat.ImChat>(
                service, METHODID_CREATE_OR_ACTIVATE_CHAT)))
        .build();
  }

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.chat.api.Chat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ChatServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getServerSeqMethod())
              .addMethod(getCreateOrActivateChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
