package com.landleaf.homeauto.center.device.service;

import com.alibaba.excel.util.DateUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrAppFlagEnum;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
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
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private IDeviceAttrInfoService deviceAttrInfoService;
    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;
    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;
    @Autowired
    private AttributeShortCodeConvertFilter attributeShortCodeConvertFilter;
    @Autowired
    private IFamilyUserService familyUserService;
    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

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
    public AppletsDeviceInfoVO getDeviceStatus4AppletsVO(String familyId, String deviceId) {
        AppletsDeviceInfoVO result = new AppletsDeviceInfoVO();
        // 获取设备
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        result.setDeviceId(deviceDO.getId());
        result.setDeviceCode(deviceDO.getCode());
        result.setDeviceName(deviceDO.getName());
        result.setFamilyId(familyDO.getId());
        BeanUtils.copyProperties(deviceDO, result);
        result.setProductCode(productService.getById(deviceDO.getProductId()).getCode());
        List<DeviceAttrInfo> attrInfos = deviceAttrInfoService.getAttributesByDeviceId(deviceId, null, AttrAppFlagEnum.ACTIVE.getCode());
        if (CollectionUtils.isEmpty(attrInfos)) {
            return result;
        }
        List<AppletsAttrInfoVO> attrs = Lists.newArrayList();
        // 定义属性值处理过滤器
        for (DeviceAttrInfo attrInfo : attrInfos) {
            // 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), deviceDO.getCode(), attrInfo.getCode()));
            AppletsAttrInfoVO attrInfoVO = new AppletsAttrInfoVO();
            BeanUtils.copyProperties(attrInfo, attrInfoVO);
            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo.getId(), attrInfo.getCode())) {
                    attrInfoVO = (AppletsAttrInfoVO) filter.handle(attributeValue, attrInfo.getId(), attrInfo.getCode(), attrInfoVO);
                }
            }
            attrInfoVO.setCode(attributeShortCodeConvertFilter.convert(attrInfo.getCode()));
            attrs.add(attrInfoVO);
        }
        result.setAttrs(attrs);
        return result;
    }

    @Override
    public AppletsSceneTimingDetailVO getTimingSceneDetail4Applets(String timingId) {
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
    public MyFamilyDetailInfoAppletsVO getMyFamilyInfo4Applets(String familyId, String userId) {
        MyFamilyDetailInfoAppletsVO result = new MyFamilyDetailInfoAppletsVO();
        MyFamilyDetailInfoVO myFamilyInfo4VO = familyService.getMyFamilyInfo4VO(familyId);
        if (myFamilyInfo4VO != null) {
            BeanUtils.copyProperties(myFamilyInfo4VO, result);
        }
        result.setAdminFlag(familyUserService.checkAdminReturn(familyId) ? 1 : 0);
        return result;
    }

    @Override
    public List<FamilyAllDeviceVO> getAllDevices(String familyId) {

        return familyCommonDeviceService.getAllDevices4AppletsVO(familyId);
    }

}
