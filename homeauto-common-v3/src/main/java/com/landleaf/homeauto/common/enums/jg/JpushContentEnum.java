package com.landleaf.homeauto.common.enums.jg;
/**
 * @ClassName JpushContentEnum
 * @Description: 极光推送内容枚举
 * @Author ljy88
 * @Date 2020/4/28 
 * @Version V1.0
 **/
public enum JpushContentEnum {

    /**
     * 安防报警推送信息
     */
    ALARM_MSG(1, "安防报警推送信息", "安防报警"),


    DISARM_MSG(2, "安防消警推送信息", "安防消警"),
    ;



    private Integer type;

    private String description;

    private String title;


    JpushContentEnum(Integer type, String description, String title) {
        this.type = type;
        this.description = description;
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
