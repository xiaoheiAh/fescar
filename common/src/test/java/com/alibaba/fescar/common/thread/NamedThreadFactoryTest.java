/*
 *
 *  *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 *
 */

package com.alibaba.fescar.common.thread;


import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author melon.zhao
 * @since 2019/2/26
 */
public class NamedThreadFactoryTest {

    @Test
    public void testNewThread() {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory("testNameThread", 5);

        Thread testNameThread = namedThreadFactory
            .newThread(() -> System.out.println(Thread.currentThread().getName()));
        System.out.println(testNameThread.toString());
        assertThat(testNameThread.getName()).startsWith("testNameThread");
        assertThat(testNameThread.isDaemon()).isTrue();
    }


    @Test
    public void testThread() {
        ThreadPoolExecutor discard = new ThreadPoolExecutor(2, 4, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10),
            new DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            discard.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            System.out.println(MessageFormat.format("queue size:{0},active count:{1},core pool size:{2},task count:{3},queue remaining capacity:{4}",
                discard.getQueue().size(),discard.getActiveCount(),discard.getCorePoolSize(),discard.getTaskCount(),discard.getQueue().remainingCapacity()));
        }
    }
}