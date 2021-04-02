package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import lombok.Data;

/**
 * @ClassName ScreenStatusDealHandle
 * @Description: 处理类
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Data
public class ScreenStatusDealHandle {

    public ScreenStatusDealHandle next;

    public Integer order;

    public String handleName;

    public void handle0(ScreenStatusDealComplexBO dealComplexBO){
        try {
            this.handle(dealComplexBO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(ScreenStatusDealComplexBO dealComplexBO){

    }

}
