package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
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
@ApiModel(value="WebSceneDetailHvacConfigVO", description="查看-场景暖通设备配置信息")
public class WebSceneDetailHvacConfigVO {


    @ApiModelProperty("类别code")
    private String categoyCode;

    @ApiModelProperty(value = "设备号")
    private String 	deviceSn;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "风量下拉选择")
    private List<SelectedVO> windSpeeds;

//    @ApiModelProperty(value = "id主键")
//    private String id;

    @ApiModelProperty(value = "系统开关属性值")
    private String switchVal;

    @ApiModelProperty(value = "暖通动作")
    private WebSceneDetailHvacActionDTO hvacActionDTO;





}
