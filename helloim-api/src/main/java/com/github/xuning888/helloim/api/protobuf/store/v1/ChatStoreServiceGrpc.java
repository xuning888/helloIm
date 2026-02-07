package com.github.xuning888.helloim.api.protobuf.store.v1;

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
    comments = "Source: api/store/v1/chat_store_service.proto")
public final class ChatStoreServiceGrpc {

  private ChatStoreServiceGrpc() {}

  public static final String SERVICE_NAME = "ChatStoreService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse> METHOD_CREATE_OR_UPDATE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest, com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatStoreService", "createOrUpdate"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> METHOD_GET_ALL_CHAT =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest, com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatStoreService", "getAllChat"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse> METHOD_BATCH_CREATE_OR_UPDATE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest, com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatStoreService", "batchCreateOrUpdate"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatStoreServiceStub newStub(io.grpc.Channel channel) {
    return new ChatStoreServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatStoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChatStoreServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatStoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChatStoreServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChatStoreServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_OR_UPDATE, responseObserver);
    }

    /**
     */
    public void getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_CHAT, responseObserver);
    }

    /**
     */
    public void batchCreateOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_BATCH_CREATE_OR_UPDATE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_OR_UPDATE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse>(
                  this, METHODID_CREATE_OR_UPDATE)))
          .addMethod(
            METHOD_GET_ALL_CHAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>(
                  this, METHODID_GET_ALL_CHAT)))
          .addMethod(
            METHOD_BATCH_CREATE_OR_UPDATE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse>(
                  this, METHODID_BATCH_CREATE_OR_UPDATE)))
          .build();
    }
  }

  /**
   */
  public static final class ChatStoreServiceStub extends io.grpc.stub.AbstractStub<ChatStoreServiceStub> {
    private ChatStoreServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatStoreServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void createOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_OR_UPDATE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_CHAT, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void batchCreateOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_BATCH_CREATE_OR_UPDATE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatStoreServiceBlockingStub extends io.grpc.stub.AbstractStub<ChatStoreServiceBlockingStub> {
    private ChatStoreServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatStoreServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse createOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_OR_UPDATE, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ALL_CHAT, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse batchCreateOrUpdate(com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_BATCH_CREATE_OR_UPDATE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatStoreServiceFutureStub extends io.grpc.stub.AbstractStub<ChatStoreServiceFutureStub> {
    private ChatStoreServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatStoreServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatStoreServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatStoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse> createOrUpdate(
        com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_OR_UPDATE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> getAllChat(
        com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_CHAT, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse> batchCreateOrUpdate(
        com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_BATCH_CREATE_OR_UPDATE, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_OR_UPDATE = 0;
  private static final int METHODID_GET_ALL_CHAT = 1;
  private static final int METHODID_BATCH_CREATE_OR_UPDATE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatStoreServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatStoreServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_OR_UPDATE:
          serviceImpl.createOrUpdate((com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.CreateOrUpdateChatResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_CHAT:
          serviceImpl.getAllChat((com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>) responseObserver);
          break;
        case METHODID_BATCH_CREATE_OR_UPDATE:
          serviceImpl.batchCreateOrUpdate((com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.BatchCreateOrUpdateResponse>) responseObserver);
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

  private static final class ChatStoreServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.api.protobuf.store.v1.ChatStoreServiceProto.getDescriptor();
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
              .setSchemaDescriptor(new ChatStoreServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_OR_UPDATE)
              .addMethod(METHOD_GET_ALL_CHAT)
              .addMethod(METHOD_BATCH_CREATE_OR_UPDATE)
              .build();
        }
      }
    }
    return result;
  }
}
