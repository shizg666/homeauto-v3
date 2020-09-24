package com.landleaf.homeauto.center.device.schedule.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyAuthorizationService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.JsonUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ProductErrorSchedule
 * @Description: 产品故障属性定时刷新任务
 * @Author shizg
 * @Date 2020/9/1
 * @Version V1.0
 **/
@Component
@Slf4j
public class ProductErrorSchedule {
    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;
    @Autowired
    private IProductAttributeErrorInfoService iProductAttributeErrorInfoService;
    @Autowired
    private RedisUtils redisUtils;


    @Scheduled(cron = "0 0/35 * * * *")
    public void saveData(){
        log.info("--------开始更新产品故障信息------");

        List<AttributeErrorDTO> data = iProductAttributeErrorService.getListCacheInfo();
        if (CollectionUtils.isEmpty(data)){
            return;
        }
        Map<String,List<ProductAttributeErrorInfo>> dataMap = null;
        List<String> errorIds = data.stream().filter(obj-> AttributeErrorTypeEnum.ERROR_CODE.getType().equals(obj.getType())).map(AttributeErrorDTO::getId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(errorIds)){
            List<ProductAttributeErrorInfo> errorInfos = iProductAttributeErrorInfoService.list(new LambdaQueryWrapper<ProductAttributeErrorInfo>().in(ProductAttributeErrorInfo::getErrorAttributeId,errorIds).select(ProductAttributeErrorInfo::getVal,ProductAttributeErrorInfo::getErrorAttributeId).orderByAsc(ProductAttributeErrorInfo::getSortNo));
            if(!CollectionUtils.isEmpty(errorInfos)){
                dataMap = errorInfos.stream().collect(Collectors.groupingBy(ProductAttributeErrorInfo::getErrorAttributeId));
            }
        }
        Map<String,String> dataCache = Maps.newHashMapWithExpectedSize(data.size());
        for (AttributeErrorDTO obj : data) {
            if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(obj.getType())) {
                if (dataMap != null && !CollectionUtils.isEmpty(dataMap.get(obj.getId()))){
                    obj.setDesc(dataMap.get(obj.getId()).stream().map(ProductAttributeErrorInfo::getVal).collect(Collectors.toList()));
                }
            }
            dataCache.put(String.format(RedisCacheConst.PRODUCT_ERROR_INFO, obj.getProductCode(), obj.getCode()), JsonUtil.beanToJson(obj));
        }
        redisUtils.pipleSet((RedisConnection connection)-> {
            dataCache.forEach((k,v)->{
                connection.set(k.getBytes(),v.getBytes());
            });
            return null;
        });
    }
}
