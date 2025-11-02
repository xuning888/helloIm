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

public final class DubboMsgStoreServiceTriple {

    public static final String SERVICE_NAME = MsgStoreService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, MsgStoreService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, Store.getDescriptor());
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
    com.github.xuning888.helloim.store.api.Store.SaveMessageRequest.class, com.github.xuning888.helloim.store.api.Store.SaveMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor saveMessageAsyncMethod = new StubMethodDescriptor("SaveMessage",
    com.github.xuning888.helloim.store.api.Store.SaveMessageRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor saveMessageProxyAsyncMethod = new StubMethodDescriptor("SaveMessageAsync",
    com.github.xuning888.helloim.store.api.Store.SaveMessageRequest.class, com.github.xuning888.helloim.store.api.Store.SaveMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.SaveMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.SaveMessageResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqMethod = new StubMethodDescriptor("MaxServerSeq",
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest.class, com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqAsyncMethod = new StubMethodDescriptor("MaxServerSeq",
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor maxServerSeqProxyAsyncMethod = new StubMethodDescriptor("MaxServerSeqAsync",
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest.class, com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageIdMethod = new StubMethodDescriptor("LastMessageId",
    com.github.xuning888.helloim.store.api.Store.LastMessageRequest.class, com.github.xuning888.helloim.store.api.Store.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageIdAsyncMethod = new StubMethodDescriptor("LastMessageId",
    com.github.xuning888.helloim.store.api.Store.LastMessageRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.LastMessageResponse::parseFrom);

    private static final StubMethodDescriptor lastMessageIdProxyAsyncMethod = new StubMethodDescriptor("LastMessageIdAsync",
    com.github.xuning888.helloim.store.api.Store.LastMessageRequest.class, com.github.xuning888.helloim.store.api.Store.LastMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.store.api.Store.LastMessageRequest::parseFrom,
    com.github.xuning888.helloim.store.api.Store.LastMessageResponse::parseFrom);

    static{
        serviceDescriptor.addMethod(saveMessageMethod);
        serviceDescriptor.addMethod(saveMessageProxyAsyncMethod);
        serviceDescriptor.addMethod(maxServerSeqMethod);
        serviceDescriptor.addMethod(maxServerSeqProxyAsyncMethod);
        serviceDescriptor.addMethod(lastMessageIdMethod);
        serviceDescriptor.addMethod(lastMessageIdProxyAsyncMethod);
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
        public com.github.xuning888.helloim.store.api.Store.SaveMessageResponse saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, saveMessageMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> saveMessageAsync(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, saveMessageAsyncMethod, request);
        }

        public void saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, saveMessageMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, maxServerSeqMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> maxServerSeqAsync(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request){
            return StubInvocationUtil.unaryCall(invoker, maxServerSeqAsyncMethod, request);
        }

        public void maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, maxServerSeqMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.LastMessageResponse lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, lastMessageIdMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> lastMessageIdAsync(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request){
            return StubInvocationUtil.unaryCall(invoker, lastMessageIdAsyncMethod, request);
        }

        public void lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, lastMessageIdMethod , request, responseObserver);
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
        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> saveMessageAsync(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request){
                return CompletableFuture.completedFuture(saveMessage(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> maxServerSeqAsync(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request){
                return CompletableFuture.completedFuture(maxServerSeq(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> lastMessageIdAsync(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request){
                return CompletableFuture.completedFuture(lastMessageId(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> responseObserver){
            saveMessageAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> responseObserver){
            maxServerSeqAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request, StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> responseObserver){
            lastMessageIdAsync(request).whenComplete((r, t) -> {
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
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/LastMessageId");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/LastMessageIdAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/LastMessageId");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/LastMessageIdAsync");
            BiConsumer<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse>> saveMessageFunc = this::saveMessage;
            handlers.put(saveMessageMethod.getMethodName(), new UnaryStubMethodHandler<>(saveMessageFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.SaveMessageRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse>> saveMessageAsyncFunc = syncToAsync(this::saveMessage);
            handlers.put(saveMessageProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(saveMessageAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse>> maxServerSeqFunc = this::maxServerSeq;
            handlers.put(maxServerSeqMethod.getMethodName(), new UnaryStubMethodHandler<>(maxServerSeqFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse>> maxServerSeqAsyncFunc = syncToAsync(this::maxServerSeq);
            handlers.put(maxServerSeqProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(maxServerSeqAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse>> lastMessageIdFunc = this::lastMessageId;
            handlers.put(lastMessageIdMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageIdFunc));
            BiConsumer<com.github.xuning888.helloim.store.api.Store.LastMessageRequest, StreamObserver<com.github.xuning888.helloim.store.api.Store.LastMessageResponse>> lastMessageIdAsyncFunc = syncToAsync(this::lastMessageId);
            handlers.put(lastMessageIdProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(lastMessageIdAsyncFunc));

            return new StubInvoker<>(this, url, MsgStoreService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.SaveMessageResponse saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request){
            throw unimplementedMethodException(saveMessageMethod);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request){
            throw unimplementedMethodException(maxServerSeqMethod);
        }

        @Override
        public com.github.xuning888.helloim.store.api.Store.LastMessageResponse lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request){
            throw unimplementedMethodException(lastMessageIdMethod);
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
