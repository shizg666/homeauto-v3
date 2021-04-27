package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 大屏apk版本检查
 * @author wenyilu
 */
@Data
public class AdapterHttpFamilyBindDTO extends AdapterMessageHttpDTO {

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
