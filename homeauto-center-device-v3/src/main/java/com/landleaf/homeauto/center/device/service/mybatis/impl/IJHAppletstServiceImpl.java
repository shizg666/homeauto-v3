package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.AppService;
import com.landleaf.homeauto.center.device.service.AppletsService;
import com.landleaf.homeauto.center.device.service.common.FamilyWeatherService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.ThirdCustomerBindFamilyReqDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class IJHAppletstServiceImpl implements IJHAppletsrService {

    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private UserRemote userRemote;
    @Autowired
    private IFamilyUserService iFamilyUserService;
    @Autowired
    private FamilyWeatherService familyWeatherService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private AppletsService appletsService;
    @Autowired
    private IFamilyRoomService iFamilyRoomService;
    @Autowired
    private IFamilySceneService iFamilySceneService;
    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private AppService appService;

    public static final String JZ_CODE = "32040401";

    public static final String SWITCH_STR = "switch";
    public static final String SWITCH_ON = "on";

    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Value("${homeauto.websocket_path}")
    public String WEBSOCKET_ADDRESS ;
    //public static final String WEBSOCKET_ADDRESS = "wss://wechat.landleaf-ib.com:10445/websocket/endpoint/";
    public static final Integer ADD_UESR = 1;
    public static final Integer DELETE_USER = 2;


    @Override
    public void updateFamilyUser(JZFamilyUserDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        Long familyId = getFamilyIdByFloorUnit(qryDTO);
        CustomerInfoDTO customerInfoDTO = getOrSaveUserInfoByPhone(request.getUserPhone(),request.getName());
        if (ADD_UESR.equals(request.getOperateType())){
            FamilyUserDTO familyUserDTO = new FamilyUserDTO();
            familyUserDTO.setUserId(customerInfoDTO.getId());
            familyUserDTO.setType(request.getUserType());
            familyUserDTO.setFamilyId(familyId);
            familyUserDTO.setBindTime(LocalDateTimeUtil.nowformat(LocalDateTimeUtil.YYYY_MM_DD_HH_MM_SS));
            familyUserDTO.setCheckStatus(1);
            iFamilyUserService.addMember(familyUserDTO);
        }else {
            iFamilyUserService.removeThridFamilyUser(familyId,customerInfoDTO.getId());
        }
    }

    private CustomerInfoDTO getOrSaveUserInfoByPhone(String userPhone,String name) {
        ThirdCustomerBindFamilyReqDTO customer = new ThirdCustomerBindFamilyReqDTO();
        customer.setMobile(userPhone);
        if(StringUtil.isEmpty(name)){
            customer.setName(userPhone);
        }else {
            customer.setName(userPhone);
        }
        Response<CustomerInfoDTO> responseDTO = userRemote.bindFamilySaveThirdCustomer(customer);
        if (Objects.isNull(responseDTO)|| !responseDTO.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getCode()),ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getMsg());
        }
        CustomerInfoDTO result = responseDTO.getResult();
        if (Objects.isNull(result)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"用户获取不到:".concat(userPhone));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transferFamilyAdmin(JZFamilyUserAdminDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        Long familyId = getFamilyIdByFloorUnit(qryDTO);
        CustomerInfoDTO customerInfoDTO = getOrSaveUserInfoByPhone(request.getNewAdminPhone(),null);
        LambdaUpdateWrapper<FamilyUserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType()).eq(FamilyUserDO::getFamilyId,familyId).eq(FamilyUserDO::getUserId,customerInfoDTO.getId());
        LambdaUpdateWrapper<FamilyUserDO> updateWrapper2 = new LambdaUpdateWrapper<>();
        updateWrapper.set(FamilyUserDO::getType, FamilyUserTypeEnum.MEMBER.getType()).eq(FamilyUserDO::getFamilyId,familyId).eq(FamilyUserDO::getType,FamilyUserTypeEnum.MADIN.getType());
        iFamilyUserService.update(updateWrapper);
        iFamilyUserService.update(updateWrapper2);
    }

    @Override
    public OutDoorWeatherVO getOutDoorWeather(JZFamilyQryDTO request) {
        Long familyId = getFamilyIdByFloorUnit(request);
        HomeAutoFamilyBO homeAutoFamilyBO = iHomeAutoFamilyService.getHomeAutoFamilyBO(familyId);
        String weatherCode = homeAutoFamilyBO.getWeatherCode();
        if (StringUtils.isEmpty(weatherCode)) {
            throw new BusinessException(ErrorCodeEnumConst.WEATHER_NOT_FOUND);
        }
        // 获取天气信息
        WeatherBO weatherBO = familyWeatherService.getWeatherByWeatherCode(weatherCode);
        OutDoorWeatherVO result = BeanUtil.mapperBean(weatherBO,OutDoorWeatherVO.class);
        return result;
    }

    @Override
    public InDoorWeatherVO getInDoorWeather(JZFamilyQryDTO request) {
        Long familyId = getFamilyIdByFloorUnit(request);
        Long realestateId = iHomeAutoFamilyService.getTemplateIdById(familyId);
        TemplateDeviceDO deviceDO = iHouseTemplateDeviceService.getSensorDeviceSnByTId(realestateId);
        if (Objects.isNull(deviceDO)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"家庭没有配置传感器设备");
        }
        AppletsDeviceInfoVO status = appletsService.getDeviceStatus4AppletsVO(familyId,deviceDO.getId());
        List<AppletsAttrInfoVO> attrs = status.getAttrs();
        if (CollectionUtils.isEmpty(attrs)){
            return new InDoorWeatherVO();
        }
        Map<String,Object> attrMap = attrs.stream().collect(Collectors.toMap(AppletsAttrInfoVO::getCode,AppletsAttrInfoVO::getCurrentValue));
        InDoorWeatherVO inDoorWeatherVO = new InDoorWeatherVO();
        inDoorWeatherVO.setCo((String) attrMap.get("co2"));
        inDoorWeatherVO.setPm25((String) attrMap.get("pm25"));
        inDoorWeatherVO.setHumidity((String) attrMap.get("humidity"));
        inDoorWeatherVO.setTemp((String) attrMap.get("temp"));
        return inDoorWeatherVO;
    }

    @Override
    public JZFamilyRoomInfoVO getListRooms(JZFamilyQryDTO request) {
        JZFamilyRoomInfoVO result = new JZFamilyRoomInfoVO();
        Long familyId = getFamilyIdByFloorUnit(request);
        List<FamilyRoomDO> data = iFamilyRoomService.getListRooms(familyId);
        if (CollectionUtils.isEmpty(data)){
             result.setRoomNum(0);
             result.setRooms(Lists.newArrayListWithCapacity(0));
             return result;
        }
        List<JZRoomInfoVO> rooms = data.stream().map(room->{
            JZRoomInfoVO jzRoomInfoVO = new JZRoomInfoVO();
            jzRoomInfoVO.setName(room.getName());
            jzRoomInfoVO.setRoomId(room.getId());
            return jzRoomInfoVO;
        }).collect(Collectors.toList());
        result.setRooms(rooms);
        result.setRoomNum(rooms.size());
        return result;
    }

    @Override
    public void updateRoomName(JZRoomInfoVO request) {
        FamilyRoomDO familyRoomDO = new FamilyRoomDO();
        familyRoomDO.setId(request.getRoomId());
        familyRoomDO.setName(request.getName());
        iFamilyRoomService.updateById(familyRoomDO);
    }

    @Override
    public List<JZFamilySceneVO> getListScene(JZFamilyQryDTO request) {
        List<JZFamilySceneVO> result = Lists.newArrayList();
        Long familyId = getFamilyIdByFloorUnit(request);
        List<FamilyScene> familyScenes = iFamilySceneService.getListSceneByfId(familyId);
        if (!CollectionUtils.isEmpty(familyScenes)){
            List<JZFamilySceneVO> familySceneVOS = familyScenes.stream().map(fscene->{
                JZFamilySceneVO jzFamilySceneVO = new JZFamilySceneVO();
                jzFamilySceneVO.setSceneId(fscene.getId());
                jzFamilySceneVO.setSceneIcon(fscene.getIcon());
                jzFamilySceneVO.setDefaultFlag(0);
                jzFamilySceneVO.setSceneName(fscene.getName());
                return jzFamilySceneVO;
            }).collect(Collectors.toList());
            result.addAll(familySceneVOS);
        }
        Long templateId = iHomeAutoFamilyService.getTemplateIdById(familyId);
        List<HouseScenePageVO> houseScenePageVOS = iHouseTemplateSceneService.getListScene(templateId);
        if (!CollectionUtils.isEmpty(houseScenePageVOS)){
            List<JZFamilySceneVO> houseScenes = houseScenePageVOS.stream().map(fscene->{
                JZFamilySceneVO jzFamilySceneVO = new JZFamilySceneVO();
                jzFamilySceneVO.setSceneId(fscene.getId());
                jzFamilySceneVO.setSceneIcon(fscene.getIcon());
                jzFamilySceneVO.setDefaultFlag(1);
                jzFamilySceneVO.setSceneName(fscene.getName());
                return jzFamilySceneVO;
            }).collect(Collectors.toList());
            result.addAll(houseScenes);
        }

        return null;
    }

    @Override
    public void removeSceneById(JZDelFamilySceneDTO request) {
        FamilyScene scene= iFamilySceneService.getById(request.getSceneId());
        if (Objects.isNull(scene)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"默认场景不可删除".concat(String.valueOf(request.getSceneId())));
        }
        iFamilySceneService.removeById(request.getSceneId());
    }

    @Override
    public Long addScene(JZFamilySceneDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        Long familyId = getFamilyIdByFloorUnit(qryDTO);
        return iFamilySceneService.addScene(familyId,request);
    }



    @Override
    public void updateScene(JZFamilySceneDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        Long familyId = getFamilyIdByFloorUnit(qryDTO);
        iFamilySceneService.updateScene(familyId,request);
    }



    @Override
    public JZSceneDetailVO getDetailSceneById(Long sceneId) {
        return iFamilySceneService.getDetailBySceneId(sceneId);
    }

    @Override
    public List<JZDeviceStatusTotalVO> getDeviceStatusTotal(JZFamilyQryDTO request) {
        List<JZDeviceStatusTotalVO> result = Lists.newArrayList();
        FamilyBaseInfoBO family = getFamilyInfoByFloorUnit(request);

        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.getListDeviceDOByTeamplateId(family.getTemplateId());
        if (CollectionUtils.isEmpty(deviceDOS)){
            return null;
        }
        Map<String,List<TemplateDeviceDO>> dataMap = deviceDOS.stream().collect(Collectors.groupingBy(TemplateDeviceDO::getCategoryCode));
        dataMap.forEach((categoryCode,devices)->{
            JZDeviceStatusTotalVO totalVO = new JZDeviceStatusTotalVO();
            totalVO.setCategoryCode(categoryCode);
            totalVO.setCategoryName(CategoryTypeEnum.getInstByType(categoryCode).getName());
            int count = 0;
            for (TemplateDeviceDO device : devices) {
                Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(family.getCode(), device.getSn(), SWITCH_STR));
                if (SWITCH_ON.equals(attributeValue)) {
                    count++;
                }
            }
            totalVO.setSwitchOnNum(count);
            result.add(totalVO);
        });
        return result;
    }

    @Override
    public JZSceneConfigDataVO getRoomDeviceAttrInfo(JZFamilyQryDTO request) {
        FamilyBaseInfoBO family = getFamilyInfoByFloorUnit(request);
        return iFamilyRoomService.getRoomDeviceAttr(family.getId(),family.getTemplateId());
    }

    @Override
    public JZDeviceStatusCategoryVO getDeviceStatusByCategoryCode(JZDeviceStatusQryDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        FamilyBaseInfoBO family = getFamilyInfoByFloorUnit(qryDTO);
        JZDeviceStatusCategoryVO result =iFamilyRoomService.getRoomDeviceAttrByCategoryCode(family.getCode(),family.getId(),family.getTemplateId(),request.getCategoryCode());
        return result;
    }

    @Override
    public void deviceCommand(JzDeviceCommandDTO request) {

    }

    @Override
    public List<JZAlarmMessageVO> getListAlarm(JZFamilyQryDTO request) {
        return null;
    }

    @Override
    public String getWebSocketAddress(JZFamilyQryDTO request) {
        Long familyId = getFamilyIdByFloorUnit(request);
        return WEBSOCKET_ADDRESS.concat(String.valueOf(familyId)).concat("_capplets");
    }

    @Override
    public Long getFamilyIdByFloorUnit(JZFamilyQryDTO request) {
        Long realestateId = iHomeAutoRealestateService.getRealestateIdByCode(JZ_CODE);
        Long familyId = iHomeAutoFamilyService.getFamilyIdByQryObj(realestateId,request);
        if (Objects.isNull(familyId)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"家庭id获取不到:"+ JSON.toJSONString(request));
        }
        return familyId;
    }

    @Override
    public FamilyBaseInfoBO getFamilyInfoByFloorUnit(JZFamilyQryDTO request) {
        Long realestateId = iHomeAutoRealestateService.getRealestateIdByCode(JZ_CODE);
        FamilyBaseInfoBO family = iHomeAutoFamilyService.getFamilyInfoByQryObj(realestateId,request);
        if (Objects.isNull(family)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"家庭id获取不到:"+ JSON.toJSONString(request));
        }
        return family;
    }


    @Override
    public JZRoomDeviceStatusCategoryVO getDeviceStatusByRIdAndCategory(JZDeviceStatusQryDTO request) {
        JZFamilyQryDTO qryDTO = BeanUtil.mapperBean(request,JZFamilyQryDTO.class);
        FamilyBaseInfoBO family = getFamilyInfoByFloorUnit(qryDTO);
        return this.iFamilyRoomService.getDeviceStatusByRIdAndCategory(family.getCode(),family.getId(),family.getTemplateId(),request.getRoomId(),request.getCategoryCode());
    }

    @Override
    public void clearAlarms(JZFamilyQryDTO request) {

    }

    @Override
    public void executeScene(Long sceneId) {
        Long familyId = iFamilySceneService.getFamilyIdById(sceneId);
        appService.executeScene(familyId,sceneId);
    }
}
