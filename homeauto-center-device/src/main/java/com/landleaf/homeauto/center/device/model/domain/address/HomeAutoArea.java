package com.landleaf.homeauto.center.device.model.domain.address;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 行政区表
 * </p>
 *
 * @author shizg
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoArea", description="行政区表")
public class HomeAutoArea extends BaseEntity {


    private static final long serialVersionUID = -4152855242937601926L;
    @ApiModelProperty(value = "省市县的名字")
    private String name;

    @ApiModelProperty(value = "行政区编码")
    private String code;

    @ApiModelProperty(value = "父编码")
    private String parentCode;

    private String parentName;

    @ApiModelProperty(value = "对应气象局中相应区域code")
    private String weatherCode;

    @ApiModelProperty(value = "对应气象局中相应区域名称")
    private String weatherName;

    @ApiModelProperty(value = "0国家 ，1省，2 市，3区")
    private String type;


}
