package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyConfigDetailVO", description="家庭配置信息")
public class FamilyConfigDetailVO {

    @ApiModelProperty(value = "楼层信息")
    private List<FamilyFloorConfigVO> floors;

    @ApiModelProperty(value = "终端信息")
    private List<FamilyTerminalPageVO> terminals;



}
