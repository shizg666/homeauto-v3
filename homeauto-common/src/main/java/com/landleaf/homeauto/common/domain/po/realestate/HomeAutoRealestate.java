package com.landleaf.homeauto.common.domain.po.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoRealestate对象", description="楼盘表")
public class HomeAutoRealestate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "楼盘名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "开发商code")
    private String developerCode;

    @ApiModelProperty(value = "开发商Name")
    private String developerName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "地址path")
    private String path;

    @ApiModelProperty(value = "path名称")
    private String pathName;

    @ApiModelProperty(value = "状态")
    private Integer status;


}
