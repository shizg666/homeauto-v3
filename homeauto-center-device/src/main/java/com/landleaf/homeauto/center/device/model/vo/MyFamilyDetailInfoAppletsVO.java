package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭视图对象
 * @author pilo
 */
@Data
@NoArgsConstructor
@ApiModel(value="MyFamilyDetailInfoAppletsVO", description="我的家庭详细信息对象")
public class MyFamilyDetailInfoAppletsVO extends MyFamilyDetailInfoVO {
    @ApiModelProperty("是否是管理员 0否 1是")
    private Integer adminFlag;


}
