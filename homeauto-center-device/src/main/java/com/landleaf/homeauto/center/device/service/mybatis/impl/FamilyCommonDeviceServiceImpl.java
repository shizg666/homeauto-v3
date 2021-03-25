package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrAppFlagEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonDeviceMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyCommonDeviceSwitchVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭常用设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonDeviceServiceImpl extends ServiceImpl<FamilyCommonDeviceMapper, FamilyCommonDeviceDO> implements IFamilyCommonDeviceService {
    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;
    @Autowired
    private IDeviceAttrInfoService deviceAttrInfoService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;
    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;

    @Override
    public List<FamilyCommonDeviceDO> listByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonDeviceDO> commonDeviceQueryWrapper = new QueryWrapper<>();
        commonDeviceQueryWrapper.eq("family_id", familyId);
        commonDeviceQueryWrapper.isNotNull("device_id");
        commonDeviceQueryWrapper.ne("device_id", "");
        return list(commonDeviceQueryWrapper);
    }

    @Override
    public void deleteFamilyCommonDeviceList(String familyId) {
        QueryWrapper<FamilyCommonDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        remove(queryWrapper);
    }

    /**
     * APP保存常用设备
     *
     * @param familyId 家庭ID
     * @param devices  设备IDs
     * @return void
     * @author wenyilu
     * @date 2021/1/6 10:55
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommonDeviceList(String familyId, List<String> devices) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        // 1. 删除常用设备
        deleteFamilyCommonDeviceList(familyId);
        // 2. 添加新的常用设备
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = new LinkedList<>();
        for (int i = 0; i < devices.size(); i++) {
            FamilyCommonDeviceDO familyCommonSceneDO = new FamilyCommonDeviceDO();
            familyCommonSceneDO.setFamilyId(familyId);
            familyCommonSceneDO.setDeviceId(devices.get(i));
            familyCommonSceneDO.setSortNo(i);
            familyCommonSceneDO.setTemplateId(familyDO.getTemplateId());
            familyCommonDeviceDOList.add(familyCommonSceneDO);
        }
        // 3. 批量插入
        saveBatch(familyCommonDeviceDOList);
    }

    /**
     * 获取APP常用设备
     *
     * @param familyId   家庭ID
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO>
     * @author wenyilu
     * @date 2020/12/25 11:34
     */
    @Override
    public List<FamilyDeviceVO> getCommonDevicesByFamilyId4VO(String familyId, String templateId) {

        HomeAutoFamilyDO familyDO = familyService.getById(familyId);

        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        // app展示的设备
        List<TemplateDeviceDO> templateDevices = houseTemplateDeviceService.getTemplateDevices(templateId, CommonConst.Business.DEVICE_SHOW_APP_TRUE);

        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = listByFamilyId(familyId);

        if (!CollectionUtils.isEmpty(templateDevices) && !CollectionUtils.isEmpty(familyCommonDeviceDOList)) {
            List<FamilyDeviceBO> familyDeviceBOList = houseTemplateDeviceService.getFamilyDeviceWithIndex(familyId, templateId, templateDevices, familyCommonDeviceDOList, true);

            if (!CollectionUtils.isEmpty(familyDeviceBOList)) {
                familyDeviceVOList.addAll(familyDeviceBOList.stream().map(familyDeviceBO -> {
                    com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
                    familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
                    familyDeviceVO.setDeviceCode(familyDeviceBO.getDeviceCode());
                    familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
                    familyDeviceVO.setDeviceIcon(Optional.ofNullable(familyDeviceBO.getProductIcon()).orElse(""));
                    familyDeviceVO.setDeviceImage(Optional.ofNullable(familyDeviceBO.getProductImage()).orElse(""));
                    familyDeviceVO.setProductCode(familyDeviceBO.getProductCode());
                    familyDeviceVO.setCategoryCode(familyDeviceBO.getCategoryCode());
                    familyDeviceVO.setUiCode(familyDeviceBO.getUiCode());
                    familyDeviceVO.setDevicePosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));
                    familyDeviceVO.setDeviceIndex(familyDeviceBO.getDeviceIndex());

                    List<DeviceAttrInfo> attributes = deviceAttrInfoService.getAttributesByDeviceId(familyDeviceBO.getDeviceId(), null, AttrAppFlagEnum.ACTIVE.getCode());
                    FamilyCommonDeviceSwitchVO switchVO = FamilyCommonDeviceSwitchVO.builder().attributeCode(null).attributeValue(null).hasSwitch(false).build();
                    Optional<DeviceAttrInfo> first = attributes.stream().filter(i -> i.getCode().toLowerCase().contains(ProductPropertyEnum.SWITCH.code())).findFirst();
                    if (first.isPresent()) {
                        DeviceAttrInfo attrInfo = first.get();
                        // 有开关属性方才展示
                        Object deviceStatus = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), familyDeviceBO.getDeviceCode(), first.get().getCode()));

                        for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                            if (filter.checkFilter(attrInfo.getId(), attrInfo.getCode())) {
                                deviceStatus = filter.handle(deviceStatus, attrInfo.getId(), attrInfo.getCode());
                            }
                        }

                        switchVO.setAttributeCode(first.get().getCode());
                        String attrCode=first.get().getCode();
                        switchVO.setShortCode(attrCode.substring(attrCode.lastIndexOf(CommonConst.SymbolConst.UNDER_LINE)+1,attrCode.length()));
                        switchVO.setHasSwitch(true);
                        switchVO.setAttributeValue((String) deviceStatus);
                    }
                    familyDeviceVO.setShowSwitch(switchVO);
                    return familyDeviceVO;
                }).collect(Collectors.toList()));
            }
        }

        return familyDeviceVOList;
    }

    /**
     * APP获取不常用设备
     *
     * @param familyId 家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO>
     * @author wenyilu
     * @date 2021/1/7 13:54
     */
    @Override
    public List<FamilyUncommonDeviceVO> getUnCommonDevices4VO(String familyId) {
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyId);
        List<FamilyUncommonDeviceVO> familyUncommonDeviceVOList = new LinkedList<>();

        List<TemplateDeviceDO> templateDevices = houseTemplateDeviceService.getTemplateDevices(homeAutoFamilyDO.getTemplateId(), CommonConst.Business.DEVICE_SHOW_APP_TRUE);

        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = listByFamilyId(familyId);
        List<FamilyDeviceBO> uncommonDeviceBOList = houseTemplateDeviceService.getFamilyDeviceWithIndex(familyId, homeAutoFamilyDO.getTemplateId(), templateDevices, familyCommonDeviceDOList, false);

        List<FamilyRoomBO> familyRoomBOList = familyService.getFamilyRoomBOByTemplateAndFloor(familyId, homeAutoFamilyDO.getTemplateId(), null);

        if (CollectionUtils.isEmpty(familyRoomBOList)) {
            throw new BusinessException(ErrorCodeEnumConst.FLOOR_ROOM_EMPTY);
        }
        Map<String, List<FamilyDeviceBO>> familyDeviceMap = uncommonDeviceBOList.stream().collect(Collectors.groupingBy(FamilyDeviceBO::getDevicePosition));

        familyRoomBOList.sort(Comparator.comparing(FamilyRoomBO::getFloorName).thenComparing(Comparator.comparing(FamilyRoomBO::getRoomName)));
        for (FamilyRoomBO familyRoomBO : familyRoomBOList) {
            String position = String.format("%sF-%s", familyRoomBO.getFloorNum(), familyRoomBO.getRoomName());
            List<FamilyDeviceBO> familyDeviceBOS = familyDeviceMap.get(position);
            FamilyUncommonDeviceVO familyUncommonDeviceVO = new FamilyUncommonDeviceVO();
            familyUncommonDeviceVO.setPosition(position);
            List<FamilyDeviceVO> deviceList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(familyDeviceBOS)) {
                deviceList.addAll(familyDeviceBOS.stream().map(familyDeviceBO -> {
                    FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
                    familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
                    familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
                    familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
                    familyDeviceVO.setDevicePosition(familyDeviceBO.getDevicePosition());
                    familyDeviceVO.setDeviceIndex(familyDeviceBO.getDeviceIndex());
                    return familyDeviceVO;
                }).collect(Collectors.toList()));
            }
            familyUncommonDeviceVO.setDeviceList(deviceList);
            familyUncommonDeviceVOList.add(familyUncommonDeviceVO);
        }
        return familyUncommonDeviceVOList;

    }

}
