package com.doubao.merchant.server.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MerchantDelayQueue implements Delayed {


    // 数据的失效时间点
    private  long time;

    // 序号
    private  long seqno;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.time-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

}
