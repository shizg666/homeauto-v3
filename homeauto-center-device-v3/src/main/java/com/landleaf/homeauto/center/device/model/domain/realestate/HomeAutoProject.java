package com.landleaf.homeauto.center.device.model.domain.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoProject对象", description="项目表")
public class HomeAutoProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态 0未锁定1已锁定")
    private Integer status;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址path")
    private String path;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "项目经理")
    private String projectManager;

    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "暖通协议主键id")
    private String protocolHvacId;

    @ApiModelProperty(value = "智能家居协议主键id")
    private String protocolAutoId;


}
