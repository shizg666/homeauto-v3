package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.jiguang.common.utils.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.enums.AttrAppFlagEnum;
import com.landleaf.homeauto.center.device.enums.FamilyEnableStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.excel.importfamily.Custemhandler;
import com.landleaf.homeauto.center.device.excel.importfamily.FamilyImportDataListener;
import com.landleaf.homeauto.center.device.excel.importfamily.ImporFamilyResultVO;
import com.landleaf.homeauto.center.device.excel.importfamily.ImportFamilyModel;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.handle.excel.ProtocolSheetWriteHandler;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoArea;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.mqtt.MqttUser;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.WebSocketMessageService;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.common.FamilyWeatherService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
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
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.NETWORK_ERROR;
import static com.landleaf.homeauto.common.web.context.TokenContextUtil.getUserIdForAppRequest;

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
    private IFamilyUserService familyUserService;

    @Autowired
    private IHomeAutoRealestateService homeAutoRealestateService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private AttributeShortCodeConvertFilter attributeShortCodeConvertFilter;


    @Autowired
    private IAppService iAppService;

    @Autowired(required = false)
    private UserRemote userRemote;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;

    @Autowired
    private CommonService commonService;
    @Autowired
    private IFamilyUserCheckoutService familyUserCheckoutService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;


    @Autowired
    private WebSocketMessageService webSocketMessageService;

    @Autowired
    private IFamilyAuthorizationService iFamilyAuthorizationService;

    @Autowired
    private IAreaService areaService;
    @Autowired
    private FamilyWeatherService familyWeatherService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

    @Autowired
    private IHouseTemplateFloorService houseTemplateFloorService;

    @Autowired
    private IHouseTemplateRoomService houseTemplateRoomService;

    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;

    @Autowired
    private IHouseTemplateSceneService houseTemplateSceneService;

    @Autowired
    private IProjectSoftConfigService projectSoftConfigService;

    @Autowired
    private IDeviceAttrInfoService deviceAttrInfoService;


    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;

    @Autowired
    private IHomeAutoProductService productService;


    @Autowired
    private IAppService appService;
    
    @Autowired
    private IMqttUserService iMqttUserService;

    public static final Integer MASTER_FLAG = 1;
    public static final String FILE_NAME_PREX = "家庭导入模板";

    public static final String MQTT_USER_PASSWORD_PREX = "landleaf";

    /**
     * 根据家庭id获取家庭BO对象
     *
     * @param familyId 家庭ID
     * @return com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO
     * @author wenyilu
     * @date 2020/12/28 16:12
     */
    @Override
    public HomeAutoFamilyBO getHomeAutoFamilyBO(String familyId) {

        HomeAutoFamilyDO homeAutoFamilyDO = getById(familyId);
        if (homeAutoFamilyDO == null) {
            throw new BusinessException(ErrorCodeEnumConst.FAMILY_NOT_FOUND);
        }
        HomeAutoRealestate homeAutoRealestate = homeAutoRealestateService.getById(homeAutoFamilyDO.getRealestateId());
        if (homeAutoRealestate == null) {
            throw new BusinessException(ErrorCodeEnumConst.REALESTATE_NOT_FOUND);
        }
        String weatherCode = null;
        String cityCode = homeAutoRealestate.getCityCode();
        HomeAutoArea homeAutoArea = areaService.getByCode(homeAutoRealestate.getCityCode());
        if (homeAutoArea != null) {
            weatherCode = homeAutoArea.getWeatherCode();
        }

        return HomeAutoFamilyBO.builder().familyCode(homeAutoFamilyDO.getCode())
                .familyId(familyId).familyName(homeAutoFamilyDO.getName())
                .familyNumber(homeAutoFamilyDO.getRoomNo())
                .templateName(null).templateId(homeAutoFamilyDO.getTemplateId())
                .unitCode(homeAutoFamilyDO.getUnitCode()).buildingCode(homeAutoFamilyDO.getBuildingCode())
                .projectId(homeAutoFamilyDO.getProjectId()).realestateId(homeAutoFamilyDO.getRealestateId())
                .cityCode(cityCode).weatherCode(weatherCode)
                .screenMac(homeAutoFamilyDO.getScreenMac()).enableStatus(homeAutoFamilyDO.getEnableStatus())
                .build();
    }

    /**
     * 根据家庭获取城市天气Code
     *
     * @param familyId
     * @return java.lang.String
     * @author wenyilu
     * @date 2020/12/28 16:15
     */
    @Override
    public String getWeatherCodeByFamilyId(String familyId) {
        return homeAutoFamilyMapper.getWeatherCodeByFamilyId(familyId);
    }

    /**
     * 根据终端类型及终端值获取家庭BO
     *
     * @param mac mac地址
     * @return com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO
     * @author wenyilu
     * @date 2020/12/28 16:20
     * 调整后将没有mac
     */
    @Deprecated
    @Override
    public FamilyInfoBO getFamilyInfoByTerminalMac(String mac) {
        return homeAutoFamilyMapper.getFamilyInfoByTerminalMac(mac);
    }

    /**
     * APP获取我的家庭家庭列表統計信息
     * 获取家庭的统计信息：房间数、设备数、用户数
     * @param userId 用户ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO>
     * @author wenyilu
     * @date 2020/12/28 16:23
     */
    @Override
    public List<MyFamilyInfoVO> getMyFamily4VO(String userId) {
        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getMyFamily(userId);

        if (CollectionUtils.isEmpty(infoVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> templateIds = infoVOS.stream().map(MyFamilyInfoVO::getTemplateId).collect(Collectors.toList());
        List<String> familyIds = infoVOS.stream().map(MyFamilyInfoVO::getId).collect(Collectors.toList());
        // 主键为户型
        List<CountBO> roomCount = houseTemplateRoomService.getCountByTemplateIds(templateIds);
        List<CountBO> deviceCount = houseTemplateDeviceService.getCountByTemplateIds(templateIds, CommonConst.Business.DEVICE_SHOW_APP_TRUE);
        // 主键为家庭
        List<CountBO> userCount = familyUserService.getCountByFamilyIds(familyIds);
        Map<String, Integer> roomCountMap = Maps.newHashMap();
        Map<String, Integer> deviceCountMap = Maps.newHashMap();
        Map<String, Integer> userCountMap = Maps.newHashMap();
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
    public MyFamilyDetailInfoVO getMyFamilyInfo4VO(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        MyFamilyDetailInfoVO result = new MyFamilyDetailInfoVO();
        List<FloorRoomVO> floors = houseTemplateFloorService.getFloorAndRoomDevices
                (familyDO.getTemplateId(), CommonConst.Business.DEVICE_SHOW_APP_TRUE);
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

    /**
     * 智齿客服根据家庭id获取家庭信息
     *
     * @param familyId
     * @return com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO
     * @author wenyilu
     * @date 2020/12/28 16:52
     */
    @Override
    public FamilyInfoForSobotDTO getFamilyInfoForSobotById(String familyId) {
        return this.baseMapper.getFamilyInfoForSobotById(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilyAddDTO request) {
        checkRoomNo(request.getRoomNo(), request.getBuildingCode(), request.getUnitCode());
        int count = count(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getScreenMac,request.getScreenMac()));
        if (count > 0){
            throw new BusinessException("该Mac已经被绑定！");
        }
        request.setId(IdGeneratorUtil.getUUID32());
        String code = buildCode(request);
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        familyDO.setCode(code);
        familyDO.setEnableStatus(0);
        save(familyDO);
        saveMqttUser(familyDO);
        redisUtils.set(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE,familyDO.getCode()),familyDO.getTemplateId());
    }

    private void saveMqttUser(HomeAutoFamilyDO familyDO) {
        MqttUser mqttUser = new MqttUser();
        String name = familyDO.getCode().concat("-").concat("0");
        String password = MQTT_USER_PASSWORD_PREX.concat(familyDO.getBuildingCode()).concat(familyDO.getUnitCode()).concat(familyDO.getRoomNo());
        mqttUser.setUserName(name);
        mqttUser.setIsSuperuser(0);
        String passwordStr = DigestUtil.sha256Hex(password);
        mqttUser.setPassword(passwordStr);
        iMqttUserService.save(mqttUser);
    }

    /**
     * 生产家庭编号
     *
     * @param request
     * @return
     */
    private String buildCode(FamilyAddDTO request) {
        PathBO realestate = homeAutoRealestateService.getRealestatePathInfoById(request.getRealestateId());
        PathBO project = iHomeAutoProjectService.getProjectPathInfoById(request.getProjectId());
        String path = project.getPath().concat("/").concat(request.getId());
        StringBuilder pathName = new StringBuilder();
        pathName.append(realestate.getPathName()).append("/").append(project.getName()).append("/").append(request.getBuildingCode()).append("栋").append(request.getUnitCode()).append("单元").append(request.getRoomNo());
        request.setPath(path);
        request.setPathName(pathName.toString());
        String bulidCode = request.getBuildingCode().length() == 2 ? request.getBuildingCode() : "0".concat(request.getBuildingCode());
        String unitCode = request.getUnitCode().length() == 2 ? request.getUnitCode() : "0".concat(request.getUnitCode());
        request.setBuildingCode(bulidCode);
        request.setUnitCode(unitCode);
        return new StringBuilder().append(project.getCode()).append("-").append(bulidCode).append(unitCode).append(request.getRoomNo()).toString();
    }

    @Override
    public void update(FamilyAddDTO request) {
        HomeAutoFamilyDO familyDO = getById(request.getId());
        String code = familyDO.getCode();
        if (!(familyDO.getBuildingCode().equals(request.getBuildingCode()) && familyDO.getUnitCode().equals(request.getUnitCode()) && familyDO.getRoomNo().equals(request.getRoomNo()))) {
            throw new BusinessException("楼栋单元号的改变会导致跟家庭编码不一致，不允许修改！");
//            checkRoomNo(request.getRoomNo(), request.getBuildingCode(), request.getUnitCode());
//            code = buildCode(request);
        }
        if(!familyDO.getScreenMac().equals(request.getScreenMac())){
            HomeAutoFamilyDO familyOld = getOne(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getScreenMac,request.getScreenMac()));
            if (Objects.nonNull(familyOld) && !familyOld.getId().equals(request.getId())){
                throw new BusinessException("该Mac已经被绑定！");
            }
        }
        HomeAutoFamilyDO family = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        family.setCode(code);
        updateById(family);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        HomeAutoFamilyDO familyDO = getById(request.getId());
        if(Objects.isNull(familyDO)){
            throw new BusinessException("id不存在！");

        }
        removeById(request.getId());
        familyUserService.remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getId()));
        familyUserCheckoutService.deleteByFamilyId(request.getId());
        iMqttUserService.removeByFamilyCode(familyDO.getCode());
        redisUtils.set(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE,familyDO.getCode()),familyDO.getTemplateId());

    }

    @Override
    public List<FamilyPageVO> getListByUnitId(String id) {
        return this.baseMapper.getListByUnitId(id);
    }


    @Override
    public List<String> getListIdByPaths(List<String> path) {
        if (CollectionUtils.isEmpty(path)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return this.baseMapper.getListIdByPaths(path);
    }


    @Override
    public List<FamilyBaseInfoDTO> getBaseInfoByProjectId(String familyId) {
        return this.baseMapper.getBaseInfoByProjectId(familyId);
    }

    @Override
    public List<FamilyBaseInfoDTO> getBaseInfoByPath(List<String> paths) {
        if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return this.baseMapper.getBaseInfoByPath(paths);
    }

    private void checkRoomNo(String roomNo,  String buildNo, String unitNo) {
        int count = this.baseMapper.existRoomNo(roomNo, buildNo, unitNo);
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户号已存在");
        }

    }

    @Override
    public HomeAutoFamilyDO getFamilyByCode(String familyCode) {
        QueryWrapper<HomeAutoFamilyDO> familyQueryWrapper = new QueryWrapper<>();
        familyQueryWrapper.eq("code", familyCode);
        return getOne(familyQueryWrapper);
    }

    @Override
    public List<FamilyUserVO> getListByUser(String userId) {
        return this.baseMapper.getListByUser(userId);
    }

    @Override
    public void downLoadImportTemplate(TemplateQeyDTO request, HttpServletResponse response) {
        List<String> templtes = iProjectHouseTemplateService.getListHoustTemplateNames(request.getProjectId());
        if (CollectionUtils.isEmpty(templtes)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "没有可用户型");
        }
        List<List<String>> headList = getListHead(request);
        commonService.setResponseHeader(response, FILE_NAME_PREX);
        try {
            OutputStream os = response.getOutputStream();
//            EasyExcel.write(os).head(headList).excelType(ExcelTypeEnum.XLSX).sheet(FILE_NAME_PREX).registerWriteHandler(new Custemhandler()).registerWriteHandler(getStyleStrategy()).registerWriteHandler(new ProtocolSheetWriteHandler(getExportData(templtes))).doWrite(Lists.newArrayListWithCapacity(0));
   EasyExcel.write(os).head(headList).excelType(ExcelTypeEnum.XLSX).sheet(FILE_NAME_PREX).registerWriteHandler(new Custemhandler()).registerWriteHandler(new ProtocolSheetWriteHandler(getExportData(templtes))).doWrite(Lists.newArrayListWithCapacity(0));

        } catch (IOException e) {
            log.error("模板下载失败，原因：{}", e.getMessage());
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getMsg());
        }
    }
    private List<List<String>> getListHead(TemplateQeyDTO request) {
        List<List<String>> headList = Lists.newArrayList();
        // 表头
        String headStr = FILE_NAME_PREX.concat("-").concat(request.getRealestateId()).concat("-").concat(request.getProjectId());
        List<String> headArray = Lists.newArrayListWithExpectedSize(8);
        headArray.add("家庭名称");
        headArray.add("门牌号(长度4位)");
        headArray.add("楼栋号(长度2位)");
        headArray.add("单元号(长度2位)");
        headArray.add("主大屏编号");
        headArray.add("主大屏ip");
        headArray.add("户型");
        List<String> headTitle;
        for (String name : headArray) {
            headTitle = Lists.newArrayListWithExpectedSize(2);
            headTitle.add(headStr);
            headTitle.add(name);
            headList.add(headTitle);
        }
        return headList;
    }

    private Map<Integer, String[]> getExportData(List<String> templtes) {

        Map<Integer, String[]> data = Maps.newHashMapWithExpectedSize(2);
        //户型
        data.put(6, templtes.toArray(new String[]{}));
        return data;
    }

    public HorizontalCellStyleStrategy getStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 18);
        // 字体样式
        headWriteFont.setFontName("Frozen");
        headWriteCellStyle.setWriteFont(headWriteFont);
        //自动换行
        headWriteCellStyle.setWrapped(false);
        // 水平对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SQUARES);
        // 背景白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 18);
        // 字体样式
        contentWriteFont.setFontName("Calibri");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteCellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }


    @Override
    public void importBatch(MultipartFile file, HttpServletResponse response) throws IOException {
        FamilyImportDataListener listener = new FamilyImportDataListener(iHomeAutoFamilyService, homeAutoRealestateService, iHomeAutoProjectService, iProjectHouseTemplateService);
        EasyExcel.read(file.getInputStream(), ImportFamilyModel.class, listener).sheet().doRead();
        if (CollectionUtils.isEmpty(listener.getErrorlist())) {
            Response result = new Response();
            result.setSuccess(true);
            result.setMessage("操作成功!");
            result.setResult(null);
            String resBody = JSON.toJSONString(result);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
            return;
        }
        try {
            String fileName = "失败列表";
            commonService.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            List<ImporFamilyResultVO> familyResultVOS = BeanUtil.mapperList(listener.getErrorlist(), ImporFamilyResultVO.class);
            EasyExcel.write(os, ImporFamilyResultVO.class).sheet("失败列表").doWrite(familyResultVOS);
        } catch (IOException e) {
            log.error("模板下载失败，原因：{}", e.getMessage());
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), e.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ImportFamilyModel> importBatchFamily(List<ImportFamilyModel> dataList) {

        if (CollectionUtils.isEmpty(dataList)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<ImportFamilyModel> result = Lists.newArrayListWithExpectedSize(dataList.size());
        for (ImportFamilyModel data : dataList) {
            try {
                checkRoomNo(data.getRoomNo(), data.getBuildingCode(), data.getUnitCode());
                HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(data, HomeAutoFamilyDO.class);
                save(familyDO);
                saveMqttUser(familyDO);
            } catch (BusinessException e) {
                data.setError(e.getMessage());
                result.add(data);
            } catch (Exception e) {
                log.error("家庭导入报错：行数:{} 工程名称：{}，原因：{}", data.getRow(), data.getName(), e.getMessage());
                data.setError(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getMsg());
                result.add(data);
            }

        }
        return result;
    }


    @Override
    public String getFamilyCodeByid(String familyId) {
        String key = String.format(RedisCacheConst.FAMILY_ID_CODE, familyId);
        String code = (String) redisUtils.get(key);
        if (!StringUtil.isEmpty(code)) {
            return code;
        }
        code = this.baseMapper.getFamilyCodeByid(familyId);
        redisUtils.set(key, code);
        return code;
    }

    @Override
    public String getFamilyIdByMac(String mac) {
        String familyId = this.baseMapper.getFamilyIdByMac(mac);
        if (StringUtil.isEmpty(familyId)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "根据Mac查询不到家庭信息");
        }
        return familyId;
    }


    @Override
    public List<SelectedVO> getListFamilySelects(String projectId) {
        List<String> paths = commonService.getUserPathScope();
//        List<String> paths = Lists.newArrayList();
//        paths.add("CN");
        return this.baseMapper.getListFamilyByPaths(paths,projectId);
    }

    @Override
    public BasePageVO<FamilyPageVO> getListPage(FamilyQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<FamilyPageVO> result = this.baseMapper.getListPage(request);
        if (CollectionUtils.isEmpty(result)) {
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<FamilyPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    /**
     * 根据用户及家庭状态获取家庭列表
     *
     * @param userId       用户ID
     * @param enableStatus 启用，停用
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO>
     * @author wenyilu
     * @date 2020/12/25 9:24
     */
    @Override
    public List<HomeAutoFamilyBO> listByUserId(String userId, Integer enableStatus) {
        List<HomeAutoFamilyBO> homeAutoFamilyBOList = new LinkedList<>();
        List<FamilyUserDO> familyUserDOList = familyUserService.listByUserId(userId);
        if (!CollectionUtils.isEmpty(familyUserDOList)) {
            if (!CollectionUtil.isEmpty(familyUserDOList)) {
                List<String> familyIdList = familyUserDOList.stream().map(FamilyUserDO::getFamilyId).collect(Collectors.toList());

                Supplier<LambdaQueryWrapper> querySupply = () -> {
                    LambdaQueryWrapper<HomeAutoFamilyDO> homeAutoFamilyDOQueryWrapper = new LambdaQueryWrapper<>();
                    if (enableStatus != null) {
                        homeAutoFamilyDOQueryWrapper.eq(HomeAutoFamilyDO::getEnableStatus, enableStatus);
                    }
                    homeAutoFamilyDOQueryWrapper.in(HomeAutoFamilyDO::getId, familyIdList);
                    homeAutoFamilyDOQueryWrapper.orderByAsc(HomeAutoFamilyDO::getCreateTime);
                    return homeAutoFamilyDOQueryWrapper;
                };
                List<HomeAutoFamilyDO> homeAutoFamilyDOList = list(querySupply.get());

                if (!CollectionUtils.isEmpty(homeAutoFamilyDOList)) {
                    homeAutoFamilyBOList.addAll(homeAutoFamilyDOList.stream().map(i -> {
                        return HomeAutoFamilyBO.builder().familyId(i.getId()).familyCode(i.getCode())
                                .familyName(i.getName()).familyNumber(i.getRoomNo()).build();
                    }).collect(Collectors.toList()));
                }
            }
        }
        return homeAutoFamilyBOList;
    }


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
        List<HomeAutoFamilyBO> homeAutoFamilyBOList = listByUserId(userId, null);

        HomeAutoFamilyVO.HomeAutoFamilyVOBuilder currentBuilder = HomeAutoFamilyVO.builder();
        List<HomeAutoFamilyVO> familyVOList = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(homeAutoFamilyBOList)) {
            familyVOList = homeAutoFamilyBOList.stream().map(i -> {
                HomeAutoFamilyVO homeAutoFamilyVO = new HomeAutoFamilyVO();
                BeanUtils.copyProperties(i, homeAutoFamilyVO);
                return homeAutoFamilyVO;
            }).collect(Collectors.toList());
            HomeAutoFamilyBO homeAutoFamilyBO = homeAutoFamilyBOList.get(0);

            currentBuilder.familyCode(homeAutoFamilyBO.getFamilyCode())
                    .familyId(homeAutoFamilyBO.getFamilyId())
                    .familyName(homeAutoFamilyBO.getFamilyName());

            FamilyUserCheckout familyUserCheckout = familyUserCheckoutService.getByUserId(userId);
            if (!Objects.isNull(familyUserCheckout)) {
                Optional<HomeAutoFamilyVO> firstOptional = familyVOList.stream()
                        .filter(i -> org.apache.commons.lang.StringUtils.equals
                                (i.getFamilyId(), familyUserCheckout.getFamilyId())).findFirst();
                boolean present = firstOptional.isPresent();
                if (present) {
                    HomeAutoFamilyVO homeAutoFamilyVO = firstOptional.get();
                    currentBuilder.familyCode(homeAutoFamilyVO.getFamilyCode())
                            .familyId(homeAutoFamilyVO.getFamilyId())
                            .familyName(homeAutoFamilyVO.getFamilyName());
                }
            }
        }
        result.setCurrent(currentBuilder.build());
        result.setList(familyVOList);
        return result;
    }

    /**
     * APP获取楼层及所属房间信息
     *
     * @param familyId 家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyFloorVO>
     * @author wenyilu
     * @date 2020/12/25 13:24
     */
    @Override
    public List<FamilyFloorVO> getFamilyFloor4VO(String familyId) {
        HomeAutoFamilyDO homeAutoFamilyDO = getById(familyId);
        List<FamilyFloorVO> result = Lists.newArrayList();

        List<TemplateFloorDO> templateFloorDOList = houseTemplateFloorService.getFloorByTemplateId(homeAutoFamilyDO.getTemplateId());

        if (!CollectionUtils.isEmpty(templateFloorDOList)) {
            result.addAll(templateFloorDOList.stream().map(familyFloorDO -> {
                List<FamilyRoomBO> familyRoomBOList = getFamilyRoomBOByTemplateAndFloor(familyId, homeAutoFamilyDO.getTemplateId(),familyFloorDO.getId());
                List<FamilyRoomVO> familyRoomVOList = Lists.newLinkedList();
                if (!CollectionUtils.isEmpty(familyRoomBOList)) {
                    familyRoomVOList.addAll(familyRoomBOList.stream().map(familyRoomBO -> {
                        FamilyRoomVO familyRoomVO = new FamilyRoomVO();
                        familyRoomVO.setRoomId(familyRoomBO.getRoomId());
                        familyRoomVO.setRoomName(familyRoomBO.getRoomName());
                        familyRoomVO.setRoomIcon(familyRoomBO.getRoomIcon1());
                        familyRoomVO.setRoomCode(familyRoomBO.getRoomCode());
                        familyRoomVO.setImgApplets(familyRoomBO.getImgApplets());
                        familyRoomVO.setImgExpand(familyRoomBO.getImgExpand());
                        return familyRoomVO;
                    }).collect(Collectors.toList()));
                }
                FamilyFloorVO familyFloorVO = new FamilyFloorVO();
                familyFloorVO.setFloorId(familyFloorDO.getId());
                familyFloorVO.setFloorName(String.format("%sF", familyFloorDO.getFloor()));
                familyFloorVO.setRoomList(familyRoomVOList);
                familyFloorVO.setFamilyId(familyId);
                familyFloorVO.setTemplateId(homeAutoFamilyDO.getTemplateId());
                return familyFloorVO;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     * APP获取房间下所有设备
     *
     * @param familyId 家庭ID
     * @param roomId   房间ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO>
     * @author wenyilu
     * @date 2021/1/6 9:29
     */
    @Override
    public List<FamilyDeviceVO> getFamilyDevices4VO(String familyId, String roomId) {
        List<FamilyDeviceVO> result = Lists.newArrayList();
        HomeAutoFamilyDO familyDO = getById(familyId);
        List<FamilyDeviceBO> familyDeviceBOList = houseTemplateDeviceService.getFamilyRoomDevices
                (familyId, roomId, familyDO.getTemplateId(), CommonConst.Business.DEVICE_SHOW_APP_TRUE);
        if (!CollectionUtils.isEmpty(familyDeviceBOList)) {
            result.addAll(familyDeviceBOList.stream().map(familyDeviceBO -> {
                FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
                familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
                familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
                familyDeviceVO.setDeviceCode(familyDeviceBO.getDeviceCode());
                familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
                familyDeviceVO.setCategoryCode(familyDeviceBO.getCategoryCode());
                familyDeviceVO.setProductCode(familyDeviceBO.getProductCode());
                familyDeviceVO.setUiCode(familyDeviceBO.getUiCode());
                return familyDeviceVO;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     * 获取家庭下楼层下房间信息
     *
     * @param familyId   家庭ID
     * @param templateId 户型ID
     * @param floorId    楼层ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO>
     * @author wenyilu
     * @date 2021/1/6 11:29
     */
    @Override
    public List<FamilyRoomBO> getFamilyRoomBOByTemplateAndFloor(String familyId, String templateId, String floorId) {
        List<TemplateRoomDO> templateRoomDOS = houseTemplateRoomService.getFamilyRoomBOByTemplateAndFloor(floorId, templateId);
        HomeAutoFamilyDO familyDO = getById(familyId);
        List<FamilyRoomBO> familyRoomBOList = new LinkedList<>();
        for (TemplateRoomDO templateRoomDO : templateRoomDOS) {
            FamilyRoomBO familyRoomBO = new FamilyRoomBO();
            // 1. 家庭信息
            familyRoomBO.setFamilyId(templateRoomDO.getId());
            familyRoomBO.setFamilyCode(familyDO.getCode());
            familyRoomBO.setFamilyName(templateRoomDO.getName());
            familyRoomBO.setTemplateId(templateId);

            // 2. 楼层信息
            TemplateFloorDO floorDO = houseTemplateFloorService.getById(templateRoomDO.getFloorId());
            familyRoomBO.setFloorId(floorDO.getId());
            familyRoomBO.setFloorName(floorDO.getName());
            familyRoomBO.setFloorNum(floorDO.getFloor());

            // 3. 房间信息
            familyRoomBO.setRoomId(templateRoomDO.getId());
            familyRoomBO.setRoomName(templateRoomDO.getName());
            familyRoomBO.setRoomIcon1(templateRoomDO.getIcon());
            familyRoomBO.setRoomIcon2(templateRoomDO.getImgIcon());
            familyRoomBO.setImgApplets(templateRoomDO.getImgApplets());
            familyRoomBO.setImgExpand(templateRoomDO.getImgExpand());
            familyRoomBO.setRoomCode(templateRoomDO.getCode());
            familyRoomBO.setRoomTypeEnum(RoomTypeEnum.getInstByType(templateRoomDO.getType()));

            familyRoomBOList.add(familyRoomBO);
        }
        return familyRoomBOList;
    }

    /**
     * 获取家庭不同模式下温度范围
     *
     * @param familyId
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyModeScopeVO>
     * @author wenyilu
     * @date 2021/1/6 13:33
     */
    @Override
    public List<FamilyModeScopeVO> getFamilyModeScopeConfig(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        return projectSoftConfigService.getFamilyModeTempScopeConfig(familyDO.getProjectId());
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
    public Map<String, Object> getDeviceStatus4VO(String familyId, String deviceId) {
        Map<String, Object> deviceStatusMap = new LinkedHashMap<>();
        // 获取设备
        HomeAutoFamilyDO familyDO = getById(familyId);
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        List<DeviceAttrInfo> attrInfos = deviceAttrInfoService.getAttributesByDeviceId(deviceId, null, AttrAppFlagEnum.ACTIVE.getCode());
        if (CollectionUtils.isEmpty(attrInfos)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        // 定义属性值处理过滤器
        for (DeviceAttrInfo attrInfo : attrInfos) {
            //// 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyDO.getCode(), deviceDO.getCode(), attrInfo.getCode()));

            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo.getId(), attrInfo.getCode())) {
                    attributeValue = filter.appGetStatusHandle(attributeValue, attrInfo.getId(), attrInfo.getCode());
                }
            }

            deviceStatusMap.put(attributeShortCodeConvertFilter.convert(attrInfo.getCode()), attributeValue);
        }
        return deviceStatusMap;
    }



    @Override
    public List<String> getAppShowDeviceAttrs( String deviceId) {
        List<DeviceAttrInfo> attributes = deviceAttrInfoService.getAttributesByDeviceId(deviceId, null, AttrAppFlagEnum.ACTIVE.getCode());
         if(!CollectionUtil.isEmpty(attributes)){
             return attributes.stream().map(i-> {
                return attributeShortCodeConvertFilter.convert(i.getCode());
             }).collect(Collectors.toList());
         }
        return null;
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
        String deviceId = deviceCommandDTO.getDeviceId();
        String familyId = deviceCommandDTO.getFamilyId();
        if(StringUtils.isEmpty(familyId)){
            FamilyUserCheckout userCheckout = familyUserCheckoutService.getByUserId(getUserIdForAppRequest());
            if(userCheckout!=null){
                familyId=userCheckout.getFamilyId();
            }
        }
        HomeAutoFamilyDO familyDO = getById(familyId);
        if(familyDO==null||StringUtils.isEmpty(familyDO.getScreenMac())){
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        if(deviceDO==null){
            throw new BusinessException(ErrorCodeEnumConst.DEVICE_NOT_FOUND);
        }
//        HomeAutoProduct product = getFamilyDeviceProduct(familyId, deviceDO.getCode());
        HomeAutoProduct product = productService.getById(deviceDO.getProductId());
        if(product==null){
            throw new BusinessException(ErrorCodeEnumConst.PRODUCT_NOT_FOUND);
        }
        List<AppDeviceAttributeDTO> commandDTOData = deviceCommandDTO.getData();
        List<ScreenDeviceAttributeDTO> screenAttributeDTOs = Lists.newArrayList();

        for (AppDeviceAttributeDTO commandDTODatum : commandDTOData) {
            if(StringUtils.isEmpty(commandDTODatum.getShortCode())){
                throw new BusinessException(ErrorCodeEnumConst.ATTRIBUTE_SHORT_CODE_REQUIRE);
            }
            String attributeCode = commandDTODatum.getCode();
            if (StringUtils.isEmpty(attributeCode) || !attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)) {
                attributeCode = attributeShortCodeConvertFilter.convert(deviceId, commandDTODatum.getShortCode());
            }
            commandDTODatum.setCode(attributeCode);
            ScreenDeviceAttributeDTO dto = new ScreenDeviceAttributeDTO();
            BeanUtils.copyProperties(commandDTODatum,dto);
            screenAttributeDTOs.add(dto);
        }
        log.info("指令信息获取完毕, 准备发送");
        AdapterDeviceControlDTO controlDTO = new AdapterDeviceControlDTO();
        controlDTO.setFamilyCode(familyDO.getCode());
        controlDTO.setFamilyId(familyId);
        controlDTO.setData(screenAttributeDTOs);
        controlDTO.setTime(System.currentTimeMillis());
        controlDTO.setTerminalMac(familyDO.getScreenMac());
        controlDTO.setProductCode(product.getCode());
        AdapterDeviceControlAckDTO adapterDeviceControlAckDTO = appService.deviceWriteControl(controlDTO);
        if (Objects.isNull(adapterDeviceControlAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else if (!Objects.equals(adapterDeviceControlAckDTO.getCode(), 200)) {
            throw new BusinessException(adapterDeviceControlAckDTO.getMessage());
        }

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
    public void executeScene(String sceneId, String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        if(familyDO==null||StringUtils.isEmpty(familyDO.getScreenMac())){
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        HouseTemplateScene sceneDO = houseTemplateSceneService.getById(sceneId);
        if (sceneDO == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        AdapterSceneControlDTO adapterSceneControlDTO = new AdapterSceneControlDTO();
        adapterSceneControlDTO.setFamilyId(familyId);
        adapterSceneControlDTO.setSceneId(sceneId);
        adapterSceneControlDTO.setFamilyCode(familyDO.getCode());
        adapterSceneControlDTO.setTime(System.currentTimeMillis());
        adapterSceneControlDTO.setSceneNo(org.springframework.util.StringUtils.isEmpty(sceneDO.getSceneNo()) ? sceneId : sceneDO.getSceneNo());
        adapterSceneControlDTO.setTerminalMac(familyDO.getScreenMac());
        AdapterSceneControlAckDTO adapterSceneControlAckDTO = appService.familySceneControl(adapterSceneControlDTO);
        if (Objects.isNull(adapterSceneControlAckDTO)) {
            throw new BusinessException(NETWORK_ERROR);
        } else if (!Objects.equals(adapterSceneControlAckDTO.getCode(), 200
        )) {
            throw new BusinessException(adapterSceneControlAckDTO.getMessage());
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
    public List<FamilySceneVO> listWholeHouseScene(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        List<HouseTemplateScene> scenesByTemplate = houseTemplateSceneService.getScenesByTemplate(familyDO.getTemplateId());
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (HouseTemplateScene scene : scenesByTemplate) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(scene.getId());
            familySceneVO.setSceneName(scene.getName());
            familySceneVO.setSceneIcon(scene.getIcon());
            familySceneVO.setFamilyId(familyId);
            familySceneVO.setTemplateId(familyDO.getTemplateId());
            familySceneVOList.add(familySceneVO);
        }
        return familySceneVOList;
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
    public void notifySceneTimingConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum) {
        HomeAutoFamilyDO familyDO = getById(familyId);

        AdapterConfigUpdateDTO adapterConfigUpdateDTO = new AdapterConfigUpdateDTO();
        adapterConfigUpdateDTO.setFamilyId(familyDO.getId());
        adapterConfigUpdateDTO.setFamilyCode(familyDO.getCode());
        adapterConfigUpdateDTO.setTime(System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        adapterConfigUpdateDTO.setTerminalMac(familyDO.getScreenMac());
        appService.configUpdateConfig(adapterConfigUpdateDTO);
    }


    @Override
    public String getTemplateIdById(String familyId) {
        return baseMapper.getTemplateIdById(familyId);
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
    public FamilyCheckoutVO switchFamily(String userId, String familyId) {
        // 更新切换家庭数据
        familyUserCheckoutService.saveOrUpdate(userId, familyId);

        HomeAutoFamilyBO homeAutoFamilyBO = getHomeAutoFamilyBO(familyId);

        String weatherCode = homeAutoFamilyBO.getWeatherCode();
        if (StringUtils.isEmpty(weatherCode)) {
            throw new BusinessException(ErrorCodeEnumConst.WEATHER_NOT_FOUND);
        }
        // 获取天气信息
        FamilyWeatherVO familyWeatherVO = familyWeatherService.getWeatherByWeatherCode4VO(weatherCode);
        // 获取常用场景信息
        List<FamilySceneVO> familySceneVOList = familyCommonSceneService.getCommonScenesByFamilyId4VO(familyId, homeAutoFamilyBO.getTemplateId());
        // 获取常用设备信息
        List<FamilyDeviceVO> familyDeviceVOList = familyCommonDeviceService.getCommonDevicesByFamilyId4VO(familyId, homeAutoFamilyBO.getTemplateId());

        return FamilyCheckoutVO.builder().weather(familyWeatherVO).commonSceneList(familySceneVOList).commonDeviceList(familyDeviceVOList).build();

    }

    /**
     * 根据家庭及设备编码获取设备
     *
     * @param familyId   家庭Id
     * @param deviceCode 设备编码
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/1/15 15:19
     */
    @Override
    public TemplateDeviceDO getDeviceByDeviceCode(String familyId, String deviceCode) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        String templateId = familyDO.getTemplateId();
        TemplateDeviceDO result = houseTemplateDeviceService.getDeviceByTemplateAndCode(templateId, deviceCode);
        return result;
    }

    @Override
    public String getScreenMacByFamilyId(String familyId) {
        return this.baseMapper.getScreenMacByFamilyId(familyId);
    }

    @Override
    public void bind(String terminalMac, String fimilyCode) {

        HomeAutoFamilyDO familyDO = getOne(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getScreenMac,terminalMac).select(HomeAutoFamilyDO::getCode));
        if (Objects.nonNull(familyDO) && !familyDO.getCode().equals(fimilyCode)){
            throw new BusinessException("该Mac已经被绑定！");
        }
        update(new LambdaUpdateWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getCode, fimilyCode).set(HomeAutoFamilyDO::getScreenMac, terminalMac));
    }

    @Override
    public List<SelectedIntegerVO> getEnableStatus() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayListWithCapacity(FamilyEnableStatusEnum.values().length);
        for (FamilyEnableStatusEnum statusEnum : FamilyEnableStatusEnum.values()) {
            selectedVOS.add(new SelectedIntegerVO(statusEnum.getName(), statusEnum.getType()));
        }
        return selectedVOS;
    }

    /**
     * 获取家庭设备的产品编码
     *
     * @param familyId   家庭ID
     * @param deviceCode 设备编码
     * @return com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct
     * @author wenyilu
     * @date 2021/1/20 13:09
     */
    @Override
    public HomeAutoProduct getFamilyDeviceProduct(String familyId, String deviceCode) {
        TemplateDeviceDO device = getDeviceByDeviceCode(familyId, deviceCode);
        HomeAutoProduct autoProduct = productService.getById(device.getProductId());
        return autoProduct;
    }

    @Override
    public FamilyBaseInfoVO getfamilyBaseInfoById(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        FamilyBaseInfoVO baseInfoVO = BeanUtil.mapperBean(familyDO,FamilyBaseInfoVO.class);
        return baseInfoVO;
    }

    @Override
    public BasePageVO<DeviceMangeFamilyPageVO> getListDeviceMangeFamilyPage(DeviceManageQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<DeviceMangeFamilyPageVO> result = this.baseMapper.getListDeviceMangeFamilyPage(request);
        if (CollectionUtils.isEmpty(result)) {
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<DeviceMangeFamilyPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public TemplateDeviceDO getFamilyDeviceByAttrCode(String familyCode, String code) {
        return houseTemplateDeviceService.getDeviceByTemplateAndAttrCode(iHomeAutoFamilyService.getTemplateIdByFamilyCode(familyCode),code);
    }

    @Override
    public AdapterDeviceStatusReadAckDTO readDeviceStatus(String familyId, String deviceId) {
        if(StringUtils.isEmpty(familyId)){
            FamilyUserCheckout userCheckout = familyUserCheckoutService.getByUserId(getUserIdForAppRequest());
            if(userCheckout!=null){
                familyId=userCheckout.getFamilyId();
            }
        }
        HomeAutoFamilyDO familyDO = getById(familyId);
        if(familyDO==null||StringUtils.isEmpty(familyDO.getScreenMac())){
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_MAC_UN_BIND_FAMILY);
        }
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(deviceId);
        if(deviceDO==null){
            throw new BusinessException(ErrorCodeEnumConst.DEVICE_NOT_FOUND);
        }
//        HomeAutoProduct product = getFamilyDeviceProduct(familyId, deviceDO.getCode());

        HomeAutoProduct product = productService.getById(deviceDO.getProductId());

        if(product==null){
            throw new BusinessException(ErrorCodeEnumConst.PRODUCT_NOT_FOUND);
        }
        List<AppDeviceAttributeDTO> commandDTOData = Lists.newArrayList();
        //获取设备属性
        List<DeviceAttrInfo> attributes = deviceAttrInfoService.getAttributesByDeviceId(deviceId, null, AttrAppFlagEnum.ACTIVE.getCode());

       ;
        log.info("指令信息获取完毕, 准备发送");
        AdapterDeviceStatusReadDTO readDTO = new AdapterDeviceStatusReadDTO();
        readDTO.setFamilyCode(familyDO.getCode());
        readDTO.setFamilyId(familyId);
//        readDTO.setItems(attributes.stream().map(i->{
//            return i.getCode();
//        }).collect(Collectors.toList()));
        readDTO.setTime(System.currentTimeMillis());
        readDTO.setTerminalMac(familyDO.getScreenMac());
        AdapterDeviceStatusReadAckDTO statusReadAckDTO = appService.deviceStatusRead(readDTO);
        if (Objects.isNull(statusReadAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else if (!Objects.equals(statusReadAckDTO.getCode(), 200)) {
            throw new BusinessException(statusReadAckDTO.getMessage());
        }
        return statusReadAckDTO;
    }

    @Override
    public List<String> getScreenMacList() {
        return this.baseMapper.getScreenMacList();
    }

    @Override
    public void updateFamilyName(FamilyUpdateVO request) {
        familyUserService.checkAdmin(request.getId());
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        updateById(familyDO);
    }

    @Override
    public String getTemplateIdByFamilyCode(String familyCode) {
        if (StringUtil.isEmpty(familyCode)){
            return null;
        }
        String templateId = (String) redisUtils.get(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE,familyCode));
        if(StringUtil.isEmpty(templateId)){
            templateId = baseMapper.getTemplateIdByFamilyCode(familyCode);
            redisUtils.set(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE,familyCode),templateId);
        }
        return templateId;
    }



    @Override
    public BasePageVO<TemplateDevicePageVO> getListDeviceByFamilyId(String familyId,Integer pageSize,Integer pageNum) {
        String templateId = this.getTemplateIdById(familyId);
        BasePageVO<TemplateDevicePageVO> data = iHouseTemplateDeviceService.getListPageByTemplateId(templateId,pageNum,pageSize);
        return data;
    }


}
