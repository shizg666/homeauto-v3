package com.landleaf.homeauto.center.device.model.vo.sys_product;

import com.landleaf.homeauto.common.enums.category.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统产品表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductVO", description="SysProductVO")
public class SysProductVO {


    @ApiModelProperty(value = "主键id 修改必传")
    private Long id;

    @ApiModelProperty(value = "系统产品code 后台自动生成")
    private String code;

    @ApiModelProperty(value = "系统产品名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * {@link StatusEnum}
     */
    @ApiModelProperty(value = "状态 0停用 1启用")
    private Integer status;

    @ApiModelProperty(value = "状态str")
    private String statusStr;

    @ApiModelProperty(value = "关联项目的数量")
    private Integer projectNum;

    @ApiModelProperty(value = "品类数量")
    private Integer categoryNum;

    @ApiModelProperty(value = "可选产品数量")
    private Integer selectProductNum;

    public void setStatus(Integer status) {
        this.status = status;
        this.statusStr = StatusEnum.getInstByType(status)!=null?StatusEnum.getInstByType(status).getName():"";
    }
}
