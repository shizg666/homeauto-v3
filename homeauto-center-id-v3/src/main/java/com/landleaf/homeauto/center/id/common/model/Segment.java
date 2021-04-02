package com.landleaf.homeauto.center.id.common.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;
/**
 * 号段
 * @author wenyilu
 */
@Data
public class Segment {
    private AtomicLong value = new AtomicLong(0);
    private volatile long max;
    private volatile int step;
    private SegmentBuffer buffer;

    /**
     * 空闲id个数
     * @return
     */
    public long getIdle() {
        return this.getMax() - getValue().get();
    }

    public Segment(SegmentBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Segment(");
        sb.append("value:");
        sb.append(value);
        sb.append(",max:");
        sb.append(max);
        sb.append(",step:");
        sb.append(step);
        sb.append(")");
        return sb.toString();
    }
}
