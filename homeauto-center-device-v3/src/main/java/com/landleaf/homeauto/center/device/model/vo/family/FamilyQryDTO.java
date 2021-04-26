package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@ApiModel(value="FamilyQryDTO", description="FamilyQryDTO")
public class FamilyQryDTO extends BaseQry {

    @NotNull(message = "项目主键id不能为空")
    @ApiModelProperty(value = "项目id 必传")
    private Long projectId;

    @NotEmpty(message = "楼栋code不能为空")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @ApiModelProperty(value = "单元code")
    private String unitCode;


    @ApiModelProperty(value = "家庭编号")
    private String code;

    @ApiModelProperty(value = "楼层")
    private String floor;


    @ApiModelProperty(value = "房屋名称")
    private String name;


    @ApiModelProperty(value = "绑定状态 0未绑定 1已绑定")
    private Integer bindStatus;


//    @ApiModelProperty(value = "单元code")
//    private String unitCode;



//    @ApiModelProperty(value = "户型id")
//    private Long templateId;


}
