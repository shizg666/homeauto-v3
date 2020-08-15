package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryDetailVO", description="品类详情对象")
public class CategoryDetailVO222222222 {


    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "品类类型")
    private Integer type;

    @ApiModelProperty(value = "品类类型")
    private String typeStr;

    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "性质: 只读，控制")
    private String natureStr;

    @ApiModelProperty(value = "协议")
    private Integer protocol;

    @ApiModelProperty(value = "协议")
    private String protocolStr;

    @ApiModelProperty(value = "波特率")
    private Integer baudRate;

    @ApiModelProperty(value = "波特率字符串")
    private String baudRateStr;

    @ApiModelProperty(value = "数据位")
    private String dataBit;

    @ApiModelProperty(value = "停止位")
    private String stopBit;

    @ApiModelProperty(value = "校验模式")
    private Integer checkMode;

    @ApiModelProperty(value = "校验模式字符串")
    private String checkModeStr;

    @ApiModelProperty(value = "属性信息")
    List<CategoryDetailAttributeVO> attributes;

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
        this.baudRateStr = BaudRateEnum.getInstByType(baudRate) != null? BaudRateEnum.getInstByType(baudRate).getName():"";
    }

    public void setCheckMode(Integer checkMode) {
        this.checkMode = checkMode;
        this.checkModeStr = CheckEnum.getInstByType(checkMode) != null? CheckEnum.getInstByType(checkMode).getName():"";
    }

    public void setType(Integer type) {
        this.type = type;
//        this.typeStr = CategoryTypeEnum.getInstByType(type) != null? CategoryTypeEnum.getInstByType(type).getName():"";
    }

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature) != null? AttributeNatureEnum.getInstByType(nature).getName():"";
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
//        this.protocolStr = ProtocolEnum.getInstByType(protocol) != null? ProtocolEnum.getInstByType(protocol).getName():"";
    }
}
