/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.network.trace.proto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.DnsNameResolverProvider;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Assert;
import org.skywalking.apm.network.proto.Downstream;
import org.skywalking.apm.network.proto.TraceSegmentServiceGrpc;
import org.skywalking.apm.network.proto.UpstreamSegment;

/**
 * @author wusheng
 */
public class GRPCNoServerTest {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannelBuilder<?> channelBuilder =
            NettyChannelBuilder.forAddress("127.0.0.1", 8080)
                .nameResolverFactory(new DnsNameResolverProvider())
                .maxInboundMessageSize(1024 * 1024 * 50)
                .usePlaintext(true);
        ManagedChannel channel = channelBuilder.build();
        TraceSegmentServiceGrpc.TraceSegmentServiceStub serviceStub = TraceSegmentServiceGrpc.newStub(channel);
        final Status[] status = {null};
        StreamObserver<UpstreamSegment> streamObserver = serviceStub.collect(new StreamObserver<Downstream>() {
            @Override public void onNext(Downstream value) {

            }

            @Override public void onError(Throwable t) {
                status[0] = ((StatusRuntimeException)t).getStatus();
            }

            @Override public void onCompleted() {

            }
        });

        streamObserver.onNext(null);
        streamObserver.onCompleted();

        Thread.sleep(2 * 1000);

        Assert.assertEquals(status[0].getCode(), Status.UNAVAILABLE.getCode());
    }
}
