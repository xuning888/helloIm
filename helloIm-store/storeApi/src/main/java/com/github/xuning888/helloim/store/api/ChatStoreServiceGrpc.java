package com.github.xuning888.helloim.store.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 会话存储服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: store.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatStoreServiceGrpc {

  private ChatStoreServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helloim.store.api.ChatStoreService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest,
      com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> getCreateOrUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createOrUpdate",
      requestType = com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest.class,
      responseType = com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest,
      com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> getCreateOrUpdateMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest, com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> getCreateOrUpdateMethod;
    if ((getCreateOrUpdateMethod = ChatStoreServiceGrpc.getCreateOrUpdateMethod) == null) {
      synchronized (ChatStoreServiceGrpc.class) {
        if ((getCreateOrUpdateMethod = ChatStoreServiceGrpc.getCreateOrUpdateMethod) == null) {
          ChatStoreServiceGrpc.getCreateOrUpdateMethod = getCreateOrUpdateMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest, com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createOrUpdate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatStoreServiceMethodDescriptorSupplier("createOrUpdate"))
              .build();
        }
      }
    }
    return getCreateOrUpdateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatStoreServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceStub>() {
        @java.lang.Override
        public ChatStoreServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatStoreServiceStub(channel, callOptions);
        }
      };
    return ChatStoreServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatStoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceBlockingStub>() {
        @java.lang.Override
        public ChatStoreServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatStoreServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatStoreServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatStoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatStoreServiceFutureStub>() {
        @java.lang.Override
        public ChatStoreServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatStoreServiceFutureStub(channel, callOptions);
        }
      };
    return ChatStoreServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 会话存储服务
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateOrUpdateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatStoreService.
   * <pre>
   * 会话存储服务
   * </pre>
   */
  public static abstract class ChatStoreServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatStoreServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatStoreService.
   * <pre>
   * 会话存储服务
   * </pre>
   */
  public static final class ChatStoreServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ChatStoreServiceStub> {
    private ChatStoreServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateOrUpdateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatStoreService.
   * <pre>
   * 会话存储服务
   * </pre>
   */
  public static final class ChatStoreServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatStoreServiceBlockingStub> {
    private ChatStoreServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateOrUpdateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatStoreService.
   * <pre>
   * 会话存储服务
   * </pre>
   */
  public static final class ChatStoreServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatStoreServiceFutureStub> {
    private ChatStoreServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> createOrUpdate(
        com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateOrUpdateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_OR_UPDATE = 0;

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
        case METHODID_CREATE_OR_UPDATE:
          serviceImpl.createOrUpdate((com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse>) responseObserver);
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
          getCreateOrUpdateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest,
              com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse>(
                service, METHODID_CREATE_OR_UPDATE)))
        .build();
  }

  private static abstract class ChatStoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatStoreServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.store.api.Store.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatStoreService");
    }
  }

  private static final class ChatStoreServiceFileDescriptorSupplier
      extends ChatStoreServiceBaseDescriptorSupplier {
    ChatStoreServiceFileDescriptorSupplier() {}
  }

  private static final class ChatStoreServiceMethodDescriptorSupplier
      extends ChatStoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ChatStoreServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ChatStoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatStoreServiceFileDescriptorSupplier())
              .addMethod(getCreateOrUpdateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
