package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.center.device.model.vo.scene.house.AtrributeRangeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.AttributeInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="值属性信息")
public class AttrInfoDTO {

    @ApiModelProperty(value = "属性名称")
    private String  name;

    @ApiModelProperty(value = "属性code")
    private String  code;
}
