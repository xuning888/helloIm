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

package com.github.xuning888.helloim.store.api;

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

public final class DubboChatStoreServiceTriple {

    public static final String SERVICE_NAME = ChatStoreService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, ChatStoreService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, Store.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboChatStoreServiceTriple::newStub);
        StubSuppliers.addSupplier(ChatStoreService.JAVA_SERVICE_NAME,  DubboChatStoreServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(ChatStoreService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("unchecked")
    public static ChatStoreService newStub(Invoker<?> invoker) {
        return new ChatStoreServiceStub((Invoker<ChatStoreService>)invoker);
    }

    private static final StubMethodDescriptor createOrUpdateMethod = new StubMethodDescriptor("createOrUpdate",
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest.class, com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse::parseFrom);

    private static final StubMethodDescriptor createOrUpdateAsyncMethod = new StubMethodDescriptor("createOrUpdate",
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse::parseFrom);

    private static final StubMethodDescriptor createOrUpdateProxyAsyncMethod = new StubMethodDescriptor("createOrUpdateAsync",
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest.class, com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse::parseFrom);

    static{
        serviceDescriptor.addMethod(createOrUpdateMethod);
        serviceDescriptor.addMethod(createOrUpdateProxyAsyncMethod);
    }

    public static class ChatStoreServiceStub implements ChatStoreService, Destroyable {
        private final Invoker<ChatStoreService> invoker;

        public ChatStoreServiceStub(Invoker<ChatStoreService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        @Override
        public com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrUpdateMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> createOrUpdateAsync(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request){
            return StubInvocationUtil.unaryCall(invoker, createOrUpdateAsyncMethod, request);
        }

        public void createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, createOrUpdateMethod , request, responseObserver);
        }
    }

    public static abstract class ChatStoreServiceImplBase implements ChatStoreService, ServerService<ChatStoreService> {
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
        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> createOrUpdateAsync(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request){
                return CompletableFuture.completedFuture(createOrUpdate(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse> responseObserver){
            createOrUpdateAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<ChatStoreService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String, StubMethodHandler<?, ?>> handlers = new HashMap<>();
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/createOrUpdate");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/createOrUpdateAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/createOrUpdate");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/createOrUpdateAsync");
            BiConsumer<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse>> createOrUpdateFunc = this::createOrUpdate;
            handlers.put(createOrUpdateMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrUpdateFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse>> createOrUpdateAsyncFunc = syncToAsync(this::createOrUpdate);
            handlers.put(createOrUpdateProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(createOrUpdateAsyncFunc));

            return new StubInvoker<>(this, url, ChatStoreService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatResponse createOrUpdate(com.github.xuning888.helloim.store.api.Store.CreateOrUpdateChatRequest request){
            throw unimplementedMethodException(createOrUpdateMethod);
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
