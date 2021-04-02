package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
@Data
@ApiModel("獲取家庭所有設備時對象VO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyAllDeviceVO {

    @ApiModelProperty("房间位置")
    private String position;

    @ApiModelProperty("房间位置下的设备列表")
    private List<FamilyDeviceVO> deviceList;


}
