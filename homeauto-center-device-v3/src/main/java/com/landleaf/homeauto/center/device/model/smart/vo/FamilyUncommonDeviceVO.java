package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
@Data
@ApiModel("家庭不常用设备视图对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyUncommonDeviceVO {

    @ApiModelProperty("房间位置")
    private String position;

    @ApiModelProperty("房间位置下的设备列表")
    private List<FamilyDeviceVO> deviceList;


}
