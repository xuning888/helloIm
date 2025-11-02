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
import org.apache.dubbo.remoting.http12.HttpMethods;
import org.apache.dubbo.remoting.http12.rest.Mapping;
import org.apache.dubbo.rpc.stub.annotations.GRequest;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public interface MsgStoreService extends org.apache.dubbo.rpc.model.DubboStub {

    String JAVA_SERVICE_NAME = "com.github.xuning888.helloim.store.api.MsgStoreService";
    String SERVICE_NAME = "helloim.store.api.MsgStoreService";

    com.github.xuning888.helloim.store.api.Store.SaveMessageResponse saveMessage(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request);

    CompletableFuture<com.github.xuning888.helloim.store.api.Store.SaveMessageResponse> saveMessageAsync(com.github.xuning888.helloim.store.api.Store.SaveMessageRequest request);

    com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse maxServerSeq(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request);

    CompletableFuture<com.github.xuning888.helloim.store.api.Store.MaxServerSeqResponse> maxServerSeqAsync(com.github.xuning888.helloim.store.api.Store.MaxServerSeqRequest request);

    com.github.xuning888.helloim.store.api.Store.LastMessageResponse lastMessageId(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request);

    CompletableFuture<com.github.xuning888.helloim.store.api.Store.LastMessageResponse> lastMessageIdAsync(com.github.xuning888.helloim.store.api.Store.LastMessageRequest request);
}
