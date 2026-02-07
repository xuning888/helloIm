/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.xuning888.helloim.api.protobuf.store.v1;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboMsgStoreServiceTriple {

    public static final String SERVICE_NAME = MsgStoreService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, MsgStoreService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, MsgStoreServiceProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboMsgStoreServiceTriple::newStub);
        StubSuppliers.addSupplier(MsgStoreService.JAVA_SERVICE_NAME,  DubboMsgStoreServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(MsgStoreService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("unchecked")
    public static MsgStoreService newStub(Invoker<?> invoker) {
        return new MsgStoreServiceStub((Invoker<MsgStoreService>)invoker);
    }

    private static final StubMethodDescriptor saveMessageMethod = new StubMethodDescriptor("SaveMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor saveMessageAsyncMethod = new StubMethodDescriptor("SaveMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor saveMessageProxyAsyncMethod = new StubMethodDescriptor("SaveMessageAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqMethod = new StubMethodDescriptor("MaxServerSeq",
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqAsyncMethod = new StubMethodDescriptor("MaxServerSeq",
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqProxyAsyncMethod = new StubMethodDescriptor("MaxServerSeqAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageMethod = new StubMethodDescriptor("LastMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageAsyncMethod = new StubMethodDescriptor("LastMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageProxyAsyncMethod = new StubMethodDescriptor("LastMessageAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor selectMessageByServerSeqsMethod = new StubMethodDescriptor("SelectMessageByServerSeqs",
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse::parseFrom);

    private static final StubMethodDescriptor selectMessageByServerSeqsAsyncMethod = new StubMethodDescriptor("SelectMessageByServerSeqs",
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse::parseFrom);

    private static final StubMethodDescriptor selectMessageByServerSeqsProxyAsyncMethod = new StubMethodDescriptor("SelectMessageByServerSeqsAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse::parseFrom);

    private static final StubMethodDescriptor getRecentMessagesMethod = new StubMethodDescriptor("GetRecentMessages",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse::parseFrom);

    private static final StubMethodDescriptor getRecentMessagesAsyncMethod = new StubMethodDescriptor("GetRecentMessages",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse::parseFrom);

    private static final StubMethodDescriptor getRecentMessagesProxyAsyncMethod = new StubMethodDescriptor("GetRecentMessagesAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse::parseFrom);

    static{
        serviceDescriptor.addMethod(saveMessageMethod);
        serviceDescriptor.addMethod(saveMessageProxyAsyncMethod);
        serviceDescriptor.addMethod(maxServerSeqMethod);
        serviceDescriptor.addMethod(maxServerSeqProxyAsyncMethod);
        serviceDescriptor.addMethod(lastMessageMethod);
        serviceDescriptor.addMethod(lastMessageProxyAsyncMethod);
        serviceDescriptor.addMethod(selectMessageByServerSeqsMethod);
        serviceDescriptor.addMethod(selectMessageByServerSeqsProxyAsyncMethod);
        serviceDescriptor.addMethod(getRecentMessagesMethod);
        serviceDescriptor.addMethod(getRecentMessagesProxyAsyncMethod);
    }

    public static class MsgStoreServiceStub implements MsgStoreService, Destroyable {
        private final Invoker<MsgStoreService> invoker;

        public MsgStoreServiceStub(Invoker<MsgStoreService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, saveMessageMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> saveMessageAsync(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, saveMessageAsyncMethod, request);
        }

        public void saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, saveMessageMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, maxServerSeqMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> maxServerSeqAsync(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, maxServerSeqAsyncMethod, request);
        }

        public void maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, maxServerSeqMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, lastMessageMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> lastMessageAsync(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, lastMessageAsyncMethod, request);
        }

        public void lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, lastMessageMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request){
            return StubInvocationUtil.unaryCall(invoker, selectMessageByServerSeqsMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> selectMessageByServerSeqsAsync(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request){
            return StubInvocationUtil.unaryCall(invoker, selectMessageByServerSeqsAsyncMethod, request);
        }

        public void selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, selectMessageByServerSeqsMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request){
            return StubInvocationUtil.unaryCall(invoker, getRecentMessagesMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> getRecentMessagesAsync(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request){
            return StubInvocationUtil.unaryCall(invoker, getRecentMessagesAsyncMethod, request);
        }

        public void getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, getRecentMessagesMethod , request, responseObserver);
        }
    }

    public static abstract class MsgStoreServiceImplBase implements MsgStoreService, ServerService<MsgStoreService> {
        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> saveMessageAsync(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request){
                return CompletableFuture.completedFuture(saveMessage(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> maxServerSeqAsync(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request){
                return CompletableFuture.completedFuture(maxServerSeq(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> lastMessageAsync(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
                return CompletableFuture.completedFuture(lastMessage(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> selectMessageByServerSeqsAsync(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request){
                return CompletableFuture.completedFuture(selectMessageByServerSeqs(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> getRecentMessagesAsync(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request){
                return CompletableFuture.completedFuture(getRecentMessages(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse> responseObserver){
            saveMessageAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse> responseObserver){
            maxServerSeqAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> responseObserver){
            lastMessageAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse> responseObserver){
            selectMessageByServerSeqsAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse> responseObserver){
            getRecentMessagesAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<MsgStoreService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String, StubMethodHandler<?, ?>> handlers = new HashMap<>();
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SaveMessage");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SaveMessageAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SaveMessage");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SaveMessageAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/MaxServerSeq");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/MaxServerSeqAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/MaxServerSeq");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/MaxServerSeqAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/LastMessage");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/LastMessageAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/LastMessage");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/LastMessageAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SelectMessageByServerSeqs");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SelectMessageByServerSeqsAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SelectMessageByServerSeqs");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SelectMessageByServerSeqsAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/GetRecentMessages");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/GetRecentMessagesAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/GetRecentMessages");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/GetRecentMessagesAsync");
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse>> saveMessageFunc = this::saveMessage;
            handlers.put(saveMessageMethod.getMethodName(), new UnaryStubMethodHandler<>(saveMessageFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse>> saveMessageAsyncFunc = syncToAsync(this::saveMessage);
            handlers.put(saveMessageProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(saveMessageAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse>> maxServerSeqFunc = this::maxServerSeq;
            handlers.put(maxServerSeqMethod.getMethodName(), new UnaryStubMethodHandler<>(maxServerSeqFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse>> maxServerSeqAsyncFunc = syncToAsync(this::maxServerSeq);
            handlers.put(maxServerSeqProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(maxServerSeqAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>> lastMessageFunc = this::lastMessage;
            handlers.put(lastMessageMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>> lastMessageAsyncFunc = syncToAsync(this::lastMessage);
            handlers.put(lastMessageProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse>> selectMessageByServerSeqsFunc = this::selectMessageByServerSeqs;
            handlers.put(selectMessageByServerSeqsMethod.getMethodName(), new UnaryStubMethodHandler<>(selectMessageByServerSeqsFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse>> selectMessageByServerSeqsAsyncFunc = syncToAsync(this::selectMessageByServerSeqs);
            handlers.put(selectMessageByServerSeqsProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(selectMessageByServerSeqsAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse>> getRecentMessagesFunc = this::getRecentMessages;
            handlers.put(getRecentMessagesMethod.getMethodName(), new UnaryStubMethodHandler<>(getRecentMessagesFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse>> getRecentMessagesAsyncFunc = syncToAsync(this::getRecentMessages);
            handlers.put(getRecentMessagesProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(getRecentMessagesAsyncFunc));

            return new StubInvoker<>(this, url, MsgStoreService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageResponse saveMessage(com.github.xuning888.helloim.api.protobuf.store.v1.SaveMessageRequest request){
            throw unimplementedMethodException(saveMessageMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.api.protobuf.store.v1.MaxServerSeqRequest request){
            throw unimplementedMethodException(maxServerSeqMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
            throw unimplementedMethodException(lastMessageMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsResponse selectMessageByServerSeqs(com.github.xuning888.helloim.api.protobuf.store.v1.SelectMessageByServerSeqsRequest request){
            throw unimplementedMethodException(selectMessageByServerSeqsMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesResponse getRecentMessages(com.github.xuning888.helloim.api.protobuf.store.v1.GetRecentMessagesRequest request){
            throw unimplementedMethodException(getRecentMessagesMethod);
        }

        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }
}
