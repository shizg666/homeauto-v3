package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
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
@ApiModel("我的家庭视图对象")
public class MyFamilyVO {

    @ApiModelProperty("家庭名称")
    private String name;

    @ApiModelProperty("是否是管理员 0否1是")
    private Integer adminFlag;

    @ApiModelProperty("房间数")
    private int roomCount;

    @ApiModelProperty("设备数")
    private int deviceCount;

    @ApiModelProperty("用户数")
    private int userCount;

}
