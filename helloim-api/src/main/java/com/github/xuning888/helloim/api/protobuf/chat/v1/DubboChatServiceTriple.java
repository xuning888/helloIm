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

package com.github.xuning888.helloim.api.protobuf.chat.v1;

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

public final class DubboChatServiceTriple {

    public static final String SERVICE_NAME = ChatService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, ChatService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, ChatServiceProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboChatServiceTriple::newStub);
        StubSuppliers.addSupplier(ChatService.JAVA_SERVICE_NAME,  DubboChatServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(ChatService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("unchecked")
    public static ChatService newStub(Invoker<?> invoker) {
        return new ChatServiceStub((Invoker<ChatService>)invoker);
    }

    private static final StubMethodDescriptor serverSeqMethod = new StubMethodDescriptor("serverSeq",
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor serverSeqAsyncMethod = new StubMethodDescriptor("serverSeq",
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor serverSeqProxyAsyncMethod = new StubMethodDescriptor("serverSeqAsync",
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatMethod = new StubMethodDescriptor("createOrActivateChat",
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatAsyncMethod = new StubMethodDescriptor("createOrActivateChat",
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatProxyAsyncMethod = new StubMethodDescriptor("createOrActivateChatAsync",
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse::parseFrom);

    private static final StubMethodDescriptor getAllChatMethod = new StubMethodDescriptor("getAllChat",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse::parseFrom);

    private static final StubMethodDescriptor getAllChatAsyncMethod = new StubMethodDescriptor("getAllChat",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse::parseFrom);

    private static final StubMethodDescriptor getAllChatProxyAsyncMethod = new StubMethodDescriptor("getAllChatAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageMethod = new StubMethodDescriptor("lastMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageAsyncMethod = new StubMethodDescriptor("lastMessage",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageProxyAsyncMethod = new StubMethodDescriptor("lastMessageAsync",
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest.class, com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor getChatMethod = new StubMethodDescriptor("getChat",
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse::parseFrom);

    private static final StubMethodDescriptor getChatAsyncMethod = new StubMethodDescriptor("getChat",
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse::parseFrom);

    private static final StubMethodDescriptor getChatProxyAsyncMethod = new StubMethodDescriptor("getChatAsync",
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest.class, com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse::parseFrom);

    static{
        serviceDescriptor.addMethod(serverSeqMethod);
        serviceDescriptor.addMethod(serverSeqProxyAsyncMethod);
        serviceDescriptor.addMethod(createOrActivateChatMethod);
        serviceDescriptor.addMethod(createOrActivateChatProxyAsyncMethod);
        serviceDescriptor.addMethod(getAllChatMethod);
        serviceDescriptor.addMethod(getAllChatProxyAsyncMethod);
        serviceDescriptor.addMethod(lastMessageMethod);
        serviceDescriptor.addMethod(lastMessageProxyAsyncMethod);
        serviceDescriptor.addMethod(getChatMethod);
        serviceDescriptor.addMethod(getChatProxyAsyncMethod);
    }

    public static class ChatServiceStub implements ChatService, Destroyable {
        private final Invoker<ChatService> invoker;

        public ChatServiceStub(Invoker<ChatService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        @Override
        public com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, serverSeqMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> serverSeqAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, serverSeqAsyncMethod, request);
        }

        public void serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, serverSeqMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrActivateChatMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> createOrActivateChatAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrActivateChatAsyncMethod, request);
        }

        public void createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, createOrActivateChatMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, getAllChatMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> getAllChatAsync(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, getAllChatAsyncMethod, request);
        }

        public void getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, getAllChatMethod , request, responseObserver);
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
        public com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, getChatMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> getChatAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, getChatAsyncMethod, request);
        }

        public void getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, getChatMethod , request, responseObserver);
        }
    }

    public static abstract class ChatServiceImplBase implements ChatService, ServerService<ChatService> {
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
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> serverSeqAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request){
                return CompletableFuture.completedFuture(serverSeq(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> createOrActivateChatAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request){
                return CompletableFuture.completedFuture(createOrActivateChat(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> getAllChatAsync(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request){
                return CompletableFuture.completedFuture(getAllChat(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse> lastMessageAsync(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
                return CompletableFuture.completedFuture(lastMessage(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> getChatAsync(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request){
                return CompletableFuture.completedFuture(getChat(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse> responseObserver){
            serverSeqAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse> responseObserver){
            createOrActivateChatAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse> responseObserver){
            getAllChatAsync(request).whenComplete((r, t) -> {
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

        public void getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse> responseObserver){
            getChatAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<ChatService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String, StubMethodHandler<?, ?>> handlers = new HashMap<>();
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/serverSeq");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/serverSeqAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/serverSeq");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/serverSeqAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/createOrActivateChat");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/createOrActivateChatAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/createOrActivateChat");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/createOrActivateChatAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/getAllChat");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/getAllChatAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/getAllChat");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/getAllChatAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/lastMessage");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/lastMessageAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/lastMessage");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/lastMessageAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/getChat");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/getChatAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/getChat");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/getChatAsync");
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse>> serverSeqFunc = this::serverSeq;
            handlers.put(serverSeqMethod.getMethodName(), new UnaryStubMethodHandler<>(serverSeqFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse>> serverSeqAsyncFunc = syncToAsync(this::serverSeq);
            handlers.put(serverSeqProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(serverSeqAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse>> createOrActivateChatFunc = this::createOrActivateChat;
            handlers.put(createOrActivateChatMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrActivateChatFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse>> createOrActivateChatAsyncFunc = syncToAsync(this::createOrActivateChat);
            handlers.put(createOrActivateChatProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrActivateChatAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>> getAllChatFunc = this::getAllChat;
            handlers.put(getAllChatMethod.getMethodName(), new UnaryStubMethodHandler<>(getAllChatFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse>> getAllChatAsyncFunc = syncToAsync(this::getAllChat);
            handlers.put(getAllChatProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(getAllChatAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>> lastMessageFunc = this::lastMessage;
            handlers.put(lastMessageMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse>> lastMessageAsyncFunc = syncToAsync(this::lastMessage);
            handlers.put(lastMessageProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse>> getChatFunc = this::getChat;
            handlers.put(getChatMethod.getMethodName(), new UnaryStubMethodHandler<>(getChatFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse>> getChatAsyncFunc = syncToAsync(this::getChat);
            handlers.put(getChatProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(getChatAsyncFunc));

            return new StubInvoker<>(this, url, ChatService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqResponse serverSeq(com.github.xuning888.helloim.api.protobuf.chat.v1.ServerSeqRequest request){
            throw unimplementedMethodException(serverSeqMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatResponse createOrActivateChat(com.github.xuning888.helloim.api.protobuf.chat.v1.CreateOrActivateChatRequest request){
            throw unimplementedMethodException(createOrActivateChatMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatResponse getAllChat(com.github.xuning888.helloim.api.protobuf.store.v1.GetAllChatRequest request){
            throw unimplementedMethodException(getAllChatMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageResponse lastMessage(com.github.xuning888.helloim.api.protobuf.store.v1.LastMessageRequest request){
            throw unimplementedMethodException(lastMessageMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatResponse getChat(com.github.xuning888.helloim.api.protobuf.chat.v1.GetChatRequest request){
            throw unimplementedMethodException(getChatMethod);
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
