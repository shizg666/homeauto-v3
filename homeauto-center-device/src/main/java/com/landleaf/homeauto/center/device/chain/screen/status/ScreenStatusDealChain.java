package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName ScreenStatusDealChain
 * @Description: 大屏上报状态数据处理链
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Data
@ApiModel(value = "ScreenStatusDealChain", description = "状态数据处理链")
public class ScreenStatusDealChain {

    private ScreenStatusDealHandle handle;
}
