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
 *
 */

package org.apache.skywalking.apm.collector.analysis.jvm.provider.worker.memorypool;

import org.apache.skywalking.apm.collector.analysis.jvm.define.graph.WorkerIdDefine;
import org.apache.skywalking.apm.collector.core.graph.Next;
import org.apache.skywalking.apm.collector.core.graph.NodeProcessor;
import org.apache.skywalking.apm.collector.core.util.Const;
import org.apache.skywalking.apm.collector.core.util.TimeBucketUtils;
import org.apache.skywalking.apm.collector.storage.table.jvm.MemoryPoolMetric;

/**
 * @author peng-yongsheng
 */
public class MemoryPoolMonthMetricTransformNode implements NodeProcessor<MemoryPoolMetric, MemoryPoolMetric> {

    @Override public int id() {
        return WorkerIdDefine.MEMORY_POOL_MONTH_METRIC_TRANSFORM_NODE_ID;
    }

    @Override public void process(MemoryPoolMetric memoryPoolMetric, Next<MemoryPoolMetric> next) {
        long timeBucket = TimeBucketUtils.INSTANCE.secondToMonth(memoryPoolMetric.getTimeBucket());

        MemoryPoolMetric newMemoryPoolMetric = MemoryPoolMetricCopy.copy(memoryPoolMetric);
        newMemoryPoolMetric.setId(String.valueOf(timeBucket) + Const.ID_SPLIT + memoryPoolMetric.getMetricId());
        newMemoryPoolMetric.setTimeBucket(timeBucket);
        next.execute(newMemoryPoolMetric);
    }
}
