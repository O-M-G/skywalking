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

package org.skywalking.apm.collector.remote.grpc.service;

import org.skywalking.apm.collector.core.data.Data;
import org.skywalking.apm.collector.remote.grpc.proto.RemoteData;
import org.skywalking.apm.collector.remote.service.RemoteDeserializeService;

/**
 * @author peng-yongsheng
 */
public class GRPCRemoteDeserializeService implements RemoteDeserializeService<RemoteData> {

    @Override public void deserialize(RemoteData remoteData, Data data) {
        for (int i = 0; i < remoteData.getDataStringsCount(); i++) {
            data.setDataString(i, remoteData.getDataStrings(i));
        }
        for (int i = 0; i < remoteData.getDataIntegersCount(); i++) {
            data.setDataInteger(i, remoteData.getDataIntegers(i));
        }
        for (int i = 0; i < remoteData.getDataLongsCount(); i++) {
            data.setDataLong(i, remoteData.getDataLongs(i));
        }
        for (int i = 0; i < remoteData.getDataBooleansCount(); i++) {
            data.setDataBoolean(i, remoteData.getDataBooleans(i));
        }
        for (int i = 0; i < remoteData.getDataDoublesCount(); i++) {
            data.setDataDouble(i, remoteData.getDataDoubles(i));
        }
        for (int i = 0; i < remoteData.getDataBytesCount(); i++) {
            data.setDataBytes(i, remoteData.getDataBytes(i).toByteArray());
        }
    }
}
