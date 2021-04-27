package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

@Data
public class ScreenHttpFamilyBindDTO extends ScreenHttpRequestDTO {

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
     * 楼层
     */
    private String floor;
    /**
     * 房号
     */
    private String roomNo;

}
