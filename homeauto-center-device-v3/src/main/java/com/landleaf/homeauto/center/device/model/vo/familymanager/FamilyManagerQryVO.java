package com.landleaf.homeauto.center.device.model.vo.familymanager;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyManagerQryVO", description="FamilyManagerQryVO")
public class FamilyManagerQryVO extends BaseQry {

    @ApiModelProperty(value = "楼盘id")
    private Long realestateId;


    @ApiModelProperty(value = "楼栋单元家庭path")
    private List<String> paths;

//    @ApiModelProperty(value = "家庭主键id")
//    private Long familyId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户id2 手机号")
    private Long userId2;






}
