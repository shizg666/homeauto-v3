package com.landleaf.homeauto.common.constance;

/**
 * 定义mqtt的qos级别
 *
 * @author wenyilu
 */
public enum QosEnumConst {
    /*
     * 对于传感器数据等不重要信息，使用这个级别
     */
    QOS_0(0, "发送一次，不保证是否到达"),
    /*
     * 对于系统内部数据，可由逻辑自己控制频率，使用此level
     */
    QOS_1(1, "至少发送一次，保证至少一次到达目的地，包可能重复"),
    /*
     * 写操作或强校验操作
     */
    QOS_2(2, "仅一次，保证存在且仅存在一次信息到达目的地");

    /**
     * qos的级别定义
     */
    private int qosCode;

    /**
     * 该级别的描述信息
     */
    private String desc;

    /**
     * 私有
     */
    private QosEnumConst(int qosCode, String desc) {

        this.qosCode = qosCode;
        this.desc = desc;
    }

    public int getQosCode() {
        return qosCode;
    }

    public String desc() {
        return desc;
    }
}