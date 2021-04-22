package com.landleaf.homeauto.center.device.schedule.config;//package com.landleaf.homeauto.center.device.schedule.product;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * 配置相关动态刷新
 */
@Component
@Slf4j
public class ConfigRefreshSchedule {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    private IProductAttributeErrorService productAttributeErrorService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IHomeAutoProjectService projectService;

    private  static Long LAST_UPDATE_DEVICE_TIME =  0l;
    private  static Long LAST_UPDATE_PRODUCT_TIME =  0l;
    private  static Long LAST_UPDATE_FAMILY_TIME =  0l;
    private  static Long LAST_UPDATE_PROJECT_TIME =  0l;


    @PostConstruct
    public void init(){
        LAST_UPDATE_DEVICE_TIME=System.currentTimeMillis();
        LAST_UPDATE_PRODUCT_TIME=System.currentTimeMillis();
        LAST_UPDATE_FAMILY_TIME=System.currentTimeMillis();
        LAST_UPDATE_PROJECT_TIME=System.currentTimeMillis();
    }


    @Scheduled(cron = "0 0/5 * * * *")
    public void configHouseTemplateDevice() {
        log.info("更新配置缓存时间:{}", DateFormatUtils.format(new Date(LAST_UPDATE_DEVICE_TIME),"yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("update_time", LocalDateTimeUtil.date2LocalDateTime(new Date(LAST_UPDATE_DEVICE_TIME)));
        int count = templateDeviceService.count(queryWrapper);
        if(count>0){
            List<TemplateDeviceDO> updateDevice = templateDeviceService.list(queryWrapper);
            for (TemplateDeviceDO templateDeviceDO : updateDevice) {
                String key = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE, templateDeviceDO.getHouseTemplateId(), templateDeviceDO.getSn(), templateDeviceDO.getId());
                if(redisUtils.hasKey(key)){
                    redisUtils.del(key);
                }
            }
        }
        LAST_UPDATE_DEVICE_TIME=System.currentTimeMillis();
    }
    @Scheduled(cron = "0 0/10 * * * *")
    public void configProductAttr() {
        QueryWrapper<HomeAutoProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("update_time", LocalDateTimeUtil.date2LocalDateTime(new Date(LAST_UPDATE_PRODUCT_TIME)));
        int count = productService.count(queryWrapper);
        QueryWrapper<ProductAttributeError> errorQueryWrapper = new QueryWrapper<>();
        errorQueryWrapper.ge("update_time", LocalDateTimeUtil.date2LocalDateTime(new Date(LAST_UPDATE_PRODUCT_TIME)));
        int errorUpdateCount = productAttributeErrorService.count(errorQueryWrapper);
        if(count>0||errorUpdateCount>0){
            List<HomeAutoProduct> autoProducts = productService.list(queryWrapper);
            for (HomeAutoProduct product : autoProducts) {
                String key = String.format(RedisCacheConst.CONFIG_PRODUCT_ATTR_CACHE, product.getCode());
                if(redisUtils.hasKey(key)){
                    redisUtils.del(key);
                }
            }
        }
        LAST_UPDATE_PRODUCT_TIME=System.currentTimeMillis();
    }
    @Scheduled(cron = "0 0/15 * * * *")
    public void configFamily() {
        QueryWrapper<HomeAutoFamilyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("update_time", LocalDateTimeUtil.date2LocalDateTime(new Date(LAST_UPDATE_FAMILY_TIME)));
        int count = familyService.count(queryWrapper);
        if(count>0){
            List<HomeAutoFamilyDO> familyDOS = familyService.list(queryWrapper);
            for (HomeAutoFamilyDO familyDO : familyDOS) {
                String familyKey = String.format(RedisCacheConst.CONFIG_FAMILY_CACHE, familyDO.getId());
                if(redisUtils.hasKey(familyKey)){
                    redisUtils.del(familyKey);
                }
                String screenMac = familyDO.getScreenMac();
                if(StringUtils.isEmpty(screenMac)){
                    String familyMacKey = String.format(RedisCacheConst.CONFIG_FAMILY_MAC_CACHE, familyDO.getScreenMac());
                    if(redisUtils.hasKey(familyMacKey)){
                        redisUtils.del(familyMacKey);
                    }
                    String macKey = String.format(RedisCacheConst.MAC_FAMILY, familyDO.getScreenMac());
                    if(redisUtils.hasKey(macKey)){
                        redisUtils.del(macKey);
                    }
                }
            }
        }
        LAST_UPDATE_FAMILY_TIME=System.currentTimeMillis();
    }
    @Scheduled(cron = "0 0/30 * * * *")
    public void configProject() {
        QueryWrapper<HomeAutoProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("update_time", LocalDateTimeUtil.date2LocalDateTime(new Date(LAST_UPDATE_PROJECT_TIME)));
        int count = projectService.count(queryWrapper);
        if(count>0){
            List<HomeAutoProject> projects = projectService.list(queryWrapper);
            for (HomeAutoProject project : projects) {
                String key = String.format(RedisCacheConst.CONFIG_PROJECT_CACHE, project.getCode());
                if(redisUtils.hasKey(key)){
                    redisUtils.del(key);
                }
            }
        }
        LAST_UPDATE_PROJECT_TIME=System.currentTimeMillis();
    }
}
