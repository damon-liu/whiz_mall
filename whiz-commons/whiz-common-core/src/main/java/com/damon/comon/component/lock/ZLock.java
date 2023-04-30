package com.damon.comon.component.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁对象抽象
 *
 * @author: damon.liu
 * @createDate: 2021/3/13
 */
@AllArgsConstructor
public class ZLock implements AutoCloseable {
    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}
