package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.enums.realestate.RealestateStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Accessors(chain = true)
@ApiModel(value="RealestateVO", description="楼盘对象")
public class RealestateVO {


    private static final long serialVersionUID = -1083009607018779779L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "楼盘名称")
    private String name;

    @ApiModelProperty(value = "楼盘编号")
    private String num;

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

    @ApiModelProperty(value = "状态str")
    private String statusStr;

    @ApiModelProperty(value = "项目数量")
    private int projectCount;



    public void setStatus(Integer status) {
        this.status = status;
        this.statusStr = RealestateStatusEnum.getInstByType(status) != null? RealestateStatusEnum.getInstByType(status).getName():"";
    }
}
