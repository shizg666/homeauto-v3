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
    private Long familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("首页暖通与房间按钮展示控制--0:都没有，1：只有暖通按钮，2：只有房间按钮，3：暖通与房间按钮都有")
    private Integer buttonControlFlag;

    public HomeAutoFamilyVO() {
    }

    public HomeAutoFamilyVO(Long familyId, String familyCode, String familyName) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
    }

    public HomeAutoFamilyVO(Long familyId, String familyCode, String familyName, Integer buttonControlFlag) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
        this.buttonControlFlag = buttonControlFlag;
    }
}
