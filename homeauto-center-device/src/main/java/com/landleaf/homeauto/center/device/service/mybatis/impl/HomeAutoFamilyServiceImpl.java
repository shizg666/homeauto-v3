package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.AirQualityEnum;
import com.landleaf.homeauto.center.device.model.bo.FamilyForAppBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.feign.WeatherServiceFeignClient;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class HomeAutoFamilyServiceImpl extends ServiceImpl<HomeAutoFamilyMapper, HomeAutoFamilyDO> implements IHomeAutoFamilyService {

    private HomeAutoFamilyMapper homeAutoFamilyMapper;

    private WeatherServiceFeignClient weatherServiceFeignClient;

    @Autowired
    private IFamilyUserService iFamilyUserService;
    @Autowired
    private IFamilyRoomService iFamilyRoomService;

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Override
    public FamilyVO getFamilyListByUserId(String userId) {
        List<FamilyForAppBO> familyForAppBOList = homeAutoFamilyMapper.getFamilyByUserId(userId);
        FamilyVO familyVO = new FamilyVO();
        for (FamilyForAppBO familyForAppBO : familyForAppBOList) {
            SimpleFamilyBO family = new SimpleFamilyBO();
            family.setFamilyId(familyForAppBO.getFamilyId());
            family.setFamilyName(familyForAppBO.getFamilyName());
            if (Objects.equals(familyForAppBO.getLastChecked(), 1)) {
                // 如果是最后一次选择的,就显示当前家庭
                // 这里做深拷贝,如果直接把family对象设值,会引起序列化问题
                SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
                simpleFamilyBO.setFamilyId(family.getFamilyId());
                simpleFamilyBO.setFamilyName(family.getFamilyName());
                familyVO.setCurrent(simpleFamilyBO);
            }
            if (Objects.nonNull(familyVO.getList())) {
                // 如果家庭列表不为空,就添加到
                familyVO.getList().add(family);
            } else {
                List<SimpleFamilyBO> tmpList = Lists.newArrayList();
                SimpleFamilyBO tmpBo = new SimpleFamilyBO();
                BeanUtils.copyProperties(family, tmpBo);
                tmpList.add(tmpBo);
                familyVO.setList(tmpList);
            }
        }
        return familyVO;
    }

    @Override
    public WeatherVO getWeatherByFamilyId(String familyId) {
        String weatherCode = homeAutoFamilyMapper.getWeatherCodeByFamilyId(familyId);
        WeatherBO weatherBO = weatherServiceFeignClient.getWeatherByWeatherCode(weatherCode).getResult();
        WeatherVO weatherVO = new WeatherVO();
        weatherVO.setWeatherStatus(weatherBO.getWeatherStatus());
        weatherVO.setTemp(weatherBO.getTemp());
        weatherVO.setMinTemp(weatherBO.getMinTemp());
        weatherVO.setMaxTemp(weatherBO.getMaxTemp());
        weatherVO.setPicUrl(weatherBO.getPicUrl());
        weatherVO.setAirQuality(AirQualityEnum.getAirQualityByPm25(Integer.parseInt(weatherBO.getPm25())).getLevel());
        return weatherVO;
    }

    @Override
    public FamilyInfoBO getFamilyInfoByTerminalMac(String mac, Integer terminal) {
        return homeAutoFamilyMapper.getFamilyInfoByTerminalMac(mac, terminal);
    }

    @Override
    public List<MyFamilyInfoVO> getListFamily() {
        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getListFamilyInfo(TokenContext.getToken().getUserId());
        if (CollectionUtils.isEmpty(infoVOS)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> familyIds = infoVOS.stream().map(MyFamilyInfoVO::getId).collect(Collectors.toList());
        List<CountBO> roomCount = iFamilyRoomService.getCountByFamilyIds(familyIds);
        List<CountBO> deviceCount = iFamilyDeviceService.getCountByFamilyIds(familyIds);
        List<CountBO> userCount = iFamilyUserService.getCountByFamilyIds(familyIds);
        Map<String,Integer> roomCountMap =  roomCount.stream().collect(Collectors.toMap(CountBO::getId,CountBO::getCount));
        Map<String,Integer> deviceCountMap =  deviceCount.stream().collect(Collectors.toMap(CountBO::getId,CountBO::getCount));
        Map<String,Integer> userCountMap =  userCount.stream().collect(Collectors.toMap(CountBO::getId,CountBO::getCount));
        infoVOS.forEach(info->{
            if (FamilyUserTypeEnum.MADIN.getType().equals(info.getType())){
                info.setAdminFlag(1);
            }else {
                info.setAdminFlag(0);
            }
            if (roomCountMap.get(info.getId()) != null){
                info.setRoomCount(roomCountMap.get(info.getId()));
            }
            if (deviceCountMap.get(info.getId()) != null){
                info.setDeviceCount(roomCountMap.get(info.getId()));
            }
            if (userCountMap.get(info.getId()) != null){
                info.setUserCount(roomCountMap.get(info.getId()));
            }
        });
        return infoVOS;
    }

    @Autowired
    public void setHomeAutoFamilyMapper(HomeAutoFamilyMapper homeAutoFamilyMapper) {
        this.homeAutoFamilyMapper = homeAutoFamilyMapper;
    }

    @Autowired
    public void setWeatherServiceFeignClient(WeatherServiceFeignClient weatherServiceFeignClient) {
        this.weatherServiceFeignClient = weatherServiceFeignClient;
    }
}
