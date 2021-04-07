package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("house_template_device")
@ApiModel(value="TemplateDevice对象", description="户型设备表")
public class TemplateDeviceDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备编号（与协议相对应）")
    private String sn;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "app是否显示")
    private Integer showApp;

    @ApiModelProperty(value = "大屏是否显示")
    private Integer showScreen;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "控制区域")
    private String controlArea;

    @ApiModelProperty(value = "设备UI页面")
    private String uiCode;
    @ApiModelProperty(value = "设备图片")
    private String imageIcon;




}
