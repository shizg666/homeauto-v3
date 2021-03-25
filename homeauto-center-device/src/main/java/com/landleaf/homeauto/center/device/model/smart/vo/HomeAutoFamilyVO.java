package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
@Data
@ApiModel("户式化APP家庭视图对象")
@Builder
public class HomeAutoFamilyVO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    public HomeAutoFamilyVO() {
    }

    public HomeAutoFamilyVO(String familyId, String familyCode, String familyName) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
    }
}
