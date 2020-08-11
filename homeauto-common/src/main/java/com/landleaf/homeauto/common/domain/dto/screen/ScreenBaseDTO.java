package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * @ClassName ScreenBaseDTO
 * @Description: 内部服务下发数据到大屏基类
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenBaseDTO {
    /**
     * 消息Id
     */
    private String messageId;

    /**
     * 家庭编码
     */
    private String familyCode;
    /**
     * 大屏mac
     */
    private String screenMac;
    /**
     * 家庭方案
     */
    private String familyScheme;


}
