package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateDeviceDetailVO", description="户型设备")
public class TemplateDeviceDetailVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备编号（对应协议里面的）")
    private String sn;

//    @ApiModelProperty(value = "产品ID")
//    private String productId;

    @ApiModelProperty(value = "产品name")
    private String productName;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @NotEmpty(message = "户型ID不能为空")
    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "app是否显示")
    private Integer showApp;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "控制区域")
    private String controlArea;

    @ApiModelProperty(value = "设备UI页面")
    private String uiCode;

    @ApiModelProperty(value = "设备属性")
    private List<TemplateDeviceAttrVO> attrs;

}
