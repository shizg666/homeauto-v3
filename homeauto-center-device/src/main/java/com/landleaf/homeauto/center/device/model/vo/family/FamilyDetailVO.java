package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyDetailVO", description="家庭详情对象")
public class FamilyDetailVO {

    @ApiModelProperty(value = "基本信息")
    private FamilyBaseInfoVO baseInfo;

    @ApiModelProperty(value = "配置信息")
    private FamilyConfigVO config;

    @ApiModelProperty(value = "大屏/网关信息信息")
    private List<TerminalInfoVO> terminal;

    @ApiModelProperty(value = "楼层房间设备配置")
    private List<FamilyFloorDetailVO> floor;

    @ApiModelProperty(value = "场景列表")
    private List<FamilyScenePageVO> scenes;




}
