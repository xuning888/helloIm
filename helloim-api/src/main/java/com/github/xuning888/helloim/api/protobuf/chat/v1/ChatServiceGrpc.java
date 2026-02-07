package com.github.xuning888.helloim.api.protobuf.chat.v1;

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
    comments = "Source: api/chat/v1/chat_service.proto")
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final String SERVICE_NAME = "ChatService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest,
      com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> METHOD_SERVER_SEQ =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest, com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatService", "serverSeq"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest,
      com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> METHOD_CREATE_OR_ACTIVATE_CHAT =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest, com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatService", "createOrActivateChat"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> METHOD_GET_ALL_CHAT =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest, com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatService", "getAllChat"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> METHOD_LAST_MESSAGE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatService", "lastMessage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest,
      com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> METHOD_GET_CHAT =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest, com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "ChatService", "getChat"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    return new ChatServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChatServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChatServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChatServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SERVER_SEQ, responseObserver);
    }

    /**
     */
    public void createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_OR_ACTIVATE_CHAT, responseObserver);
    }

    /**
     */
    public void getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_CHAT, responseObserver);
    }

    /**
     */
    public void lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LAST_MESSAGE, responseObserver);
    }

    /**
     */
    public void getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CHAT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SERVER_SEQ,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest,
                com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse>(
                  this, METHODID_SERVER_SEQ)))
          .addMethod(
            METHOD_CREATE_OR_ACTIVATE_CHAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest,
                com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse>(
                  this, METHODID_CREATE_OR_ACTIVATE_CHAT)))
          .addMethod(
            METHOD_GET_ALL_CHAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>(
                  this, METHODID_GET_ALL_CHAT)))
          .addMethod(
            METHOD_LAST_MESSAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>(
                  this, METHODID_LAST_MESSAGE)))
          .addMethod(
            METHOD_GET_CHAT,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest,
                com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse>(
                  this, METHODID_GET_CHAT)))
          .build();
    }
  }

  /**
   */
  public static final class ChatServiceStub extends io.grpc.stub.AbstractStub<ChatServiceStub> {
    private ChatServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     */
    public void serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SERVER_SEQ, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_OR_ACTIVATE_CHAT, getCallOptions()), request, responseObserver);
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
    public void lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LAST_MESSAGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_CHAT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatServiceBlockingStub extends io.grpc.stub.AbstractStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SERVER_SEQ, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_OR_ACTIVATE_CHAT, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ALL_CHAT, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LAST_MESSAGE, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_CHAT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatServiceFutureStub extends io.grpc.stub.AbstractStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> serverSeq(
        com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SERVER_SEQ, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> createOrActivateChat(
        com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_OR_ACTIVATE_CHAT, getCallOptions()), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> lastMessage(
        com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LAST_MESSAGE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> getChat(
        com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_CHAT, getCallOptions()), request);
    }
  }

  private static final int METHODID_SERVER_SEQ = 0;
  private static final int METHODID_CREATE_OR_ACTIVATE_CHAT = 1;
  private static final int METHODID_GET_ALL_CHAT = 2;
  private static final int METHODID_LAST_MESSAGE = 3;
  private static final int METHODID_GET_CHAT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SERVER_SEQ:
          serviceImpl.serverSeq((com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse>) responseObserver);
          break;
        case METHODID_CREATE_OR_ACTIVATE_CHAT:
          serviceImpl.createOrActivateChat((com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_CHAT:
          serviceImpl.getAllChat((com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>) responseObserver);
          break;
        case METHODID_LAST_MESSAGE:
          serviceImpl.lastMessage((com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>) responseObserver);
          break;
        case METHODID_GET_CHAT:
          serviceImpl.getChat((com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse>) responseObserver);
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

  private static final class ChatServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.api.protobuf.chat.v1.ChatServiceProto.getDescriptor();
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
              .setSchemaDescriptor(new ChatServiceDescriptorSupplier())
              .addMethod(METHOD_SERVER_SEQ)
              .addMethod(METHOD_CREATE_OR_ACTIVATE_CHAT)
              .addMethod(METHOD_GET_ALL_CHAT)
              .addMethod(METHOD_LAST_MESSAGE)
              .addMethod(METHOD_GET_CHAT)
              .build();
        }
      }
    }
    return result;
  }
}
