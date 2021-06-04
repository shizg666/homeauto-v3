package com.landleaf.homeauto.center.device.service;

import com.alibaba.excel.util.DateUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.filter.sys.ISysAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneAppletsDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyAllDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoAppletsVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AppletsServiceImpl
 * @Description: TODO
 * @Author wyl
 * @Date 2021/3/19
 * @Version V1.0
 **/
@Service
public class AppletsServiceImpl implements AppletsService {
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;
    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;
    @Resource
    private List<ISysAttributeOutPutFilter> sysAttributeOutPutFilters;
    @Autowired
    private AttributeShortCodeConvertFilter attributeShortCodeConvertFilter;
    @Autowired
    private IFamilyUserService familyUserService;
    @Autowired
    private IContactScreenService contactScreenService;

    @Override
    public boolean saveTimingScene(TimingSceneAppletsDTO timingSceneDTO) {
        TimingSceneDTO sceneDTO = new TimingSceneDTO();
        BeanUtils.copyProperties(timingSceneDTO, sceneDTO);
        List<String> repeatValue = timingSceneDTO.getRepeatValue();
        Integer repeatType = timingSceneDTO.getRepeatType();
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(repeatValue)) {
            for (int i = 0; i < repeatValue.size(); i++) {
                String currentValue = repeatValue.get(i);
                if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(repeatType), FamilySceneTimingRepeatTypeEnum.WEEK)) {
                    sb.append(currentValue);
                    if (i != repeatValue.size() - 1) {
                        sb.append(EscapeCharacterConst.SPACE);
                    }
                }
                if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(repeatType), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
                    String convertValue = com.alibaba.excel.util.DateUtils.format(new Date(Long.parseLong(currentValue)), "yyyy.MM.dd");
                    sb.append(convertValue);
                    if (i != repeatValue.size() - 1) {
                        sb.append("-");
                    }
                }
            }
            sceneDTO.setRepeatValue(sb.toString());
        }
        return familySceneTimingService.saveTimingScene(sceneDTO);
    }

    @Override
    public AppletsDeviceInfoVO getDeviceStatus4AppletsVO(Long familyId, Long deviceId) {
        AppletsDeviceInfoVO result = new AppletsDeviceInfoVO();
        // 获取设备
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        result.setDeviceId(deviceDO.getId());
        result.setDeviceSn(deviceDO.getSn());
        result.setDeviceName(deviceDO.getName());
        result.setFamilyId(familyDO.getId());
        BeanUtils.copyProperties(deviceDO, result);
        result.setProductCode(deviceDO.getProductCode());
        result.setCategoryCode(deviceDO.getCategoryCode());
        List<ScreenProductAttrBO> functionAttrs = contactScreenService.getDeviceFunctionAttrsByProductCode(deviceDO.getProductCode());
        if (CollectionUtils.isEmpty(functionAttrs)) {
            return result;
        }

        List<AppletsAttrInfoVO> attrs = Lists.newArrayList();
        // 定义属性值处理过滤器
        for (ScreenProductAttrBO attrInfo : functionAttrs) {
            // 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), deviceDO.getSn(), attrInfo.getAttrCode()));
            AppletsAttrInfoVO attrInfoVO = new AppletsAttrInfoVO();
            BeanUtils.copyProperties(attrInfo, attrInfoVO);
            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    attrInfoVO = (AppletsAttrInfoVO) filter.handle(attributeValue, attrInfo, attrInfoVO);
                }
            }
            attrInfoVO.setCode(attrInfo.getAttrCode());
            attrs.add(attrInfoVO);
        }
        result.setAttrs(attrs);
        return result;
    }
    @Override
    public AppletsDeviceInfoVO getSystemStatus4AppletsVO(Long familyId) {
        AppletsDeviceInfoVO result = new AppletsDeviceInfoVO();

        // 获取系统设备
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        TemplateDeviceDO systemDevice = houseTemplateDeviceService.getSystemDevice(familyDO.getTemplateId());
        result.setDeviceId(systemDevice.getId());
        result.setDeviceSn(systemDevice.getSn());
        result.setDeviceName(systemDevice.getName());
        result.setFamilyId(familyDO.getId());
        result.setRoomId(systemDevice.getRoomId());
        BeanUtils.copyProperties(systemDevice, result);
        result.setProductCode(systemDevice.getProductCode());
        result.setCategoryCode(systemDevice.getCategoryCode());
        List<ScreenSysProductAttrBO> functionAttrs = contactScreenService.getSysDeviceFunctionAttrsByProductCode(systemDevice.getProductCode());

        if (CollectionUtils.isEmpty(functionAttrs)) {
            return result;
        }
        List<AppletsAttrInfoVO> attrs = Lists.newArrayList();
        // 定义属性值处理过滤器
        for (ScreenSysProductAttrBO attrInfo : functionAttrs) {
            // 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), systemDevice.getSn(), attrInfo.getAttrCode()));
            AppletsAttrInfoVO attrInfoVO = new AppletsAttrInfoVO();
            BeanUtils.copyProperties(attrInfo, attrInfoVO);
            for (ISysAttributeOutPutFilter filter : sysAttributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    attrInfoVO = (AppletsAttrInfoVO) filter.handle(attributeValue, attrInfo, attrInfoVO);
                }
            }
            attrInfoVO.setCode(attrInfo.getAttrCode());
            attrs.add(attrInfoVO);
        }
        result.setAttrs(attrs);
        return result;
    }

    @Override
    public AppletsSceneTimingDetailVO getTimingSceneDetail4Applets(Long timingId) {
        AppletsSceneTimingDetailVO result = new AppletsSceneTimingDetailVO();
        SceneTimingDetailVO timingSceneDetail = familySceneTimingService.getTimingSceneDetail(timingId);
        BeanUtils.copyProperties(timingSceneDetail, result);
        String repeatValue = timingSceneDetail.getRepeatValue();
        Integer repeatType = timingSceneDetail.getRepeatType();
        List<String> convertRepeatValue = Lists.newArrayList();
        if (!StringUtils.isEmpty(repeatValue)) {
            if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(repeatType), FamilySceneTimingRepeatTypeEnum.WEEK)) {
                convertRepeatValue.addAll(Collections.arrayToList(repeatValue.split(EscapeCharacterConst.SPACE)));
            }
            if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(repeatType), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
                List<String> arrayToList = Collections.arrayToList(repeatValue.split("-"));
                convertRepeatValue.addAll(arrayToList.stream().map(i -> {
                    String tmpReturn = (String) i;
                    try {
                        Date date = DateUtils.parseDate(tmpReturn, "yyyy.MM.dd");
                        tmpReturn = String.valueOf(date.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return tmpReturn;
                }).collect(Collectors.toList()));
            }
        }
        result.setRepeatValue(convertRepeatValue);
        return result;
    }

    @Override
    public MyFamilyDetailInfoAppletsVO getMyFamilyInfo4Applets(Long familyId, String userId) {
        MyFamilyDetailInfoAppletsVO result = new MyFamilyDetailInfoAppletsVO();
        MyFamilyDetailInfoVO myFamilyInfo4VO = familyService.getMyFamilyInfo4VO(familyId);
        if (myFamilyInfo4VO != null) {
            BeanUtils.copyProperties(myFamilyInfo4VO, result);
        }
        result.setAdminFlag(familyUserService.checkAdminReturn(familyId) ? 1 : 0);
        return result;
    }


}
