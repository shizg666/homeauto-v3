package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.vo.*;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@Slf4j
public class HomeAutoFamilyServiceImpl extends ServiceImpl<HomeAutoFamilyMapper, HomeAutoFamilyDO> implements IHomeAutoFamilyService {

    @Autowired
    private HomeAutoFamilyMapper homeAutoFamilyMapper;

    @Autowired
    private IFamilyUserService iFamilyUserService;

    @Autowired
    private IFamilyRoomService iFamilyRoomService;

    @Autowired
    private IFamilyFloorService iFamilyFloorService;

    @Autowired
    private IFamilyTerminalService iFamilyTerminalService;


    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private IProjectBuildingService iProjectBuildingService;
    @Autowired
    private IProjectBuildingUnitService iProjectBuildingUnitService;
    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHouseTemplateTerminalService iHouseTemplateTerminalService;

    @Autowired(required = false)
    private UserRemote userRemote;

    @Override
    public List<FamilyBO> getFamilyListByUserId(String userId) {
        return homeAutoFamilyMapper.getFamilyByUserId(userId);
    }

    @Override
    public String getWeatherCodeByFamilyId(String familyId) {
        return homeAutoFamilyMapper.getWeatherCodeByFamilyId(familyId);
    }

    @Override
    public FamilyInfoBO getFamilyInfoByTerminalMac(String mac, Integer terminal) {
        return homeAutoFamilyMapper.getFamilyInfoByTerminalMac(mac, terminal);
    }

