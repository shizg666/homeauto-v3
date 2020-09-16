package com.landleaf.homeauto.center.device.excel.importfamily;

import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工程批量导入配置对象
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateQeyDTO", description="下载家庭批量导入模板请求对象")
public class HouseTemplateConfig {


    private List<TemplateFloorDO> floorDOS;


    private List<TemplateRoomDO> roomDOS;


    private List<TemplateDeviceDO> deviceDOS;


    private List<TemplateTerminalDO> terminalDOS;

    //场景主信息
    private List<HouseTemplateScene> templateScenes;

    //场景非暖通设备配置
    List<HouseTemplateSceneAction> sceneActions;

    //场景暖通设备动作配置
    List<HvacAction> hvacActions;

    //场景暖通面板动作配置
    List<HvacPanelAction> panelActions;

    //场景暖通设备配置
    List<HvacConfig> configs;



}
