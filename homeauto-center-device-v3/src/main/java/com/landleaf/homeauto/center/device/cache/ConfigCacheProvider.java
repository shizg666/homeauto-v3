package com.landleaf.homeauto.center.device.cache;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenProjectBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttribute;
import com.landleaf.homeauto.center.device.service.FloorRoomDeviceAttrProvider;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleAttrDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDeviceDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ConfigCacheProvider
 * @Description: 所有配置缓存提供类
 * @Author wyl
 * @Date 2021/4/2
 * @Version V1.0
 **/
@Component
public class ConfigCacheProvider extends BaseCacheProvider {
    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    private IHomeAutoProjectService projectService;
    @Autowired
    private ISysProductService sysProductService;
    @Autowired
    private ISysProductAttributeService sysProductAttributeService;

    @Autowired
    private IProjectHouseTemplateService projectHouseTemplateService;
    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;
    @Autowired
    private IHouseTemplateRoomService templateRoomService;
  @Autowired
  private FloorRoomDeviceAttrProvider floorRoomDeviceAttrProvider;
    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @param deviceId
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDevice(Long houseTemplateId, String deviceSn, Long deviceId) {
        String realKey = null;
        if (deviceId == null && StringUtils.isEmpty(deviceSn)) {
            throw new BusinessException("必須有一个");
        }
        String preKey = RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE_PRE;
        String key = String.format(preKey, houseTemplateId);
        Set<String> keys = redisUtils.keys(key.concat("*"));
        if (!CollectionUtils.isEmpty(keys)) {
            Optional<String> first = keys.stream().filter(i -> {
                boolean flag = true;
                String[] target = i.split(CommonConst.SymbolConst.COLON);
                if (!StringUtils.isEmpty(deviceSn)) {
                    if (!StringUtils.equals(deviceSn, target[3])) {
                        flag = false;
                    }
                }
                if (deviceId != null) {
                    if (!StringUtils.equals(String.valueOf(deviceId), target[4])) {
                        flag = false;
                    }
                }
                return flag;
            }).findFirst();
            if (first.isPresent()) {
                realKey = first.get();
                Object boFromRedis = getBoFromRedis(realKey, SINGLE_TYPE, ScreenTemplateDeviceBO.class);
                if (boFromRedis != null) {
                    return (ScreenTemplateDeviceBO) boFromRedis;
                }

            }
        }
        TemplateDeviceDO deviceDO = templateDeviceService.getDeviceByIdOrDeviceSn(houseTemplateId,
                deviceId, deviceSn);
        ScreenTemplateDeviceBO result = buildScreenDeviceBO(deviceDO);
        if (result != null) {
            realKey = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE, houseTemplateId, result.getDeviceSn(), result.getId());
            redisUtils.set(realKey, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        return result;
    }

    private ScreenTemplateDeviceBO buildScreenDeviceBO(TemplateDeviceDO deviceDO) {
        ScreenTemplateDeviceBO result = null;
        if (deviceDO != null) {
            result = new ScreenTemplateDeviceBO();
            BeanUtils.copyProperties(deviceDO, result);
            result.setDeviceSn(deviceDO.getSn());
            result.setSystemFlag(deviceDO.getSystemFlag());
            result.setHouseTemplateId(BeanUtil.convertLong2String(deviceDO.getHouseTemplateId()));
            result.setProductId(BeanUtil.convertLong2String(deviceDO.getProductId()));
            result.setRoomId(BeanUtil.convertLong2String(deviceDO.getRoomId()));
        }
        return result;
    }

    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @param deviceSn
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDeviceBySn(Long houseTemplateId, String deviceSn) {
        return getFamilyDevice(houseTemplateId, deviceSn, null);
    }

    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDeviceByDeviceId(Long houseTemplateId, Long deviceId) {
        return getFamilyDevice(houseTemplateId, null, deviceId);
    }


    /**
     * 产品属性缓存
     *
     * @param productCode
     * @return java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO>
     * @author wenyilu
     * @date 2021/4/2 11:19
     */
    public List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode) {
        return getDeviceAttrsByProductCode(productCode, null);
    }

