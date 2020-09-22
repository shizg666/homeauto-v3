package com.landleaf.homeauto.common.constant.enums;

/**
 * 定义topic的名称
 *
 * @author wenyilu
 */
public enum TopicEnumConst {


    /**
     * 云端==》大屏
     */
    CONTACT_SCREEN_CLOUD_TO_SCREEN("/screen/service/cloud/to/screen/", "云端通过此Topic向大屏发送消息"),

    /**
     * 大屏==》云端
     */
    CONTACT_SCREEN_SCREEN_TO_CLOUD("/screen/service/screen/to/cloud/", "云端通过此Topic向大屏发送消息"),

    /*
     * 系统部件检查是否链接正常的的topic
     */
    CHECK_CONN_TOPIC("/check/link/heart/beat", "系统部件检查是否链接正常的的topic"),



    ;

    /**
     * topic名称，支持统配符，+表示其中任一层为任意值，#只能放在最后，表示匹配所有
     */
    private String topic;

    /**
     * topic描述
     */
    private String desc;

    private TopicEnumConst(String topic, String desc) {
        this.topic = topic;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getDesc() {
        return desc;
    }
}
