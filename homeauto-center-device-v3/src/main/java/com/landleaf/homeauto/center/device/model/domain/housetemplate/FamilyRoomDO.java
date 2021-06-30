package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型房间表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_room")
@ApiModel(value="family_room", description="房间房间表")
public class FamilyRoomDO extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "户型房间ID")
    private Long templateRoomId;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;


}
