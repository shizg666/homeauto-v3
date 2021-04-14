package com.landleaf.homeauto.center.device.model.bo.screen;

import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 状态数据处理复合BO
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "ScreenStatusDealComplexBO", description = "状态数据处理复合BO")
public class ScreenStatusDealComplexBO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "上传的状态信息")
    private AdapterDeviceStatusUploadDTO uploadDTO;
    @ApiModelProperty(value = "设备信息")
    private ScreenTemplateDeviceBO deviceBO;
    @ApiModelProperty(value = "家庭信息")
    private ScreenFamilyBO familyBO;
    @ApiModelProperty(value = "产品属性信息")
    private List<ScreenProductAttrCategoryBO> attrCategoryBOs;

}
