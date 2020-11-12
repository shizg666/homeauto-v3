package com.landleaf.homeauto.center.id.common.model;

import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 双buffer
 */
@Data
public class SegmentBuffer {
    private String key;
    /**
     * 双buffer
     */
    private Segment[] segments;
    /**
     * 当前的使用的segment的index
     */
    private volatile int currentPos;
    /**
     * 下一个segment是否处于可切换状态
     */
    private volatile boolean nextReady;
    /**
     * 是否初始化完成
     */
    private volatile boolean initOk;
    /**
     * 线程是否在运行中
     */
    private final AtomicBoolean threadRunning;
    private final ReadWriteLock lock;

    private volatile int step;
    private volatile int minStep;
    private volatile long updateTimestamp;

    /**
     * 获取当前用的哪一个号段
     * @return
     */
    public Segment getCurrent() {
        return segments[currentPos];
    }
    public Lock rLock() {
        return lock.readLock();
    }

    public Lock wLock() {
        return lock.writeLock();
    }

    /**
     * 下一个号段索引 只会为0、1
     * @return
     */
    public int nextPos() {
        return (currentPos + 1) % 2;
    }

    public void switchPos() {
        currentPos = nextPos();
    }

    public SegmentBuffer() {
         // 总的来说就是始终有两个号段
        segments = new Segment[]{new Segment(this), new Segment(this)};
        currentPos = 0;
        nextReady = false;
        initOk = false;
        threadRunning = new AtomicBoolean(false);
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SegmentBuffer{");
        sb.append("key='").append(key).append('\'');
        sb.append(", segments=").append(Arrays.toString(segments));
        sb.append(", currentPos=").append(currentPos);
        sb.append(", nextReady=").append(nextReady);
        sb.append(", initOk=").append(initOk);
        sb.append(", threadRunning=").append(threadRunning);
        sb.append(", step=").append(step);
        sb.append(", minStep=").append(minStep);
        sb.append(", updateTimestamp=").append(updateTimestamp);
        sb.append('}');
        return sb.toString();
    }

}