    @Override
    public List<MyFamilyInfoVO> getListFamily() {
        String userId = "5ce32feb4c224b22ad5705bc7accf21d";
//        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getListFamilyInfo(TokenContext.getToken().getUserId());
        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getListFamilyInfo(userId);
        if (CollectionUtils.isEmpty(infoVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> familyIds = infoVOS.stream().map(MyFamilyInfoVO::getId).collect(Collectors.toList());
        List<CountBO> roomCount = iFamilyRoomService.getCountByFamilyIds(familyIds);
        List<CountBO> deviceCount = iFamilyDeviceService.getCountByFamilyIds(familyIds);
        List<CountBO> userCount = iFamilyUserService.getCountByFamilyIds(familyIds);
        Map<String, Integer> roomCountMap = roomCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        Map<String, Integer> deviceCountMap = deviceCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        Map<String, Integer> userCountMap = userCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        infoVOS.forEach(info -> {
            if (FamilyUserTypeEnum.MADIN.getType().equals(info.getType())) {
                info.setAdminFlag(1);
            } else {
                info.setAdminFlag(0);
            }
            if (roomCountMap.get(info.getId()) != null) {
                info.setRoomCount(roomCountMap.get(info.getId()));
            }
            if (deviceCountMap.get(info.getId()) != null) {
                info.setDeviceCount(deviceCountMap.get(info.getId()));
            }
            if (userCountMap.get(info.getId()) != null) {
                info.setUserCount(userCountMap.get(info.getId()));
            }
        });
        return infoVOS;
    }

    @Override
    public MyFamilyDetailInfoVO getMyFamilyInfo(String familyId) {
        MyFamilyDetailInfoVO result = new MyFamilyDetailInfoVO();
        List<FloorInfoVO> floors = this.baseMapper.getMyFamilyInfo(familyId);
        if (!CollectionUtils.isEmpty(floors)) {
            result.setFloors(floors);
        }
        List<FamilyUserInfoVO> userInfoVOS = this.baseMapper.getMyFamilyUserInfo(familyId);
        if (!CollectionUtils.isEmpty(userInfoVOS)) {
            List<String> userIds = userInfoVOS.stream().map(FamilyUserInfoVO::getUserId).collect(Collectors.toList());
            Response<List<HomeAutoCustomerDTO>> response = userRemote.getListByIds(userIds);
            if (!response.isSuccess()) {
                log.error("getMyFamilyInfo----userRemote.getListByIds ----获取用户信息失败：{}", response.getErrorMsg());
            }
            List<HomeAutoCustomerDTO> customerDTOS = response.getResult();
            if (CollectionUtils.isEmpty(customerDTOS)) {
                log.error("getMyFamilyInfo----userRemote.getListByIds ----获取用户信息为空：{}", response.toString());
            }
            Map<String, List<HomeAutoCustomerDTO>> collect = customerDTOS.stream().collect(Collectors.groupingBy(HomeAutoCustomerDTO::getId));
            userInfoVOS.forEach(user -> {
                if (FamilyUserTypeEnum.MADIN.getType().equals(user.getType())) {
                    user.setAdminFlag(1);
                } else {
                    user.setAdminFlag(0);
                }
                List<HomeAutoCustomerDTO> list = collect.get(user.getUserId());
                if (!CollectionUtils.isEmpty(list)) {
                    HomeAutoCustomerDTO customerDTO = list.get(0);
                    user.setName(customerDTO == null ? "" : customerDTO.getName());
                }
            });
            result.setUsers(userInfoVOS);
        }

        return result;
    }

    @Override
    public FamilyInfoForSobotDTO getFamilyInfoForSobotById(String familyId) {
        return this.baseMapper.getFamilyInfoForSobotById(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilyAddDTO request) {
        addCheck(request);
        String code = buildCode(request);
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request,HomeAutoFamilyDO.class);
        familyDO.setCode(code);
        familyDO.setId(IdGeneratorUtil.getUUID32());
        save(familyDO);
        saveTempalteConfig(request.getTemplateId(),familyDO.getId());
    }

    /**
     * 户型配置保存
     * @param templateId
     */
    private void saveTempalteConfig(String templateId,String familyId) {
        List<TemplateFloorDO> floorDOS = iHouseTemplateFloorService.list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId,templateId).select(TemplateFloorDO::getFloor,TemplateFloorDO::getName,TemplateFloorDO::getId));
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,templateId).select(TemplateRoomDO::getName,TemplateRoomDO::getFloorId,TemplateRoomDO::getHouseTemplateId,TemplateRoomDO::getType,TemplateRoomDO::getSortNo,TemplateRoomDO::getIcon,TemplateRoomDO::getId));
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,templateId));
        List<TemplateTerminalDO> terminalDOS = iHouseTemplateTerminalService.list(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId,templateId));


        Map<String, String> floorMap = copyFloor(floorDOS,familyId);
        Map<String, String> roomMap = copyRoom(roomDOS,floorMap,familyId);
        Map<String, String> terminalMap = copyTerminal(terminalDOS,familyId);
        copyDevice(deviceDOS,roomMap,terminalMap,familyId);
    }


    private Map<String, String> copyTerminal(List<TemplateTerminalDO> terminalDOS, String familyId) {
        if (CollectionUtils.isEmpty(terminalDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> terminalMap = Maps.newHashMapWithExpectedSize(terminalDOS.size());
        List<FamilyTerminalDO> data = Lists.newArrayListWithCapacity(terminalDOS.size());
        terminalDOS.forEach(terminal->{

            FamilyTerminalDO terminalDO = BeanUtil.mapperBean(terminal,FamilyTerminalDO.class);
            terminalDO.setFamilyId(familyId);
            terminalDO.setId(IdGeneratorUtil.getUUID32());
            terminalMap.put(terminal.getId(),terminalDO.getId());
            data.add(terminalDO);
        });
        iFamilyTerminalService.saveBatch(data);
        return terminalMap;
    }

    private void copyDevice(List<TemplateDeviceDO> deviceDOS, Map<String, String> roomMap, Map<String, String> terminalMap,String familyId) {
        if (CollectionUtils.isEmpty(deviceDOS)){
            return ;
        }
        List<FamilyDeviceDO> data = Lists.newArrayListWithCapacity(deviceDOS.size());
        deviceDOS.forEach(device->{
            FamilyDeviceDO deviceDO = BeanUtil.mapperBean(device,FamilyDeviceDO.class);
            deviceDO.setFamilyId(familyId);
            deviceDO.setRoomId(roomMap.get(device.getRoomId()));
            deviceDO.setTerminalId(terminalMap.get(device.getTerminalId()));
            data.add(deviceDO);
        });
        iFamilyDeviceService.saveBatch(data);
    }

    private Map<String, String> copyRoom(List<TemplateRoomDO> roomDOS, Map<String, String> floorMap, String familyId) {
        if (CollectionUtils.isEmpty(roomDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> roomMap = Maps.newHashMapWithExpectedSize(roomDOS.size());
        List<FamilyRoomDO> data = Lists.newArrayListWithCapacity(roomDOS.size());
        roomDOS.forEach(room->{
            FamilyRoomDO roomDO = BeanUtil.mapperBean(room,FamilyRoomDO.class);
            roomDO.setId(IdGeneratorUtil.getUUID32());
            roomDO.setFamilyId(familyId);
            room.setFloorId(floorMap.get(room.getFloorId()));
            floorMap.put(room.getId(),roomDO.getId());
            data.add(roomDO);
        });
        iFamilyRoomService.saveBatch(data);
        return roomMap;
    }

    private Map<String, String> copyFloor(List<TemplateFloorDO> floorDOS, String familyId) {
        if (CollectionUtils.isEmpty(floorDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> floorMap = Maps.newHashMapWithExpectedSize(floorDOS.size());
        List<FamilyFloorDO> data = Lists.newArrayListWithCapacity(floorDOS.size());
        floorDOS.forEach(floor->{
            FamilyFloorDO floorDO = BeanUtil.mapperBean(floor,FamilyFloorDO.class);
            floorDO.setId(IdGeneratorUtil.getUUID32());
            floorDO.setFamilyId(familyId);
            floorMap.put(floor.getId(),floorDO.getId());
            data.add(floorDO);
        });
        iFamilyFloorService.saveBatch(data);
        return floorMap;
    }

    /**
     * 生产家庭编号
     * @param request
     * @return
     */
    private String buildCode(FamilyAddDTO request) {
        String realestateNo = iHomeAutoRealestateService.getRealestateNoById(request.getRealestateId());
        String buildingNo = iProjectBuildingService.getBuildingNoById(request.getBuildingId());
        String unitNo = iProjectBuildingUnitService.getUnitNoById(request.getUnitId());
        return  new StringBuilder().append(realestateNo).append(buildingNo).append(unitNo).append(request.getRoomNo()).toString();
    }

    @Override
    public void update(FamilyUpdateDTO request) {
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request,HomeAutoFamilyDO.class);
        save(familyDO);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo 删除逻辑
        removeById(request.getId());

    }

    @Override
    public List<FamilyPageVO> getListByUnitId(String id) {
        return this.baseMapper.getListByUnitId(id);
    }

    @Override
    public void review(FamilyOperateDTO request) {
        HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
        familyDO.setId(request.getId());
        familyDO.setReviewStatus(FamilyReviewStatusEnum.REVIEW.getType());
        familyDO.setReviewTime(LocalDateTime.now());
        updateById(familyDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(FamilyOperateDTO request) {
        HomeAutoFamilyDO familyDO = getById(request.getId());
        if (familyDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在" );
        }
        if (FamilyDeliveryStatusEnum.DELIVERY.getType().equals(familyDO.getDeliveryStatus())){
            return;
        }
        if (FamilyReviewStatusEnum.UNREVIEW.getType().equals(familyDO.getReviewStatus())) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "家庭未审核不可交付");
        }

        HomeAutoFamilyDO obj = new HomeAutoFamilyDO();
        obj.setId(request.getId());
        obj.setDeliveryStatus(FamilyDeliveryStatusEnum.DELIVERY.getType());
        obj.setDeliveryTime(LocalDateTime.now());
        updateById(obj);
        iFamilyUserService.deleteOperation(request.getId());
    }

    @Override
    public FamilyDetailVO detail(String familyId) {
        return null;
    }

    private void addCheck(FamilyAddDTO request) {
//        int count = existRoomNo(request.getRoomNo(),request.getUnitId());
//        if (count >0){
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户号已存在");
//        }
    }

}
