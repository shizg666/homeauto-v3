package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
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
@TableName("family_device")
public class FamilyDeviceDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("sn")
    @ApiModelProperty(value = "设备号")
    private String sn;

    @TableField("port")
    @ApiModelProperty(value = "485端口号")
    private String port;

    @TableField("sort_no")
    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @TableField("product_id")
    @ApiModelProperty(value = "产品ID")
    private String productId;

    @TableField("terminal_id")
    @ApiModelProperty(value = "通信终端ID")
    private String terminalId;

    @TableField("room_id")
    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @TableField("category_id")
    @ApiModelProperty(value = "品类")
    private String categoryId;

    @TableField("ip")
    @ApiModelProperty(value = "ip地址")
    private String ip;


}
