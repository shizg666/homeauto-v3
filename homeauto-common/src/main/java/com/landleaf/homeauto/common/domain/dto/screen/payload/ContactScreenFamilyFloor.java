package com.landleaf.homeauto.common.domain.dto.screen.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 楼层信息
 **/
@Data
public class ContactScreenFamilyFloor {

    /**
     * 楼层主键
     */
    String id;
    /**
     * 楼层名称
     */
    String name;
    /**
     * 几楼
     */
    Integer order;
}
