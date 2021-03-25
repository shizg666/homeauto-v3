package com.landleaf.homeauto.center.device.model.smart.vo;

import com.landleaf.homeauto.common.constant.CommonConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName FamilyDeviceAttributeVO
 * @Description: TODO
 * @Author wyl
 * @Date 2021/1/7
 * @Version V2.0
 **/
@Builder
@Data
@ApiModel("app设备属性对象")
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDeviceAttributeVO {

    @ApiModelProperty("通信编码")
    private String attributeCode;

    @ApiModelProperty("简码：通信编码去掉协议、区域、设备等标记，只保留属性如:switch")
    private String shortCode;

    @ApiModelProperty("家庭ID")
    private String familyId;
    @ApiModelProperty("属性值")
    private String attributeValue;


    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
        if(StringUtils.isNotEmpty(this.attributeCode)&&this.attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)){
            String[] split = this.attributeCode.split(CommonConst.SymbolConst.UNDER_LINE);
            this.shortCode=split[split.length-1];
        }
    }

    public String getShortCode() {
        if(StringUtils.isNotEmpty(this.attributeCode)&&this.attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)){
            String[] split = this.attributeCode.split(CommonConst.SymbolConst.UNDER_LINE);
            this.shortCode=split[split.length-1];
        }
        return this.shortCode;
    }
}
