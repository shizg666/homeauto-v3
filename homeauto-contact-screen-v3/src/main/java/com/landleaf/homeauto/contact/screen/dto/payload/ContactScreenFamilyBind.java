package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

/**
 * @ClassName 大屏绑定消息体
 **/
@Data
public class ContactScreenFamilyBind {

    /**
     * 项目编码
     */
    private String projectCode;
    /**
     * 楼栋
     */
    private String buildingCode;
    /**
     * 单元
     */
    private String unitCode;
    /**
     * 单元
     */
    private String floor;
    /**
     * 房号
     */
    private String roomNo;



}
