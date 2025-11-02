package com.github.xuning888.helloim.store.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 消息存储服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: store.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MsgStoreServiceGrpc {

  private MsgStoreServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helloim.store.api.MsgStoreService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest,
      com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> getSaveMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveMessage",
      requestType = com.github.xuning888.helloim.store.api.Store.SaveMessageRequest.class,
      responseType = com.github.xuning888.helloim.store.api.Store.SaveMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest,
      com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> getSaveMessageMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest, com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> getSaveMessageMethod;
    if ((getSaveMessageMethod = MsgStoreServiceGrpc.getSaveMessageMethod) == null) {
      synchronized (MsgStoreServiceGrpc.class) {
        if ((getSaveMessageMethod = MsgStoreServiceGrpc.getSaveMessageMethod) == null) {
          MsgStoreServiceGrpc.getSaveMessageMethod = getSaveMessageMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest, com.github.xuning888.helloim.store.api.Store.SaveMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.SaveMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.SaveMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgStoreServiceMethodDescriptorSupplier("SaveMessage"))
              .build();
        }
      }
    }
    return getSaveMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest,
      com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> getMaxServerSeqMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MaxServerSeq",
      requestType = com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest.class,
      responseType = com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest,
      com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> getMaxServerSeqMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest, com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> getMaxServerSeqMethod;
    if ((getMaxServerSeqMethod = MsgStoreServiceGrpc.getMaxServerSeqMethod) == null) {
      synchronized (MsgStoreServiceGrpc.class) {
        if ((getMaxServerSeqMethod = MsgStoreServiceGrpc.getMaxServerSeqMethod) == null) {
          MsgStoreServiceGrpc.getMaxServerSeqMethod = getMaxServerSeqMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest, com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MaxServerSeq"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgStoreServiceMethodDescriptorSupplier("MaxServerSeq"))
              .build();
        }
      }
    }
    return getMaxServerSeqMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.LastMessageRequest,
      com.github.xuning888.helloim.store.api.Store.LastMessageResponse> getLastMessageIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LastMessageId",
      requestType = com.github.xuning888.helloim.store.api.Store.LastMessageRequest.class,
      responseType = com.github.xuning888.helloim.store.api.Store.LastMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.LastMessageRequest,
      com.github.xuning888.helloim.store.api.Store.LastMessageResponse> getLastMessageIdMethod() {
    io.grpc.MethodDescriptor<com.github.xuning888.helloim.store.api.Store.LastMessageRequest, com.github.xuning888.helloim.store.api.Store.LastMessageResponse> getLastMessageIdMethod;
    if ((getLastMessageIdMethod = MsgStoreServiceGrpc.getLastMessageIdMethod) == null) {
      synchronized (MsgStoreServiceGrpc.class) {
        if ((getLastMessageIdMethod = MsgStoreServiceGrpc.getLastMessageIdMethod) == null) {
          MsgStoreServiceGrpc.getLastMessageIdMethod = getLastMessageIdMethod =
              io.grpc.MethodDescriptor.<com.github.xuning888.helloim.store.api.Store.LastMessageRequest, com.github.xuning888.helloim.store.api.Store.LastMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LastMessageId"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.LastMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.xuning888.helloim.store.api.Store.LastMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgStoreServiceMethodDescriptorSupplier("LastMessageId"))
              .build();
        }
      }
    }
    return getLastMessageIdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MsgStoreServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceStub>() {
        @java.lang.Override
        public MsgStoreServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgStoreServiceStub(channel, callOptions);
        }
      };
    return MsgStoreServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MsgStoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceBlockingStub>() {
        @java.lang.Override
        public MsgStoreServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgStoreServiceBlockingStub(channel, callOptions);
        }
      };
    return MsgStoreServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MsgStoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgStoreServiceFutureStub>() {
        @java.lang.Override
        public MsgStoreServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgStoreServiceFutureStub(channel, callOptions);
        }
      };
    return MsgStoreServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 消息存储服务
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveMessageMethod(), responseObserver);
    }

    /**
     */
    default void maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMaxServerSeqMethod(), responseObserver);
    }

    /**
     */
    default void lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLastMessageIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MsgStoreService.
   * <pre>
   * 消息存储服务
   * </pre>
   */
  public static abstract class MsgStoreServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MsgStoreServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MsgStoreService.
   * <pre>
   * 消息存储服务
   * </pre>
   */
  public static final class MsgStoreServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MsgStoreServiceStub> {
    private MsgStoreServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMaxServerSeqMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLastMessageIdMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MsgStoreService.
   * <pre>
   * 消息存储服务
   * </pre>
   */
  public static final class MsgStoreServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MsgStoreServiceBlockingStub> {
    private MsgStoreServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.store.api.Store.SaveMessageResponse saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMaxServerSeqMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.store.api.Store.LastMessageResponse lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLastMessageIdMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MsgStoreService.
   * <pre>
   * 消息存储服务
   * </pre>
   */
  public static final class MsgStoreServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MsgStoreServiceFutureStub> {
    private MsgStoreServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> saveMessage(
        com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> maxServerSeq(
        com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMaxServerSeqMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> lastMessageId(
        com.github.xuning888.helloim.store.api.Store.LastMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLastMessageIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_MESSAGE = 0;
  private static final int METHODID_MAX_SERVER_SEQ = 1;
  private static final int METHODID_LAST_MESSAGE_ID = 2;

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
        case METHODID_SAVE_MESSAGE:
          serviceImpl.saveMessage((com.github.xuning888.helloim.store.api.Store.SaveMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse>) responseObserver);
          break;
        case METHODID_MAX_SERVER_SEQ:
          serviceImpl.maxServerSeq((com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse>) responseObserver);
          break;
        case METHODID_LAST_MESSAGE_ID:
          serviceImpl.lastMessageId((com.github.xuning888.helloim.store.api.Store.LastMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse>) responseObserver);
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
          getSaveMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.store.api.Store.SaveMessageRequest,
              com.github.xuning888.helloim.store.api.Store.SaveMessageResponse>(
                service, METHODID_SAVE_MESSAGE)))
        .addMethod(
          getMaxServerSeqMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest,
              com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse>(
                service, METHODID_MAX_SERVER_SEQ)))
        .addMethod(
          getLastMessageIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.xuning888.helloim.store.api.Store.LastMessageRequest,
              com.github.xuning888.helloim.store.api.Store.LastMessageResponse>(
                service, METHODID_LAST_MESSAGE_ID)))
        .build();
  }

  private static abstract class MsgStoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MsgStoreServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.store.api.Store.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MsgStoreService");
    }
  }

  private static final class MsgStoreServiceFileDescriptorSupplier
      extends MsgStoreServiceBaseDescriptorSupplier {
    MsgStoreServiceFileDescriptorSupplier() {}
  }

  private static final class MsgStoreServiceMethodDescriptorSupplier
      extends MsgStoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MsgStoreServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (MsgStoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MsgStoreServiceFileDescriptorSupplier())
              .addMethod(getSaveMessageMethod())
              .addMethod(getMaxServerSeqMethod())
              .addMethod(getLastMessageIdMethod())
              .build();
        }
      }
    }
    return result;
  }
}
