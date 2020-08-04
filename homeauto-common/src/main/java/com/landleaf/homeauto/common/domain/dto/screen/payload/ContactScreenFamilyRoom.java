package com.landleaf.homeauto.common.domain.dto.screen.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 房间信息
 **/
@Data
public class ContactScreenFamilyRoom {

    /**
     * 房间主键
     */
    private String id;

    /**
     * 楼层主键
     */
    private String floorId;
    /**
     * 名称
     */
    private String name;
    /**
     * 房间类型
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;

}
