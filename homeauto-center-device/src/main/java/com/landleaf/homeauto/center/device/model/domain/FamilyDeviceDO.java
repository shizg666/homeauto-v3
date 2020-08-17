package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.model.po.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭设备表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyDevice对象", description="家庭设备表")
public class FamilyDeviceDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "485端口号")
    private String port;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "通信终端ID")
    private String terminalId;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;


}
