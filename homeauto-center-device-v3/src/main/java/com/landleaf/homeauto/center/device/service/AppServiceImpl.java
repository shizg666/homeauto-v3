package com.landleaf.homeauto.center.device.service;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.cache.FamilyCacheProvider;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.filter.sys.ISysAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.constant.FamilySceneTimingRepeatTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.maintenance.FamilyMaintenanceRecord;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgReadNote;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.*;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluseAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.bridge.IBridgeAppService;
import com.landleaf.homeauto.center.device.service.common.FamilyWeatherService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.EscapeCharacterConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.AppDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.ApiException;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.center.device.service.mybatis.impl.DicTagServiceImpl.SCENE_CION;
import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.NETWORK_ERROR;
import static com.landleaf.homeauto.common.web.context.TokenContextUtil.getUserIdForAppRequest;

/**
 * @className: AppServiceImpl
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/4
 **/
@Service
@Slf4j
public class AppServiceImpl implements AppService{

    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;
    @Autowired
    private IHomeAutoAlarmMessageService homeAutoAlarmMessageService;
    @Autowired
    private IHomeAutoAppVersionService homeAutoAppVersionService;
    @Autowired
    private IFamilyUserService familyUserService;
    @Autowired
    private IMsgNoticeService msgNoticeService;

