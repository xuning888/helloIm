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

package com.github.xuning888.helloim.chat.api;

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
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, Chat.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboChatServiceTriple::newStub);
        StubSuppliers.addSupplier(ChatService.JAVA_SERVICE_NAME,  DubboChatServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(ChatService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("unchecked")
    public static ChatService newStub(Invoker<?> invoker) {
        return new ChatServiceStub((Invoker<ChatService>)invoker);
    }

    private static final StubMethodDescriptor serverSeqMethod = new StubMethodDescriptor("ServerSeq",
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest.class, com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor serverSeqAsyncMethod = new StubMethodDescriptor("ServerSeq",
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor serverSeqProxyAsyncMethod = new StubMethodDescriptor("ServerSeqAsync",
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest.class, com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatMethod = new StubMethodDescriptor("CreateOrActivateChat",
    com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest.class, com.github.xuning888.helloim.chat.api.Chat.ImChat.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ImChat::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatAsyncMethod = new StubMethodDescriptor("CreateOrActivateChat",
    com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ImChat::parseFrom);

    private static final StubMethodDescriptor createOrActivateChatProxyAsyncMethod = new StubMethodDescriptor("CreateOrActivateChatAsync",
    com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest.class, com.github.xuning888.helloim.chat.api.Chat.ImChat.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest::parseFrom,
    com.github.xuning888.helloim.chat.api.Chat.ImChat::parseFrom);

    static{
        serviceDescriptor.addMethod(serverSeqMethod);
        serviceDescriptor.addMethod(serverSeqProxyAsyncMethod);
        serviceDescriptor.addMethod(createOrActivateChatMethod);
        serviceDescriptor.addMethod(createOrActivateChatProxyAsyncMethod);
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
        public com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, serverSeqMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> serverSeqAsync(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, serverSeqAsyncMethod, request);
        }

        public void serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, serverSeqMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.chat.api.Chat.ImChat createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrActivateChatMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.chat.api.Chat.ImChat> createOrActivateChatAsync(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrActivateChatAsyncMethod, request);
        }

        public void createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat> responseObserver){
            StubInvocationUtil.unaryCall(invoker, createOrActivateChatMethod , request, responseObserver);
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
        public CompletableFuture<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> serverSeqAsync(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request){
                return CompletableFuture.completedFuture(serverSeq(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.chat.api.Chat.ImChat> createOrActivateChatAsync(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request){
                return CompletableFuture.completedFuture(createOrActivateChat(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse> responseObserver){
            serverSeqAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat> responseObserver){
            createOrActivateChatAsync(request).whenComplete((r, t) -> {
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
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ServerSeq");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ServerSeqAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ServerSeq");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ServerSeqAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CreateOrActivateChat");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CreateOrActivateChatAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CreateOrActivateChat");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CreateOrActivateChatAsync");
            BiConsumer<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse>> serverSeqFunc = this::serverSeq;
            handlers.put(serverSeqMethod.getMethodName(), new UnaryStubMethodHandler<>(serverSeqFunc));
            BiConsumer<com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse>> serverSeqAsyncFunc = syncToAsync(this::serverSeq);
            handlers.put(serverSeqProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(serverSeqAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat>> createOrActivateChatFunc = this::createOrActivateChat;
            handlers.put(createOrActivateChatMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrActivateChatFunc));
            BiConsumer<com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest, StreamObserver<com.github.xuning888.helloim.chat.api.Chat.ImChat>> createOrActivateChatAsyncFunc = syncToAsync(this::createOrActivateChat);
            handlers.put(createOrActivateChatProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrActivateChatAsyncFunc));

            return new StubInvoker<>(this, url, ChatService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.chat.api.Chat.ServerSeqResponse serverSeq(com.github.xuning888.helloim.chat.api.Chat.ServerSeqRequest request){
            throw unimplementedMethodException(serverSeqMethod);
        }

        @Override
        public com.github.xuning888.helloim.chat.api.Chat.ImChat createOrActivateChat(com.github.xuning888.helloim.chat.api.Chat.CreateOrActivateChatRequest request){
            throw unimplementedMethodException(createOrActivateChatMethod);
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
