package com.landleaf.homeauto.center.device.model.smart.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 家庭业务对象
 */
@Builder
@Data
@ApiModel("户式化APP家庭信息业务对象")
public class HomeAutoFamilyBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("户型编号")
    private String templateId;

    @ApiModelProperty("门牌号")
    private String familyNumber;

    @ApiModelProperty("户型名称")
    private String templateName;

    @ApiModelProperty("单元号")
    private String unitCode;

    @ApiModelProperty("楼栋号")
    private String buildingCode;

    @ApiModelProperty("项目ID")
    private String projectId;

    @ApiModelProperty("楼盘ID")
    private String realestateId;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("天气编码")
    private String weatherCode;

    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;

    @ApiModelProperty(value = "启用状态0 开启，1 禁用")
    private Integer enableStatus;

    public HomeAutoFamilyBO() {
    }

    public HomeAutoFamilyBO(String familyId, String familyCode, String familyName, String familyNumber) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
        this.familyNumber = familyNumber;
    }

    public HomeAutoFamilyBO(String familyId, String familyCode, String familyName, String templateId, String familyNumber) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
        this.templateId = templateId;
        this.familyNumber = familyNumber;
    }

    public HomeAutoFamilyBO(String familyId, String familyCode, String familyName, String templateId, String familyNumber, String templateName, String unitCode, String buildingCode, String projectId, String realestateId, String cityCode, String weatherCode, String screenMac, Integer enableStatus) {
        this.familyId = familyId;
        this.familyCode = familyCode;
        this.familyName = familyName;
        this.templateId = templateId;
        this.familyNumber = familyNumber;
        this.templateName = templateName;
        this.unitCode = unitCode;
        this.buildingCode = buildingCode;
        this.projectId = projectId;
        this.realestateId = realestateId;
        this.cityCode = cityCode;
        this.weatherCode = weatherCode;
        this.screenMac = screenMac;
        this.enableStatus = enableStatus;
    }
}
