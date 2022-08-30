package com.gaoice.distributed.scheduling;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoice
 */
public interface DistributedHandler {

    /**
     * @param key     定时任务 key
     * @param timeout 锁定时间
     * @param unit    时间单位
     * @return 是否已经处理过该定时任务
     */
    boolean lock(String key, long timeout, TimeUnit unit);
}