    @Autowired
    private IMsgReadNoteService msgReadNoteService;
    @Autowired
    private IDicTagService dicTagService;
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;
    @Autowired
    private IContactScreenService contactScreenService;
    @Autowired
    private IFamilyMaintenanceRecordService familyMaintenanceRecordService;
    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;
    @Autowired
    private IHouseTemplateRoomService houseTemplateRoomService;
    @Autowired
    private IFamilyUserCheckoutService familyUserCheckoutService;
    @Autowired
    private FamilyWeatherService familyWeatherService;
    @Autowired
    private ITemplateFloorService templateFloorService;
    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;
    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;
    @Resource
    private List<ISysAttributeOutPutFilter> sysAttributeOutPutFilters;
    @Autowired(required = false)
    private UserRemote userRemote;
    @Autowired
    private ConfigCacheProvider configCacheProvider;
    @Autowired
    private FamilyCacheProvider familyCacheProvider;
    @Autowired
    private IBridgeAppService bridgeAppService;
    @Autowired
    private IHouseTemplateSceneService houseTemplateSceneService;
    @Autowired
    private IdService idService;
    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;
    /**
     * APP获取用户家庭列表及当前家庭
     *
     * @param userId 用户ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySelectVO
     * @author wenyilu
     * @date 2020/12/28 15:59
     */
    @Override
    public FamilySelectVO getUserFamily4VO(String userId) {
        FamilySelectVO result = new FamilySelectVO();
        List<HomeAutoFamilyBO> homeAutoFamilyBOList = familyService.listByUserId(userId, null);

        HomeAutoFamilyVO.HomeAutoFamilyVOBuilder currentBuilder = HomeAutoFamilyVO.builder();
        List<HomeAutoFamilyVO> familyVOList = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(homeAutoFamilyBOList)) {
            familyVOList = homeAutoFamilyBOList.stream().map(i -> {
                HomeAutoFamilyVO homeAutoFamilyVO = new HomeAutoFamilyVO();
                BeanUtils.copyProperties(i, homeAutoFamilyVO);
                // 获取是否包含系统设备|普通设备
                Integer hasDeviceType = houseTemplateDeviceService.checkDeviceType(i.getTemplateId());
                homeAutoFamilyVO.setButtonControlFlag(hasDeviceType);
                return homeAutoFamilyVO;
            }).collect(Collectors.toList());
            HomeAutoFamilyBO homeAutoFamilyBO = homeAutoFamilyBOList.get(0);

            currentBuilder.familyCode(homeAutoFamilyBO.getFamilyCode())
                    .familyId(homeAutoFamilyBO.getFamilyId())
                    .familyName(homeAutoFamilyBO.getFamilyName());

            // 从userCheckout中封装family
            FamilyUserCheckout familyUserCheckout = familyUserCheckoutService.getByUserId(userId);
            if (!Objects.isNull(familyUserCheckout)) {
                Optional<HomeAutoFamilyVO> firstOptional = familyVOList.stream()
                        .filter(i -> {
                            Long target = i.getFamilyId();
                            Long source = familyUserCheckout.getFamilyId();
                            if (target != null && source != null &&
                                    target.longValue() == source.longValue()) {
                                return true;
                            }
                            return false;
                        }).findFirst();
                boolean present = firstOptional.isPresent();
                if (present) {
                    HomeAutoFamilyVO homeAutoFamilyVO = firstOptional.get();
                    currentBuilder.familyCode(homeAutoFamilyVO.getFamilyCode())
                            .familyId(homeAutoFamilyVO.getFamilyId())
                            .familyName(homeAutoFamilyVO.getFamilyName())
                    .buttonControlFlag(homeAutoFamilyVO.getButtonControlFlag());
                }
            }
        }
        result.setCurrent(currentBuilder.build());
        result.setList(familyVOList);
        return result;
    }
    /**
     * 切換家庭
     * 获取APP首页信息（天气、常用设备、常用场景）
     *
     * @param userId   用戶ID
     * @param familyId 家庭ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO
     * @author wenyilu
     * @date 2021/1/12 10:04
     */
    @Override
    public FamilyCheckoutVO switchFamily(String userId, Long familyId) {
        // 更新切换家庭数据
        try {
            familyUserCheckoutService.saveOrUpdate(userId, familyId);
        } catch (Exception e) {
            log.error("切换家庭保存异常:{}",e.getMessage());
        }

        HomeAutoFamilyBO homeAutoFamilyBO = familyService.getHomeAutoFamilyBO(familyId);

        String weatherCode = homeAutoFamilyBO.getWeatherCode();
        if (StringUtils.isEmpty(weatherCode)) {
            throw new BusinessException(ErrorCodeEnumConst.WEATHER_NOT_FOUND);
        }
        // 获取天气信息
        FamilyWeatherVO familyWeatherVO = familyWeatherService.getWeatherByWeatherCode4VO(weatherCode);
        // 获取常用场景信息
        List<FamilySceneVO> familySceneVOList=familyCacheProvider.getCommonScenesByFamilyId4VO(familyId,homeAutoFamilyBO.getTemplateId());
        if (!CollectionUtils.isEmpty(familySceneVOList)) {
            familySceneVOList.sort(Comparator.comparing(FamilySceneVO::getSceneIndex));
        }

        return FamilyCheckoutVO.builder().weather(familyWeatherVO).commonSceneList(familySceneVOList).build();

    }
    /**
     * APP获取楼层及所属房间信息
     *
     * @param familyId 家庭ID
     * @param deviceFilterFlag
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyFloorVO>
     * @author wenyilu
     * @date 2020/12/25 13:24
     */
    @Override
    public List<FamilyFloorVO> getFamilyFloor4VO(Long familyId, Integer deviceFilterFlag) {
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyId);
        List<FamilyFloorVO> result = Lists.newArrayList();

        List<TemplateFloorDO> templateFloorDOList = templateFloorService.getFloorByTemplateId(homeAutoFamilyDO.getTemplateId());

        if (!CollectionUtils.isEmpty(templateFloorDOList)) {
            for (TemplateFloorDO templateFloorDO : templateFloorDOList) {
                List<FamilyRoomBO> familyRoomBOList = familyService.getFamilyRoomBOByTemplateAndFloor(familyId, homeAutoFamilyDO.getTemplateId(), templateFloorDO.getId(),deviceFilterFlag);
                if(CollectionUtils.isEmpty(familyRoomBOList)){
                    continue;
                }
                List<FamilyRoomVO> familyRoomVOList = Lists.newLinkedList();
                if (!CollectionUtils.isEmpty(familyRoomBOList)) {
                    familyRoomVOList.addAll(familyRoomBOList.stream().map(familyRoomBO -> {
                        FamilyRoomVO familyRoomVO = new FamilyRoomVO();
                        familyRoomVO.setRoomId(familyRoomBO.getRoomId());
                        familyRoomVO.setRoomName(familyRoomBO.getRoomName());
                        familyRoomVO.setRoomIcon(familyRoomBO.getRoomIcon1());
                        familyRoomVO.setImgApplets(familyRoomBO.getImgApplets());
                        familyRoomVO.setImgExpand(familyRoomBO.getImgExpand());
                        return familyRoomVO;
                    }).collect(Collectors.toList()));
                }
                FamilyFloorVO familyFloorVO = new FamilyFloorVO();
                familyFloorVO.setFloorId(templateFloorDO.getId());
                familyFloorVO.setFloorName(String.format("%sF", templateFloorDO.getFloor()));
                familyFloorVO.setRoomList(familyRoomVOList);
                familyFloorVO.setFamilyId(BeanUtil.convertLong2String(familyId));
                familyFloorVO.setTemplateId(BeanUtil.convertLong2String(homeAutoFamilyDO.getTemplateId()));
                result.add(familyFloorVO);
            }
        }
        return result;
    }
    /**
     * APP获取房间下所有设备
     *
     * @param familyId   家庭ID
     * @param roomId     房间ID
     * @param systemFlag 是否系统设备(1:是，0:否)
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO>
     * @author wenyilu
     * @date 2021/1/6 9:29
     */
    @Override
    public List<FamilyDeviceSimpleVO> getFamilyDevices4VO(Long familyId, Long roomId, Integer systemFlag) {
        List<FamilyDeviceSimpleVO> result = Lists.newArrayList();
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<FamilyDeviceSimpleBO> familyDeviceBOList = houseTemplateDeviceService.getFamilyRoomDevices
                (familyId, roomId, familyDO.getTemplateId(), systemFlag);
        if (!CollectionUtils.isEmpty(familyDeviceBOList)) {
            result.addAll(familyDeviceBOList.stream().map(familyDeviceBO -> {
                FamilyDeviceSimpleVO familyDeviceVO = new FamilyDeviceSimpleVO();
                familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
                familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
                familyDeviceVO.setCategoryCode(familyDeviceBO.getCategoryCode());
                familyDeviceVO.setProductCode(familyDeviceBO.getProductCode());
                familyDeviceVO.setSystemFlag(familyDeviceBO.getSystemFlag());
                familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
                familyDeviceVO.setDeviceImage(familyDeviceBO.getProductImage());
                return familyDeviceVO;
            }).collect(Collectors.toList()));
        }
        return result;
    }
    /**
     * APP获取我的家庭家庭列表統計信息
     * 获取家庭的统计信息：房间数、设备数、用户数
     *
     * @param userId 用户ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO>
     * @author wenyilu
     * @date 2020/12/28 16:23
     */
    @Override
    public List<MyFamilyInfoVO> getMyFamily4VO(String userId) {

        List<MyFamilyInfoVO> infoVOS =  familyService.getMyFamily(userId);

        if (CollectionUtils.isEmpty(infoVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<Long> templateIds = infoVOS.stream().map(i -> {
            return i.getTemplateId();
        }).collect(Collectors.toList());
        List<Long> familyIds = infoVOS.stream().map(i -> {
            return i.getId();
        }).collect(Collectors.toList());
        // 主键为户型
        List<CountBO> roomCount = houseTemplateRoomService.getCountByTemplateIds(templateIds);
        List<CountBO> deviceCount = houseTemplateDeviceService.getCountByTemplateIds(templateIds);
        // 主键为家庭
        List<CountBO> userCount = familyUserService.getCountByFamilyIds(familyIds);
        Map<Long, Integer> roomCountMap = Maps.newHashMap();
        Map<Long, Integer> deviceCountMap = Maps.newHashMap();
        Map<Long, Integer> userCountMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(roomCount)) {
            roomCountMap = roomCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        }
        if (!CollectionUtils.isEmpty(deviceCount)) {
            deviceCountMap = deviceCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        }
        if (!CollectionUtils.isEmpty(userCount)) {
            userCountMap = userCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        }
        for (MyFamilyInfoVO info : infoVOS) {
            if (FamilyUserTypeEnum.MADIN.getType().equals(info.getType())) {
                info.setAdminFlag(1);
            } else {
                info.setAdminFlag(0);
            }
            if (roomCountMap.get(info.getTemplateId()) != null) {
                info.setRoomCount(Optional.ofNullable(roomCountMap.get(info.getTemplateId())).orElse(0));
            }
            if (deviceCountMap.get(info.getTemplateId()) != null) {
                info.setDeviceCount(Optional.ofNullable(deviceCountMap.get(info.getTemplateId())).orElse(0));
            }
            if (userCountMap.get(info.getId()) != null) {
                info.setUserCount(Optional.ofNullable(userCountMap.get(info.getId())).orElse(0));
            }
        }
        return infoVOS;
    }
    /**
     * APP我的家庭-获取楼层房间设备信息
     * 获取某个家庭详情：楼层、房间、设备、用户信息等
     *
     * @param familyId 家庭ID
     * @return com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO
     * @author wenyilu
     * @date 2020/12/25 15:00
     */
    @Override
    public MyFamilyDetailInfoVO getMyFamilyInfo4VO(Long familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        MyFamilyDetailInfoVO result = new MyFamilyDetailInfoVO();
        List<FloorRoomVO> floors =familyCacheProvider.getFloorAndRoomDevices(familyDO.getTemplateId());
        if (!CollectionUtils.isEmpty(floors)) {
            result.setFloors(floors);
        }
        List<FamilyUserInfoVO> userInfoVOS = familyService.getMyFamilyUserInfo(familyId);
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
    /**
     * APP移除家庭成员
     * @param familuserDeleteVO 移除成员信息
     * @return void
     * @author wenyilu
     * @date  2020/12/28 17:07
     */
    @Override
    public void deleteFamilyMember(FamiluserDeleteVO familuserDeleteVO) {

        familyUserService.deleteFamilyMember(familuserDeleteVO);
    }
    /**
     *  退出家庭
     * @param familyId  家庭ID
     * @param userId    用户ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:30
     */
    @Override
    public void quitFamily(Long familyId, String userId) {
        familyUserService.quitFamily(familyId,userId);
    }
    /**
     * 扫码绑定家庭（渠道app/大屏）
     *
     * @param familyId type:familyId/家庭编号
     * @param userId
     * @return void
     * @author wenyilu
     * @date 2021/1/6 10:32
     */
    @Override
    public void addFamilyMember(String familyId, String userId) {
        if(!com.alibaba.excel.util.StringUtils.isEmpty(familyId)&&!familyId.contains(":")){
            familyId=String.format("1:%s",familyId);
        }
        String[] path = familyId.split(":");
        if (path.length != 2) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_DATA_EXIST.getCode()), "格式不对");
        }
        FamiluseAddDTO familuseAddDTO = new FamiluseAddDTO();
        familuseAddDTO.setFamily(path[1]);
        familuseAddDTO.setType(path[0]);
        familyUserService.addFamilyMember(familuseAddDTO, userId);
    }
    /**
     *  绑定家庭
     * @param familuseAddDTO   家庭ID/用户类型
     * @param userId            用户ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:33
     */
    @Override
    public void addFamilyMember(FamiluseAddDTO familuseAddDTO, String userId) {

        String familyId = "";
        if ("1".equals(familuseAddDTO.getType())) {
            familyId = familuseAddDTO.getFamily();
        } else if("3".equals(familuseAddDTO.getType())){
            HomeAutoFamilyDO familyDO = familyService.getFamilyByMac(familuseAddDTO.getFamily());
            if(familyDO==null){
                throw new BusinessException("mac尚未绑定家庭!");
            }
            familyId = String.valueOf(familyDO.getId());
        } else {
            HomeAutoFamilyDO familyDO = familyService.getFamilyByCode(familuseAddDTO.getFamily());
            familyId = String.valueOf(familyDO.getId());
        }
        familyUserService.addFamilyMemberById(BeanUtil.convertString2Long(familyId), userId);
    }
    /**
     *  通过APP设置管理员
     * @param familyUserOperateDTO  家庭Id、记录Id
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:40
     */
    @Override
    public void settingAdmin(FamilyUserOperateDTO familyUserOperateDTO) {
        familyUserService.checkAdmin(Long.valueOf(familyUserOperateDTO.getFamilyId()));
        List<FamilyUserDO> familyUserDOS = Lists.newArrayList();
        FamilyUserDO familyUserDO1 = new FamilyUserDO();
        familyUserDO1.setId(Long.valueOf(familyUserOperateDTO.getId()));
        familyUserDO1.setType(FamilyUserTypeEnum.MADIN.getType());
        familyUserDOS.add(familyUserDO1);
        Supplier<LambdaQueryWrapper> lastAdminQueryCondition = () -> {
            LambdaQueryWrapper<FamilyUserDO> lambdaQueryWrapper = new LambdaQueryWrapper<FamilyUserDO>()
                    .eq(FamilyUserDO::getFamilyId, BeanUtil.convertString2Long(familyUserOperateDTO.getFamilyId()))
                    .eq(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType());
            return lambdaQueryWrapper;
        };
        FamilyUserDO familyUserDO = familyUserService.getOne(lastAdminQueryCondition.get());
        if (familyUserDO != null) {
            familyUserDO.setType(FamilyUserTypeEnum.MEMBER.getType());
            familyUserDOS.add(familyUserDO);
        }
        familyUserService.updateBatchById(familyUserDOS);
    }
    @Override
    public void updateFamilyName(FamilyUpdateVO request) {
        familyUserService.checkAdmin(Long.valueOf(request.getId()));
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        familyService.updateById(familyDO);
    }

    /**
     * @param: familyId  家庭ID
     * @description: 获取家庭系统运行状态
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @author: wyl
     * @date: 2021/5/26
     */
    @Override
    public Map<String, Object> getSystemStatusVO(Long familyId) {
        Map<String, Object> systemStatusMap = new LinkedHashMap<>();
        // 获取系统设备
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        TemplateDeviceDO systemDevice = houseTemplateDeviceService.getSystemDevice(familyDO.getTemplateId());

        List<ScreenSysProductAttrBO> functionAttrs = contactScreenService.getSysDeviceFunctionAttrsByProductCode(systemDevice.getProductCode());

        if (CollectionUtils.isEmpty(functionAttrs)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        // 定义属性值处理过滤器
        for (ScreenSysProductAttrBO attrInfo : functionAttrs) {
            //// 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), systemDevice.getSn(), attrInfo.getAttrCode()));

            for (ISysAttributeOutPutFilter filter : sysAttributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    attributeValue = filter.appGetStatusHandle(attributeValue, attrInfo);
                }
            }
            systemStatusMap.put(attrInfo.getAttrCode(), attributeValue);
        }
        return systemStatusMap;
    }
    /**
     * 获取家庭设备属性状态
     *
     * @param familyId 家庭ID
     * @param deviceId 设备ID
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author wenyilu
     * @date 2021/1/6 13:49
     */
    @Override
    public Map<String, Object> getDeviceStatus4VO(Long familyId, Long deviceId) {
        Map<String, Object> deviceStatusMap = new LinkedHashMap<>();
        // 获取设备
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        List<ScreenProductAttrBO> functionAttrs = contactScreenService.getDeviceFunctionAttrsByProductCode(deviceDO.getProductCode());

        if (CollectionUtils.isEmpty(functionAttrs)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        // 定义属性值处理过滤器
        for (ScreenProductAttrBO attrInfo : functionAttrs) {
            //// 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), deviceDO.getSn(), attrInfo.getAttrCode()));

            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    attributeValue = filter.appGetStatusHandle(attributeValue, attrInfo);
                }
            }

            deviceStatusMap.put(attrInfo.getAttrCode(), attributeValue);
        }
        return deviceStatusMap;
    }
    @Override
    public AdapterDeviceStatusReadAckDTO readDeviceStatus(Long familyId, Long deviceId) {
        if (Objects.isNull(familyId)) {
            FamilyUserCheckout userCheckout = familyUserCheckoutService.getByUserId(getUserIdForAppRequest());
            if (userCheckout != null) {
                familyId = userCheckout.getFamilyId();
            }
        }
        ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(familyId);
        if (familyInfo == null || StringUtils.isEmpty(familyInfo.getScreenMac())) {
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        ScreenTemplateDeviceBO deviceBO = configCacheProvider.getFamilyDeviceByDeviceId(familyInfo.getTemplateId(), deviceId);
        if (deviceBO == null) {
            throw new BusinessException(ErrorCodeEnumConst.DEVICE_NOT_FOUND);
        }
        log.info("指令信息获取完毕, 准备发送");
        AdapterDeviceStatusReadDTO readDTO = new AdapterDeviceStatusReadDTO();
        readDTO.buildBaseInfo(familyId, familyInfo.getCode(), familyInfo.getTemplateId(), familyInfo.getScreenMac(), System.currentTimeMillis());
        readDTO.setProductCode(deviceBO.getProductCode());
        readDTO.setDeviceSn(Integer.parseInt(deviceBO.getDeviceSn()));
        readDTO.setSystemFlag(deviceBO.getSystemFlag());
        AdapterDeviceStatusReadAckDTO statusReadAckDTO = bridgeAppService.deviceStatusRead(readDTO);
        if (Objects.isNull(statusReadAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else if (!Objects.equals(statusReadAckDTO.getCode(), 200)) {
            throw new BusinessException(statusReadAckDTO.getMessage());
        }
        return statusReadAckDTO;
    }
    /**
     *  App获取家庭消息列表
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO>
     * @author wenyilu
     * @date 2021/1/12 13:27
     */
    @Override
    public List<MsgNoticeAppDTO> getMsgList4VO(Long familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        if (familyDO == null){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<MsgNoticeAppDTO> data = msgNoticeService.getMsglist(familyDO.getProjectId());
        List<String> msgIds = msgReadNoteService.getListUserAndType(TokenContext.getToken().getUserId(), MsgTypeEnum.NOTICE.getType());
        if (CollectionUtils.isEmpty(msgIds)){
            return data;
        }
        data.stream().forEach(obj->{
            if (msgIds.contains(obj.getMsgId())){
                obj.setReadFlag(1);
            }
        });
        return data;
    }
    /**
     *  添加消息已读记录
     * @param msgReadNoteDTO  消息
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:29
     */
    @Override
    public void addReadNote(MsgReadNoteDTO msgReadNoteDTO) {
        MsgReadNote msgReadNote = BeanUtil.mapperBean(msgReadNoteDTO,MsgReadNote.class);
        msgReadNote.setUserId(TokenContext.getToken().getUserId());
        msgReadNoteService.save(msgReadNote);
    }
    /**
     *  APP保存常用场景
     * @param familyId  家庭ID
     * @param sceneIds 场景Ids
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommonSceneList(Long familyId, List<Long> sceneIds) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        // 1. 删除家庭常用场景
        familyCommonSceneService.deleteByFamilyId(familyId);

        // 2. 再把新的常用场景添加进去
        if(!CollectionUtils.isEmpty(sceneIds)) {
            List<FamilyCommonSceneDO> familyCommonSceneList = new LinkedList<>();
            for (int i = 0; i < sceneIds.size(); i++) {
                Long sceneId = sceneIds.get(i);
                FamilyCommonSceneDO familyCommonSceneDO = new FamilyCommonSceneDO();
                familyCommonSceneDO.setFamilyId(familyId);
                familyCommonSceneDO.setSceneId(sceneId);
                familyCommonSceneDO.setSortNo(i);
                familyCommonSceneDO.setTemplateId(familyDO.getTemplateId());
                familyCommonSceneList.add(familyCommonSceneDO);
            }
            familyCommonSceneService.saveBatch(familyCommonSceneList);
        }
    }
    @Override
    public List<FamilySceneVO> getFamilyUncommonScenes4VOByFamilyId(Long familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<HouseTemplateScene> scenes = houseTemplateSceneService.getScenesByTemplate(familyDO.getTemplateId());

        List<FamilyCommonSceneDO> familyCommonSceneDOList = familyCommonSceneService.listCommonScenesByFamilyId(familyId);
        List<FamilySceneBO> familySceneBOList = houseTemplateSceneService.getFamilySceneWithIndex(familyId,familyDO.getTemplateId(),scenes, familyCommonSceneDOList, false);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneBO familySceneBO : familySceneBOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneBO.getSceneId());
            familySceneVO.setSceneName(familySceneBO.getSceneName());
            familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
            familySceneVO.setSceneIndex(familySceneBO.getSceneIndex());
            familySceneVOList.add(familySceneVO);
        }
        return familySceneVOList;
    }
    /**
     * APP下发场景
     *
     * @param sceneId  场景ID
     * @param familyId 家庭ID
     * @return void
     * @author wenyilu
     * @date 2021/1/6 15:26
     */
    @Override
    public void executeScene(Long sceneId, Long familyId) {
        ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(familyId);
        if (familyInfo == null || StringUtils.isEmpty(familyInfo.getScreenMac())) {
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        HouseTemplateScene sceneDO = houseTemplateSceneService.getById(sceneId);
        if (sceneDO == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        AdapterSceneControlDTO adapterSceneControlDTO = new AdapterSceneControlDTO();
        adapterSceneControlDTO.buildBaseInfo(familyId, familyInfo.getCode(),familyInfo.getTemplateId(), familyInfo.getScreenMac(), System.currentTimeMillis());
        adapterSceneControlDTO.setSceneId(sceneId);
        AdapterSceneControlAckDTO adapterSceneControlAckDTO = bridgeAppService.familySceneControl(adapterSceneControlDTO);
        if (Objects.isNull(adapterSceneControlAckDTO)) {
            throw new BusinessException(NETWORK_ERROR);
        } else if (!Objects.equals(adapterSceneControlAckDTO.getCode(), 200
        )) {
            throw new BusinessException(adapterSceneControlAckDTO.getMessage());
        }
    }
    @Override
    public List<PicVO> getListScenePic() {
        List<PicVO> data = dicTagService.getListScenePic(SCENE_CION);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        data.forEach(obj->{
            String[] str = obj.getName().split("-");
            obj.setName(str[0]);
            obj.setMode(str[1]);
        });
        return data;
    }
    /**
     * APP获取场景定时列表
     *
     * @param familyId 家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO>
     * @author wenyilu
     * @date 2020/12/28 10:41
     */
    @Override
    public List<FamilySceneTimingVO> getTimingSceneList(Long familyId) {
        List<FamilySceneTimingVO> familySceneTimingVOList = Lists.newArrayList();
        List<FamilySceneTimingBO> familySceneTimingBOList = familySceneTimingService.listFamilySceneTiming(familyId);

        if (!CollectionUtils.isEmpty(familySceneTimingBOList)) {
            familySceneTimingVOList.addAll(familySceneTimingBOList.stream().map(familySceneTimingBO -> {
                FamilySceneTimingVO familySceneTimingVO = new FamilySceneTimingVO();
                familySceneTimingVO.setTimingId(familySceneTimingBO.getTimingId());
                familySceneTimingVO.setExecuteSceneId(familySceneTimingBO.getExecuteSceneId());
                familySceneTimingVO.setExecuteSceneName(familySceneTimingBO.getExecuteSceneName());
                familySceneTimingVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingBO.getExecuteTime(), "HH:mm"));
                familySceneTimingVO.setEnabled(familySceneTimingBO.getEnabled());
                // 处理重复类型显示
                FamilySceneTimingRepeatTypeEnum sceneTimingRepeatTypeEnum = FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingBO.getRepeatType());
                if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.NONE)) {
                    familySceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(null));
                } else if (Objects.equals(sceneTimingRepeatTypeEnum, FamilySceneTimingRepeatTypeEnum.WEEK)) {
                    String workDay = sceneTimingRepeatTypeEnum.handleWorkDay(familySceneTimingBO.getWeekday());
                    if (Objects.equals(familySceneTimingBO.getSkipHoliday(), 1)) {
                        workDay += "，跳过法定节假日";
                    }
                    familySceneTimingVO.setWorkday(workDay);
                } else {
                    String startDateString = DateUtils.toTimeString(familySceneTimingBO.getStartDate(), "yyyy.MM.dd");
                    String endDateString = DateUtils.toTimeString(familySceneTimingBO.getEndDate(), "yyyy.MM.dd");
                    String timeString = startDateString + "," + endDateString;
                    familySceneTimingVO.setWorkday(sceneTimingRepeatTypeEnum.handleWorkDay(timeString));
                }
                return familySceneTimingVO;

            }).collect(Collectors.toList()));
        }

        return familySceneTimingVOList;
    }
    /**
     * APP查看场景定时记录详情
     *
     * @param timingId 场景定时记录ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO
     * @author wenyilu
     * @date 2020/12/28 10:48
     */
    @Override
    public SceneTimingDetailVO getTimingSceneDetail(Long timingId) {
        SceneTimingDetailVO detailVO = new SceneTimingDetailVO();

        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingId);

        detailVO.setTimingId(BeanUtil.convertLong2String(familySceneTimingDO.getId()));
        detailVO.setExecuteTime(DateUtils.toTimeString(familySceneTimingDO.getExecuteTime(), "HH:mm"));
        detailVO.setRepeatType(familySceneTimingDO.getType());
        detailVO.setSkipHoliday(familySceneTimingDO.getHolidaySkipFlag());

        // 重复设置
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            String weekdayInChinese = FamilySceneTimingRepeatTypeEnum.WEEK.replaceWeek(familySceneTimingDO.getWeekday().split(EscapeCharacterConst.COMMA));
            detailVO.setRepeatValue(String.join(EscapeCharacterConst.SPACE, weekdayInChinese.split(EscapeCharacterConst.COMMA)));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(familySceneTimingDO.getType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String startDateString = DateUtils.toTimeString(familySceneTimingDO.getStartDate(), "yyyy.MM.dd");
            String endDateString = DateUtils.toTimeString(familySceneTimingDO.getEndDate(), "yyyy.MM.dd");
            detailVO.setRepeatValue(startDateString + "-" + endDateString);
        }
        Long familyId = familySceneTimingDO.getFamilyId();
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<HouseTemplateScene> scenesByTemplate = houseTemplateSceneService.getScenesByTemplate(familyDO.getTemplateId());
        // 场景设置
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (HouseTemplateScene scene : scenesByTemplate) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
//            familySceneVO.setSceneId(scene.getId());
            familySceneVO.setSceneName(scene.getName());
            familySceneVO.setSceneIcon(scene.getIcon());
            familySceneVO.setChecked(Objects.equals(scene.getId(), familySceneTimingDO.getSceneId()) ? 1 : 0);
            familySceneVOList.add(familySceneVO);
        }
        detailVO.setScenes(familySceneVOList);
        detailVO.setSceneId(String.valueOf(familySceneTimingDO.getSceneId()));
        detailVO.setEnabled(familySceneTimingDO.getEnableFlag());
        return detailVO;
    }

    @Override
    public boolean saveTimingScene(TimingSceneDTO timingSceneDTO) {
        if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getExecuteTime())) {
            throw new ApiException("执行时间不可为空");
        } else if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getFamilyId())) {
            throw new ApiException("家庭ID不可为空");
        } else if (org.springframework.util.StringUtils.isEmpty(timingSceneDTO.getSceneId())) {
            throw new ApiException("场景ID不可为空");
        } else if (Objects.isNull(timingSceneDTO.getRepeatType())) {
            throw new ApiException("重复类型不可为空");
        }
        FamilySceneTimingDO familySceneTimingDO = new FamilySceneTimingDO();
        familySceneTimingDO.setId(timingSceneDTO.getTimingId());
        familySceneTimingDO.setFamilyId(timingSceneDTO.getFamilyId());
        familySceneTimingDO.setSceneId(timingSceneDTO.getSceneId());
        familySceneTimingDO.setExecuteTime(DateUtils.parseLocalTime(timingSceneDTO.getExecuteTime(), "HH:mm"));
        familySceneTimingDO.setType(timingSceneDTO.getRepeatType());
        familySceneTimingDO.setHolidaySkipFlag(timingSceneDTO.getSkipHoliday());
        familySceneTimingDO.setEnableFlag(1);
        if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.WEEK)) {
            familySceneTimingDO.setWeekday(String.join(EscapeCharacterConst.COMMA, DateUtils.parseWeek(timingSceneDTO.getRepeatValue().split(EscapeCharacterConst.SPACE))));
        } else if (Objects.equals(FamilySceneTimingRepeatTypeEnum.getByType(timingSceneDTO.getRepeatType()), FamilySceneTimingRepeatTypeEnum.CALENDAR)) {
            String[] dateSplits = timingSceneDTO.getRepeatValue().split("-");
            familySceneTimingDO.setStartDate(DateUtils.parseLocalDate(dateSplits[0], "yyyy.MM.dd"));
            familySceneTimingDO.setEndDate(DateUtils.parseLocalDate(dateSplits[1], "yyyy.MM.dd"));
        }
        return familySceneTimingService.saveOrUpdate(familySceneTimingDO);
    }
    /**
     * 通知大屏定时场景配置更新
     *
     * @param familyId
     * @param typeEnum
     * @return void
     * @author wenyilu
     * @date 2021/1/7 9:31
     */
    @Override
    public void notifySceneTimingConfigUpdate(Long familyId, ContactScreenConfigUpdateTypeEnum typeEnum) {
        ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(familyId);

        AdapterConfigUpdateDTO adapterConfigUpdateDTO = new AdapterConfigUpdateDTO();
        adapterConfigUpdateDTO.buildBaseInfo(familyId, familyInfo.getCode(),
                familyInfo.getTemplateId(), familyInfo.getScreenMac(),
                System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        bridgeAppService.configUpdateConfig(adapterConfigUpdateDTO);
    }

    @Override
    public void deleteFamilySceneTiming(Long timingSceneId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(timingSceneId);
        familySceneTimingService.removeById(timingSceneId);

        // 通知大屏定时场景配置更新
        try {
            notifySceneTimingConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enableSceneTiming(Long sceneTimingId) {
        FamilySceneTimingDO familySceneTimingDO = familySceneTimingService.getById(sceneTimingId);
        int targetEnabled = (familySceneTimingDO.getEnableFlag() + 1) % 2;
        familySceneTimingService.updateEnabled(sceneTimingId, targetEnabled);
        // 通知大屏定时场景配置更新
        try {
            contactScreenService.notifySceneTimingConfigUpdate(familySceneTimingDO.getFamilyId(), ContactScreenConfigUpdateTypeEnum.SCENE_TIMING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询家庭下场景
     *
     * @param familyId 家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO>
     * @author wenyilu
     * @date 2021/1/6 15:54
     */
    @Override
    public List<FamilySceneVO> listWholeHouseScene(Long familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        List<HouseTemplateScene> scenesByTemplate = houseTemplateSceneService.getScenesByTemplate(familyDO.getTemplateId());
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (HouseTemplateScene scene : scenesByTemplate) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(BeanUtil.convertLong2String(scene.getId()));
            familySceneVO.setSceneName(scene.getName());
            familySceneVO.setSceneIcon(scene.getIcon());
            familySceneVO.setFamilyId(BeanUtil.convertLong2String(familyId));
            familySceneVO.setTemplateId(BeanUtil.convertLong2String(familyDO.getTemplateId()));
            familySceneVOList.add(familySceneVO);
        }
        return familySceneVOList;
    }
    @Override
    public List<AlarmMessageRecordVO> getAlarmlistByDeviceId(Long deviceId, Long familyId) {
        return homeAutoAlarmMessageService.getAlarmlistByDeviceId(deviceId,familyId);
    }

    @Override
    public AppVersionDTO getCurrentVersion(Integer appType, String belongApp) {
        return homeAutoAppVersionService.getCurrentVersion(appType,belongApp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMaintenanceRecord(FamilyMaintenanceAddRequestDTO requestDTO) {
        // 获取id
        long segmentId = idService.getSegmentId(CommonConst.BIZ_CODE_HOMEAUTO_MAINTENANCE);
        FamilyMaintenanceRecord saveData = new FamilyMaintenanceRecord();
        BeanUtils.copyProperties(requestDTO,saveData);
        saveData.setMaintenanceTime(LocalDateTimeUtil.parseStr2LocalDate(requestDTO.getMaintenanceTime(),"yyyy-MM-dd"));
        saveData.setNum(segmentId);
        familyMaintenanceRecordService.save(saveData);
    }

    @Override
    public List<FamilyMaintenanceRecordVO> listMaintenanceRecords(Long familyId) {
        return familyMaintenanceRecordService.listByFamily(familyId);
    }

    @Override
    public FamilyMaintenanceRecordVO getMaintenanceDetail(Long id) {
        return familyMaintenanceRecordService.detail(id);
    }

    /**
     * APP下发指令
     *
     * @param deviceCommandDTO 设备控制数据传输对象
     * @return void
     * @author wenyilu
     * @date 2021/1/6 15:03
     */
    @Override
    public void sendCommand(DeviceCommandDTO deviceCommandDTO) {
        Long deviceId = deviceCommandDTO.getDeviceId();
        Long familyId = deviceCommandDTO.getFamilyId();
        if (familyId==null) {
            FamilyUserCheckout userCheckout = familyUserCheckoutService.getByUserId(getUserIdForAppRequest());
            if (userCheckout != null) {
                familyId = userCheckout.getFamilyId();
            }
        }
        ScreenFamilyBO familyBO = configCacheProvider.getFamilyInfo(familyId);
        if (familyBO == null || StringUtils.isEmpty(familyBO.getScreenMac())) {
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        ScreenTemplateDeviceBO deviceBO = configCacheProvider.getFamilyDeviceByDeviceId(familyBO.getTemplateId(), deviceId);
        if (deviceBO == null) {
            throw new BusinessException(ErrorCodeEnumConst.DEVICE_NOT_FOUND);
        }
        List<AppDeviceAttributeDTO> commandDTOData = deviceCommandDTO.getData();
        List<ScreenDeviceAttributeDTO> screenAttributeDTOs = Lists.newArrayList();

        for (AppDeviceAttributeDTO commandDTODatum : commandDTOData) {
            String shortCode = commandDTODatum.getShortCode();
            String code = commandDTODatum.getCode();
            if (StringUtils.isEmpty(shortCode) && StringUtils.isEmpty(code)) {
                throw new BusinessException(ErrorCodeEnumConst.ATTRIBUTE_SHORT_CODE_REQUIRE);
            }
            ScreenDeviceAttributeDTO dto = new ScreenDeviceAttributeDTO();
            dto.setValue(commandDTODatum.getValue());
            dto.setCode(!StringUtils.isEmpty(shortCode) ? shortCode : code);
            dto.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(familyBO.getTemplateId(), dto.getCode(), deviceBO.getSystemFlag(), deviceBO.getDeviceSn()));
            screenAttributeDTOs.add(dto);
        }
        log.info("指令信息获取完毕, 准备发送");
        AdapterDeviceControlDTO controlDTO = new AdapterDeviceControlDTO();
        controlDTO.buildBaseInfo(familyId, familyBO.getCode(), familyBO.getTemplateId(), familyBO.getScreenMac(), System.currentTimeMillis());
        controlDTO.setData(screenAttributeDTOs);
        controlDTO.setProductCode(deviceBO.getProductCode());
        controlDTO.setSystemFlag(deviceBO.getSystemFlag());
        controlDTO.setDeviceSn(Integer.parseInt(deviceBO.getDeviceSn()));
        AdapterDeviceControlAckDTO adapterDeviceControlAckDTO = bridgeAppService.deviceWriteControl(controlDTO);
        if (Objects.isNull(adapterDeviceControlAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else if (!Objects.equals(adapterDeviceControlAckDTO.getCode(), 200)) {
            throw new BusinessException(adapterDeviceControlAckDTO.getMessage());
        }

    }

}
