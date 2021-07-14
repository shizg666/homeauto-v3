package com.landleaf.homeauto.center.device.chain.screen.status;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @ClassName ScreenStatusDealJHSwitchToAppHandle
 * @Description: 嘉宏设备状态开关统计状态推送
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealJHSwitchToAppHandle extends ScreenStatusDealHandle {

    //嘉宏楼盘code
    @Value("${homeauto.applets.jh_code}")
    public String JZ_CODE ;

    public static final String SWITCH_CODE = "switch";
    @Autowired
    private IJHAppletsrService ijhAppletsrService;
    @Autowired
    private ThreadPoolTaskExecutor bussnessExecutor;
    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        List<DeviceStatusBO> deviceStatusBOList = Lists.newArrayList();
        if (checkCondition(dealComplexBO)) {
            log.info("设备状态统计发送");
            CompletableFuture<Void> future = CompletableFuture.runAsync(()->ijhAppletsrService.sendJHSwitchTotalMessage(dealComplexBO.getFamilyBO().getTemplateId(),dealComplexBO.getFamilyBO().getId(),dealComplexBO.getFamilyBO().getCode()),bussnessExecutor);
        }
        nextHandle(dealComplexBO);
    }



    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        String familyCode = dealComplexBO.getFamilyBO().getCode();
        if (StringUtil.isEmpty(familyCode)){
            return false;
        }
        //判断是否是嘉宏的楼盘
        if (!familyCode.startsWith(JZ_CODE)){
            return false;
        }
        List<ScreenDeviceAttributeDTO> items = dealComplexBO.getUploadDTO().getItems();
        if (CollectionUtils.isEmpty(items)){
            return false;
        }
        List<String> ignoreCodes = dealComplexBO.getIgnoreCodes();
        if (!CollectionUtils.isEmpty(ignoreCodes)){
            Optional<String> any = ignoreCodes.stream().filter(i ->{
                return SWITCH_CODE.equals(i);
            } ).findAny();
            //switch 值不变则忽略
            return !any.isPresent();
        }
        //上报的属性信息
        Optional<ScreenDeviceAttributeDTO> switchany = items.stream().filter(i ->{
            return SWITCH_CODE.equals(i.getCode());
        } ).findAny();
        //根据缓存存储时的判定，判断是否需要存储 TODO
        return switchany.isPresent();
    }

    @PostConstruct
    public void init() {
        this.order=8;
        this.handleName=this.getClass().getSimpleName();
    }
}