    /**
     * 产品属性缓存
     *
     * @param productCode
     * @return java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO>
     * @author wenyilu
     * @date 2021/4/2 11:19
     */
    public List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode, Integer systemFlag) {

        String key = String.format(RedisCacheConst.CONFIG_PRODUCT_ATTR_CACHE, productCode);
        Object boFromRedis = getBoFromRedis(key, LIST_TYPE, ScreenProductAttrCategoryBO.class);
        if (boFromRedis != null) {
            return (List<ScreenProductAttrCategoryBO>) boFromRedis;
        }
        List<ScreenProductAttrCategoryBO> result = null;
        if (systemFlag == null || systemFlag.intValue() != FamilySystemFlagEnum.SYS_DEVICE.getType()) {
            result = productService.getAllAttrByCode(productCode);
        } else {
            result = sysProductService.getAllAttrByCode(productCode);
        }
        if (!CollectionUtils.isEmpty(result)) {
            redisUtils.set(key, result, RedisCacheConst.PRODUCT_ATTR_CACHE_EXPIRE);
        }
        return result;
    }


    /**
     * 获取家庭信息
     *
     * @param familyId
     * @return com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO
     * @author wenyilu
     * @date 2021/4/2 11:24
     */
    public ScreenFamilyBO getFamilyInfo(Long familyId) {
        String key = String.format(RedisCacheConst.CONFIG_FAMILY_CACHE, familyId);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, ScreenFamilyBO.class);
        if (boFromRedis != null) {
            return (ScreenFamilyBO) boFromRedis;
        }
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        if (familyDO != null) {
            ScreenFamilyBO result = new ScreenFamilyBO();
            BeanUtils.copyProperties(familyDO, result);
            result.setId(familyDO.getId());
            redisUtils.set(key, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
            return result;
        }
        return null;
    }

    /**
     * 獲取家庭信息
     *
     * @param mac
     * @return
     */
    public ScreenFamilyBO getFamilyInfoByMac(String mac) {
        String key = String.format(RedisCacheConst.CONFIG_FAMILY_MAC_CACHE, mac);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, String.class);
        Long familyId = null;
        if (boFromRedis != null) {
            if(boFromRedis instanceof String){
                familyId=Long.parseLong((String)boFromRedis);
            }else {
                familyId = (Long) boFromRedis;
            }
        }
        FamilyInfoBO familyInfoByTerminalMac = familyService.getFamilyInfoByTerminalMac(mac);

        if (familyInfoByTerminalMac != null) {
            familyId = familyInfoByTerminalMac.getFamilyId();
            redisUtils.set(key, familyInfoByTerminalMac.getFamilyId(), RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        if (!Objects.isNull(familyId)) {
            return getFamilyInfo(familyId);
        }
        return null;

    }

    public ScreenProjectBO getProject(String projectCode) {
        String key = String.format(RedisCacheConst.CONFIG_PROJECT_CACHE, projectCode);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, ScreenProjectBO.class);
        if (boFromRedis != null) {
            return (ScreenProjectBO) boFromRedis;
        }
        HomeAutoProject project = projectService.getByCode(projectCode);

        if (project != null) {
            ScreenProjectBO result = new ScreenProjectBO();
            result.setProjectId(project.getId());
            redisUtils.set(key, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
            return result;
        }
        return null;
    }


    public SysProductRelatedRuleDTO getHouseTemplateSystemRelated(Long houseTemplateId) {
        String key = String.format(RedisCacheConst.CONFIG_SYSTEM_PRODUCT_RELATED_CACHE, houseTemplateId);
        if (redisUtils.hasKey(key)) {
            Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, SysProductRelatedRuleDTO.class);
            if (boFromRedis != null) {
                return (SysProductRelatedRuleDTO) boFromRedis;
            }
        }

        ProjectHouseTemplate houseTemplate = projectHouseTemplateService.getById(houseTemplateId);
        if (houseTemplate == null) {
            return null;
        }
        List<TemplateDeviceDO> systemDevices = templateDeviceService.getSystemDevices(houseTemplateId,
                FamilySystemFlagEnum.SYS_DEVICE.getType(), FamilySystemFlagEnum.SYS_SUB_DEVICE.getType());
        if (CollectionUtils.isEmpty(systemDevices)) {
            return null;
        }
        // 获取系统产品关联规则
        Optional<TemplateDeviceDO> first = systemDevices.stream().filter(i -> i.getSystemFlag().intValue() == FamilySystemFlagEnum.SYS_DEVICE.getType()).findFirst();
        TemplateDeviceDO systemDevice = null;
        if(first.isPresent()){
            systemDevice= first.get();
        }
        if (systemDevice == null) {
            return null;
        }
        String sysProductCode = systemDevice.getProductCode();
        List<SysProductAttribute> sysProductAttributes = sysProductAttributeService.getByProductCode(sysProductCode);
        List<SysProductRelatedRuleAttrDTO> attrDTOS = Lists.newArrayList();
        for (SysProductAttribute sysProductAttribute : sysProductAttributes) {
            String code = sysProductAttribute.getCode();
            String categoryList = sysProductAttribute.getCategoryList();
            if (!StringUtils.isEmpty(categoryList)) {
                List<String> categorys = Arrays.asList(categoryList.split(","));
                //查找设备
                List<TemplateDeviceDO> relatedDevices = systemDevices.stream().filter(i -> categorys.contains(i.getCategoryCode())).collect(Collectors.toList());
                SysProductRelatedRuleAttrDTO attrDTO = SysProductRelatedRuleAttrDTO.builder().attrCode(code).relatedDevices(relatedDevices.stream().map(i -> {
                    return SysProductRelatedRuleDeviceDTO.builder().categoryCode(i.getCategoryCode()).deviceId(i.getId())
                            .deviceSn(i.getSn()).productCode(i.getProductCode()).systemFlag(i.getSystemFlag()).build();
                }).collect(Collectors.toList())).build();
                attrDTOS.add(attrDTO);
            }
        }
        SysProductRelatedRuleDTO relatedRuleDTO = SysProductRelatedRuleDTO.builder()
                .sysCategoryCode(systemDevice.getCategoryCode()).sysProductCode(sysProductCode)
                .houseTemplateId(houseTemplateId).sysDeviceId(systemDevice.getId())
                .projectId(houseTemplate.getProjectId()).realestateId(houseTemplate.getRealestateId()).sysAttrs(attrDTOS)
                .sysDeviceSn(systemDevice.getSn()).build();
        if (!Objects.isNull(relatedRuleDTO)) {
            redisUtils.set(key, relatedRuleDTO, RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        return relatedRuleDTO;
    }

    public List<HomeAutoProduct> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * @param: templateId
     * @description: 获取楼层房间设备缓存
     * @return: java.util.List<com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO>
     * @author: wyl
     * @date: 2021/6/3
     */
    public List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(Long templateId) {
        String key = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_ATTR_CACHE,templateId);
        if (redisUtils.hasKey(key)) {
            Object boFromRedis = getBoFromRedis(key, LIST_TYPE, ScreenHttpFloorRoomDeviceResponseDTO.class);
            if (boFromRedis != null) {
                return (List<ScreenHttpFloorRoomDeviceResponseDTO>) boFromRedis;
            }
        }

        List<ScreenHttpFloorRoomDeviceResponseDTO> floorRoomDeviceList = floorRoomDeviceAttrProvider.getFloorRoomDeviceList(templateId);
        if(!CollectionUtils.isEmpty(floorRoomDeviceList)){
            redisUtils.set(key,floorRoomDeviceList,RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        return floorRoomDeviceList;
    }

}
