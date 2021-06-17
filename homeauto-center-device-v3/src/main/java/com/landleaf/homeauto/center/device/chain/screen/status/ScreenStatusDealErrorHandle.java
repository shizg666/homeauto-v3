package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorAttrValueBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.status.ScreenStatusDealErrorHandleService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @ClassName ScreenStatusDealErrorHandle
 * @Description: 处理故障信息
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealErrorHandle extends ScreenStatusDealHandle {

    @Autowired
    private IContactScreenService contactScreenService;
    @Resource
    private List<ScreenStatusDealErrorHandleService> screenStatusDealErrorHandleServices;
    @Autowired
    private Executor screenStatusDealErrorHandleExecutePool;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        if (checkCondition(dealComplexBO)) {
            Map<String, ScreenProductErrorAttrValueBO> errorValueMap = dealComplexBO.getAttrCategoryBOs().stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.ERROR_ATTR.getType()).collect(Collectors.toList()).stream()
                    .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().collect(Collectors.toMap(ScreenProductAttrBO::getAttrCode, ScreenProductAttrBO::getErrorAttrValue, (o, n) -> n));
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                String code = item.getCode();
                ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO = errorValueMap.get(code);
                if (screenProductErrorAttrValueBO != null) {
                    AttributeErrorTypeEnum errorTypeEnum = AttributeErrorTypeEnum.getInstByType(screenProductErrorAttrValueBO.getType());
                    ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
                    ScreenDeviceInfoStatusDTO familyDeviceInfoStatus = contactScreenService.getFamilyDeviceInfoStatus(dealComplexBO.getFamilyBO().getId(), deviceBO.getId());
                    screenStatusDealErrorHandleExecutePool.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (ScreenStatusDealErrorHandleService handleService : screenStatusDealErrorHandleServices) {
                                if (handleService.checkCondition(errorTypeEnum)) {
                                    handleService.handleErrorStatus(dealComplexBO, item, screenProductErrorAttrValueBO,
                                            familyDeviceInfoStatus);
                                }
                            }
                        }
                    });
                }
            }
        }
        nextHandle(dealComplexBO);
    }


    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        if (deviceBO.getSystemFlag() == FamilySystemFlagEnum.SYS_DEVICE.getType()) {
            //系统设备无需处理故障逻辑
            return false;
        }
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.ERROR_ATTR.getType()).findAny();
        return any.isPresent();
    }


    @PostConstruct
    public void init() {
        this.order = 3;
        this.handleName = this.getClass().getSimpleName();
    }

}
