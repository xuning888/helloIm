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

package com.github.xuning888.helloim.api.protobuf.message.v1;

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

public final class DubboMessageServiceTriple {

    public static final String SERVICE_NAME = MessageService.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME, MessageService.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME, MessageServiceProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboMessageServiceTriple::newStub);
        StubSuppliers.addSupplier(MessageService.JAVA_SERVICE_NAME,  DubboMessageServiceTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(MessageService.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("unchecked")
    public static MessageService newStub(Invoker<?> invoker) {
        return new MessageServiceStub((Invoker<MessageService>)invoker);
    }

    private static final StubMethodDescriptor pullOfflineMsgMethod = new StubMethodDescriptor("PullOfflineMsg",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor pullOfflineMsgAsyncMethod = new StubMethodDescriptor("PullOfflineMsg",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor pullOfflineMsgProxyAsyncMethod = new StubMethodDescriptor("PullOfflineMsgAsync",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor getLatestOfflineMessagesMethod = new StubMethodDescriptor("GetLatestOfflineMessages",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor getLatestOfflineMessagesAsyncMethod = new StubMethodDescriptor("GetLatestOfflineMessages",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor getLatestOfflineMessagesProxyAsyncMethod = new StubMethodDescriptor("GetLatestOfflineMessagesAsync",
    com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse::parseFrom);

    private static final StubMethodDescriptor cleanOfflineMessageMethod = new StubMethodDescriptor("CleanOfflineMessage",
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse::parseFrom);

    private static final StubMethodDescriptor cleanOfflineMessageAsyncMethod = new StubMethodDescriptor("CleanOfflineMessage",
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse::parseFrom);

    private static final StubMethodDescriptor cleanOfflineMessageProxyAsyncMethod = new StubMethodDescriptor("CleanOfflineMessageAsync",
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest.class, com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest::parseFrom,
    com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse::parseFrom);

    static{
        serviceDescriptor.addMethod(pullOfflineMsgMethod);
        serviceDescriptor.addMethod(pullOfflineMsgProxyAsyncMethod);
        serviceDescriptor.addMethod(getLatestOfflineMessagesMethod);
        serviceDescriptor.addMethod(getLatestOfflineMessagesProxyAsyncMethod);
        serviceDescriptor.addMethod(cleanOfflineMessageMethod);
        serviceDescriptor.addMethod(cleanOfflineMessageProxyAsyncMethod);
    }

    public static class MessageServiceStub implements MessageService, Destroyable {
        private final Invoker<MessageService> invoker;

        public MessageServiceStub(Invoker<MessageService> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, pullOfflineMsgMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> pullOfflineMsgAsync(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, pullOfflineMsgAsyncMethod, request);
        }

        public void pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, pullOfflineMsgMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, getLatestOfflineMessagesMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> getLatestOfflineMessagesAsync(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, getLatestOfflineMessagesAsyncMethod, request);
        }

        public void getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, getLatestOfflineMessagesMethod , request, responseObserver);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, cleanOfflineMessageMethod, request);
        }

        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> cleanOfflineMessageAsync(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request){
            return StubInvocationUtil.unaryCall(invoker, cleanOfflineMessageAsyncMethod, request);
        }

        public void cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> responseObserver){
            StubInvocationUtil.unaryCall(invoker, cleanOfflineMessageMethod , request, responseObserver);
        }
    }

    public static abstract class MessageServiceImplBase implements MessageService, ServerService<MessageService> {
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
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> pullOfflineMsgAsync(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
                return CompletableFuture.completedFuture(pullOfflineMsg(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> getLatestOfflineMessagesAsync(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
                return CompletableFuture.completedFuture(getLatestOfflineMessages(request));
        }

        @Override
        public CompletableFuture<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> cleanOfflineMessageAsync(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request){
                return CompletableFuture.completedFuture(cleanOfflineMessage(request));
        }

        // This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        // It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.

        public void pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver){
            pullOfflineMsgAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse> responseObserver){
            getLatestOfflineMessagesAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        public void cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse> responseObserver){
            cleanOfflineMessageAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<MessageService> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String, StubMethodHandler<?, ?>> handlers = new HashMap<>();
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/PullOfflineMsg");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/PullOfflineMsgAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/PullOfflineMsg");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/PullOfflineMsgAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/GetLatestOfflineMessages");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/GetLatestOfflineMessagesAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/GetLatestOfflineMessages");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/GetLatestOfflineMessagesAsync");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CleanOfflineMessage");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CleanOfflineMessageAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CleanOfflineMessage");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CleanOfflineMessageAsync");
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>> pullOfflineMsgFunc = this::pullOfflineMsg;
            handlers.put(pullOfflineMsgMethod.getMethodName(), new UnaryStubMethodHandler<>(pullOfflineMsgFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>> pullOfflineMsgAsyncFunc = syncToAsync(this::pullOfflineMsg);
            handlers.put(pullOfflineMsgProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(pullOfflineMsgAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>> getLatestOfflineMessagesFunc = this::getLatestOfflineMessages;
            handlers.put(getLatestOfflineMessagesMethod.getMethodName(), new UnaryStubMethodHandler<>(getLatestOfflineMessagesFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse>> getLatestOfflineMessagesAsyncFunc = syncToAsync(this::getLatestOfflineMessages);
            handlers.put(getLatestOfflineMessagesProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(getLatestOfflineMessagesAsyncFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse>> cleanOfflineMessageFunc = this::cleanOfflineMessage;
            handlers.put(cleanOfflineMessageMethod.getMethodName(), new UnaryStubMethodHandler<>(cleanOfflineMessageFunc));
            BiConsumer<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest, StreamObserver<com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse>> cleanOfflineMessageAsyncFunc = syncToAsync(this::cleanOfflineMessage);
            handlers.put(cleanOfflineMessageProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(cleanOfflineMessageAsyncFunc));

            return new StubInvoker<>(this, url, MessageService.class, handlers);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse pullOfflineMsg(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            throw unimplementedMethodException(pullOfflineMsgMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.OfflineMessageResponse getLatestOfflineMessages(com.github.xuning888.helloim.api.protobuf.message.v1.PullOfflineMsgRequest request){
            throw unimplementedMethodException(getLatestOfflineMessagesMethod);
        }

        @Override
        public com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgResponse cleanOfflineMessage(com.github.xuning888.helloim.api.protobuf.message.v1.CleanOfflineMsgRequest request){
            throw unimplementedMethodException(cleanOfflineMessageMethod);
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
