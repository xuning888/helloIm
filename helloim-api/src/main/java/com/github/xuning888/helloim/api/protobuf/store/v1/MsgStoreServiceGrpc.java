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
    comments = "Source: api/store/v1/msg_store_service.proto")
public final class MsgStoreServiceGrpc {

  private MsgStoreServiceGrpc() {}

  public static final String SERVICE_NAME = "MsgStoreService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> METHOD_SAVE_MESSAGE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest, com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MsgStoreService", "SaveMessage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> METHOD_MAX_SERVER_SEQ =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest, com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MsgStoreService", "MaxServerSeq"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> METHOD_LAST_MESSAGE =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MsgStoreService", "LastMessage"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> METHOD_SELECT_MESSAGE_BY_SERVER_SEQS =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest, com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MsgStoreService", "SelectMessageByServerSeqs"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest,
      com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> METHOD_GET_RECENT_MESSAGES =
      io.grpc.MethodDescriptor.<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest, com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MsgStoreService", "GetRecentMessages"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MsgStoreServiceStub newStub(io.grpc.Channel channel) {
    return new MsgStoreServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MsgStoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MsgStoreServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MsgStoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MsgStoreServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MsgStoreServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SAVE_MESSAGE, responseObserver);
    }

    /**
     */
    public void maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MAX_SERVER_SEQ, responseObserver);
    }

    /**
     */
    public void lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LAST_MESSAGE, responseObserver);
    }

    /**
     */
    public void selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SELECT_MESSAGE_BY_SERVER_SEQS, responseObserver);
    }

    /**
     */
    public void getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_RECENT_MESSAGES, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SAVE_MESSAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse>(
                  this, METHODID_SAVE_MESSAGE)))
          .addMethod(
            METHOD_MAX_SERVER_SEQ,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse>(
                  this, METHODID_MAX_SERVER_SEQ)))
          .addMethod(
            METHOD_LAST_MESSAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>(
                  this, METHODID_LAST_MESSAGE)))
          .addMethod(
            METHOD_SELECT_MESSAGE_BY_SERVER_SEQS,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse>(
                  this, METHODID_SELECT_MESSAGE_BY_SERVER_SEQS)))
          .addMethod(
            METHOD_GET_RECENT_MESSAGES,
            asyncUnaryCall(
              new MethodHandlers<
                com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest,
                com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse>(
                  this, METHODID_GET_RECENT_MESSAGES)))
          .build();
    }
  }

  /**
   */
  public static final class MsgStoreServiceStub extends io.grpc.stub.AbstractStub<MsgStoreServiceStub> {
    private MsgStoreServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgStoreServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SAVE_MESSAGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MAX_SERVER_SEQ, getCallOptions()), request, responseObserver);
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
    public void selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SELECT_MESSAGE_BY_SERVER_SEQS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request,
        io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_RECENT_MESSAGES, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MsgStoreServiceBlockingStub extends io.grpc.stub.AbstractStub<MsgStoreServiceBlockingStub> {
    private MsgStoreServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgStoreServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SAVE_MESSAGE, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MAX_SERVER_SEQ, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LAST_MESSAGE, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SELECT_MESSAGE_BY_SERVER_SEQS, getCallOptions(), request);
    }

    /**
     */
    public com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_RECENT_MESSAGES, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MsgStoreServiceFutureStub extends io.grpc.stub.AbstractStub<MsgStoreServiceFutureStub> {
    private MsgStoreServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgStoreServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgStoreServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgStoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> saveMessage(
        com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SAVE_MESSAGE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> maxServerSeq(
        com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MAX_SERVER_SEQ, getCallOptions()), request);
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
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> selectMessageByServerSeqs(
        com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SELECT_MESSAGE_BY_SERVER_SEQS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> getRecentMessages(
        com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_RECENT_MESSAGES, getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_MESSAGE = 0;
  private static final int METHODID_MAX_SERVER_SEQ = 1;
  private static final int METHODID_LAST_MESSAGE = 2;
  private static final int METHODID_SELECT_MESSAGE_BY_SERVER_SEQS = 3;
  private static final int METHODID_GET_RECENT_MESSAGES = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MsgStoreServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MsgStoreServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_MESSAGE:
          serviceImpl.saveMessage((com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse>) responseObserver);
          break;
        case METHODID_MAX_SERVER_SEQ:
          serviceImpl.maxServerSeq((com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse>) responseObserver);
          break;
        case METHODID_LAST_MESSAGE:
          serviceImpl.lastMessage((com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>) responseObserver);
          break;
        case METHODID_SELECT_MESSAGE_BY_SERVER_SEQS:
          serviceImpl.selectMessageByServerSeqs((com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse>) responseObserver);
          break;
        case METHODID_GET_RECENT_MESSAGES:
          serviceImpl.getRecentMessages((com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest) request,
              (io.grpc.stub.StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse>) responseObserver);
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

  private static final class MsgStoreServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.xuning888.helloim.api.protobuf.store.v1.MsgStoreServiceProto.getDescriptor();
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
              .setSchemaDescriptor(new MsgStoreServiceDescriptorSupplier())
              .addMethod(METHOD_SAVE_MESSAGE)
              .addMethod(METHOD_MAX_SERVER_SEQ)
              .addMethod(METHOD_LAST_MESSAGE)
              .addMethod(METHOD_SELECT_MESSAGE_BY_SERVER_SEQS)
              .addMethod(METHOD_GET_RECENT_MESSAGES)
              .build();
        }
      }
    }
    return result;
  }
}
