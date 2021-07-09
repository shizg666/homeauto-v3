package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.*;
import com.landleaf.homeauto.center.device.excel.importfamily.Custemhandler;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.handle.excel.ProtocolSheetWriteHandler;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoArea;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.mqtt.MqttUser;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProduct;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.FamilyBaseInfoBO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.InDoorWeatherVO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZFamilyQryDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.*;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceDetailVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.statistics.FamilyStatistics;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticQryDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductDetailVO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.AppletsService;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.ITemplateFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryBaseInfoVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductDetailVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeLongVo;
import com.landleaf.homeauto.common.domain.vo.realestate.CascadeStringVo;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteBatchDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Supplier;
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

    public static final String BUILDING_NAME = "栋";
    public static final String UNIT_NAME = "单元";

    @Autowired
    private HomeAutoFamilyMapper homeAutoFamilyMapper;
    @Autowired
    private IFamilyUserService familyUserService;
    @Autowired
    private IHomeAutoRealestateService homeAutoRealestateService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;

    @Autowired
    private IFamilyDeviceInfoStatusService iFamilyDeviceInfoStatusService;
    @Autowired
    private AttributeShortCodeConvertFilter attributeShortCodeConvertFilter;

    @Autowired(required = false)
    private UserRemote userRemote;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;

    @Autowired
    private CommonService commonService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ITemplateFloorService templateFloorService;

    @Autowired
    private IHouseTemplateRoomService houseTemplateRoomService;

    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;

    @Autowired
    private IHomeAutoProductService productService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private IMqttUserService iMqttUserService;

    @Autowired
    private IdService idService;
    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IContactScreenService contactScreenService;

    @Autowired
    private IHomeAutoFaultDeviceHavcService havcService;

    @Autowired
    private IHouseTemplateRoomService roomService;

    @Autowired
    private ISysProductService iSysProductService;
    @Autowired
    private IFamilyRoomService iFamilyRoomService;
    @Autowired
    private IFamilySceneService iFamilySceneService;

    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;


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
    public HomeAutoFamilyBO getHomeAutoFamilyBO(Long familyId) {

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
    public String getWeatherCodeByFamilyId(Long familyId) {
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
        HomeAutoFamilyDO familyDO = getById(familyId);
        MyFamilyDetailInfoVO result = new MyFamilyDetailInfoVO();
        List<FloorRoomVO> floors = templateFloorService.getFloorAndRoomDevices
                (familyDO.getTemplateId());
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
    public FamilyInfoForSobotDTO getFamilyInfoForSobotById(Long familyId) {
        return this.baseMapper.getFamilyInfoForSobotById(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilyAddDTO request) {
        buildDoorPlate(request);
        request.setId(idService.getSegmentId());
        buildCode(request);
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        familyDO.setEnableStatus(1);
        familyDO.setScreenMac(org.apache.commons.lang3.StringUtils.EMPTY);
        familyDO.setBuildingName(familyDO.getBuildingCode().concat(BUILDING_NAME));
        familyDO.setUnitName(familyDO.getBuildingCode().concat(UNIT_NAME));
        checkRoomNo(familyDO.getRealestateId(), familyDO.getBuildingCode(), familyDO.getUnitCode(), familyDO.getDoorplate());
        save(familyDO);
        // 新增家庭房间配置信信息
        iFamilyRoomService.addTemplateRoomByTid(familyDO.getTemplateId(),familyDO.getId());
        //新增家庭设备loginProcessingUrl
//        iFamilyDeviceService.addFamilyDevice(familyDO);
//        saveMqttUser(familyDO);
        redisUtils.set(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE, familyDO.getCode()), familyDO.getTemplateId());
    }

    /**
     * 构建门牌
     *
     * @param request
     * @return
     */
    private void buildDoorPlate(FamilyAddDTO request) {
        StringBuilder doorPlate = new StringBuilder();
        String roomNo = request.getRoomNo().length() == 2 ? request.getRoomNo() : "0".concat(request.getRoomNo());
        if (!StringUtil.isEmpty(request.getPrefix())) {
            doorPlate.append(request.getPrefix()).append(request.getFloor()).append(roomNo);
        } else {
            doorPlate.append(request.getFloor()).append(roomNo);
        }
        if (!StringUtil.isEmpty(request.getSuffix())) {
            doorPlate.append(request.getSuffix());
        }
        request.setDoorplate(doorPlate.toString());
    }

    private void saveBatchMqttUser(List<HomeAutoFamilyDO> data) {
        List<MqttUser> mqttUsers = Lists.newArrayListWithCapacity(data.size());
        data.forEach(obj -> {
            MqttUser mqttUser = new MqttUser();
            String name = obj.getCode().concat("-").concat("0");
            String password = MQTT_USER_PASSWORD_PREX.concat(obj.getBuildingCode()).concat(obj.getUnitCode()).concat(obj.getRoomNo());
            mqttUser.setUserName(name);
            mqttUser.setIsSuperuser(0);
            mqttUser.setFamilyId(obj.getId());
            String passwordStr = DigestUtil.sha256Hex(password);
            mqttUser.setPassword(passwordStr);
            mqttUsers.add(mqttUser);
        });
        iMqttUserService.saveBatch(mqttUsers);
    }

    private void saveMqttUser(HomeAutoFamilyDO familyDO) {
        MqttUser mqttUser = new MqttUser();
        String name = familyDO.getCode().concat("-").concat("0");
        String password = MQTT_USER_PASSWORD_PREX.concat(familyDO.getBuildingCode()).concat(familyDO.getUnitCode()).concat(familyDO.getRoomNo());
        mqttUser.setUserName(name);
        mqttUser.setIsSuperuser(0);
        mqttUser.setFamilyId(familyDO.getId());
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
    private void buildCode(FamilyAddDTO request) {
        PathBO realestate = homeAutoRealestateService.getRealestatePathInfoById(request.getRealestateId());
        PathBO project = iHomeAutoProjectService.getProjectPathInfoById(request.getProjectId());
        String path = project.getPath().concat("/").concat(request.getBuildingCode()).concat("/").concat(request.getUnitCode()).concat("/").concat(String.valueOf(request.getId()));
        String path1 = String.valueOf(request.getRealestateId()).concat("/").concat(String.valueOf(request.getProjectId())).concat("/").concat(request.getBuildingCode()).concat("/").concat(request.getUnitCode()).concat("/").concat(String.valueOf(request.getId()));
        String path2 = String.valueOf(request.getRealestateId()).concat("/").concat(request.getBuildingCode()).concat("/").concat(request.getUnitCode()).concat("/").concat(String.valueOf(request.getId()));
        StringBuilder pathName = new StringBuilder();
        pathName.append(realestate.getPathName()).append("/").append(project.getName()).append("/").append(request.getBuildingCode()).append("栋").append(request.getUnitCode()).append("单元").append(request.getDoorplate());
        request.setPath(path);
        request.setPath1(path1);
        request.setPath2(path2);
        request.setPathName(pathName.toString());
        String bulidCode = request.getBuildingCode().length() == 2 ? request.getBuildingCode() : "0".concat(request.getBuildingCode());
        String unitCode = request.getUnitCode().length() == 2 ? request.getUnitCode() : "0".concat(request.getUnitCode());
//        request.setBuildingCode(bulidCode);
//        request.setUnitCode(unitCode);
        request.setCode(new StringBuilder().append(project.getCode()).append("-").append(bulidCode).append(unitCode).append(request.getDoorplate()).toString());
        //构建名称
        request.setName(realestate.getName().concat("-").concat(request.getDoorplate()));
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
        HomeAutoFamilyDO family = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        family.setCode(code);
        updateById(family);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        HomeAutoFamilyDO familyDO = getById(request.getId());
        if (Objects.isNull(familyDO)) {
            throw new BusinessException("id不存在！");
        }
        removeById(request.getId());
        familyUserService.remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getId()));
//        familyUserCheckoutService.deleteByFamilyId(request.getId());
//        iMqttUserService.removeByFamilyId(familyDO.getId());
        iFamilyRoomService.remove(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFamilyId,request.getId()));
        iFamilySceneService.removeByFamilyId(request.getId());
        redisUtils.del(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE, familyDO.getCode()));

    }

    @Override
    public List<FamilyPageVO> getListByUnitId(String id) {
        return this.baseMapper.getListByUnitId(id);
    }


    @Override
    public List<Long> getListIdByPaths(List<String> path) {
        if (CollectionUtils.isEmpty(path)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return this.baseMapper.getListIdByPaths(path);
    }
    @Override
    public List<Long> getListIdByPathsAndType(List<String> paths,Integer type) {
        if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayListWithCapacity(0);
        }
        String cloumnName = "path";
        switch (type){
            case 0:
                break;
            case 1:
                cloumnName ="path_1";
                break;
            case 2:
                cloumnName ="path_2";
                break;
        }
        QueryWrapper<HomeAutoFamilyDO> queryWrapper = new QueryWrapper<>();
        String finalCloumnName = cloumnName;
        for (String path : paths) {
            queryWrapper.or(i->i.likeRight(finalCloumnName,path));
        }
        queryWrapper.select("id");
        List<HomeAutoFamilyDO> list = list(queryWrapper);

        return CollectionUtils.isEmpty(list)?null:list.stream().map(i->i.getId()).collect(Collectors.toList());
    }


    @Override
    public List<FamilyBaseInfoDTO> getBaseInfoByProjectId(String familyId) {
        return this.baseMapper.getBaseInfoByProjectId(familyId);
    }


    private void checkRoomNo(Long realestateId, String buildNo, String unitNo, String doorplate) {
        int count = this.baseMapper.existRoomNo(realestateId, buildNo, unitNo, doorplate);
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
//        FamilyImportDataListener listener = new FamilyImportDataListener(iHomeAutoFamilyService, homeAutoRealestateService, iHomeAutoProjectService, iProjectHouseTemplateService);
//        EasyExcel.read(file.getInputStream(), ImportFamilyModel.class, listener).sheet().doRead();
//        if (CollectionUtils.isEmpty(listener.getErrorlist())) {
//            Response result = new Response();
//            result.setSuccess(true);
//            result.setMessage("操作成功!");
//            result.setResult(null);
//            String resBody = JSON.toJSONString(result);
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setCharacterEncoding("utf-8");
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.print(resBody);
//            printWriter.flush();
//            printWriter.close();
//            return;
//        }
//        try {
//            String fileName = "失败列表";
//            commonService.setResponseHeader(response, fileName);
//            OutputStream os = response.getOutputStream();
//            List<ImporFamilyResultVO> familyResultVOS = BeanUtil.mapperList(listener.getErrorlist(), ImporFamilyResultVO.class);
//            EasyExcel.write(os, ImporFamilyResultVO.class).sheet("失败列表").doWrite(familyResultVOS);
//        } catch (IOException e) {
//            log.error("模板下载失败，原因：{}", e.getMessage());
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), e.getMessage());
//        }

    }


    @Override
    public List<SelectedVO> getListFamilySelects(String projectId) {
        List<String> paths = commonService.getUserPathScope();
//        List<String> paths = Lists.newArrayList();
//        paths.add("CN");
        return this.baseMapper.getListFamilyByPaths(paths, projectId);
    }

    @Override
    public BasePageVO<FamilyPageVO> getListPage(com.landleaf.homeauto.center.device.model.vo.family.FamilyQryDTO request) {
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
                List<Long> familyIdList = familyUserDOList.stream().map(FamilyUserDO::getFamilyId).collect(Collectors.toList());

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
                                .familyName(i.getName()).familyNumber(i.getRoomNo())
                                .templateId(i.getTemplateId()).build();
                    }).collect(Collectors.toList()));
                }
            }
        }
        return homeAutoFamilyBOList;
    }


    /**
     * 获取家庭下楼层下房间信息
     *
     * @param familyId         家庭ID
     * @param templateId       户型ID
     * @param floorId          楼层ID
     * @param deviceFilterFlag
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO>
     * @author wenyilu
     * @date 2021/1/6 11:29
     */
    @Override
    public List<FamilyRoomBO> getFamilyRoomBOByTemplateAndFloor(Long familyId, Long templateId, String floorId, Integer deviceFilterFlag) {
        List<TemplateRoomDO> templateRoomDOS = houseTemplateRoomService.getFamilyRoomBOByTemplateAndFloor(floorId, templateId);
        HomeAutoFamilyDO familyDO = getById(familyId);
        List<TemplateDeviceDO> templateDevices = houseTemplateDeviceService.getTemplateDevices(templateId);
        List<FamilyRoomBO> familyRoomBOList = new LinkedList<>();
        if (!CollectionUtils.isEmpty(templateDevices)) {
            Map<Long, Map<Integer, Long>> room_systemFlag_count = templateDevices.stream()
                    .collect(Collectors.groupingBy(TemplateDeviceDO::getRoomId, Collectors.groupingBy(TemplateDeviceDO::getSystemFlag, Collectors.counting())));
            List<Long> roomIds = templateDevices.stream().map(i -> i.getRoomId()).collect(Collectors.toList());
            List<TemplateRoomDO> hasDeviceRooms = templateRoomDOS.stream().filter(i -> roomIds.contains(i.getId())).collect(Collectors.toList());
            for (TemplateRoomDO roomDO : hasDeviceRooms) {
                boolean flag = true;
                Long sys_sub_count = room_systemFlag_count.get(roomDO.getId()).get(FamilySystemFlagEnum.SYS_SUB_DEVICE.getType());
                Long normal_count = room_systemFlag_count.get(roomDO.getId()).get(FamilySystemFlagEnum.NORMAL_DEVICE.getType());
                if (deviceFilterFlag != null) {
                    switch (deviceFilterFlag) {
                        case 0:
                            break;
                        case 1:
                            //有系統子设备的
                            if (sys_sub_count == null || sys_sub_count < 0) {
                                flag = false;
                            }
                            break;
                        case 2:
                            //有普通设备的
                            if (normal_count == null || normal_count < 0) {
                                flag = false;
                            }
                            break;
                        case 3:
                            //两者皆有的
                            if (normal_count == null || normal_count < 0 || sys_sub_count == null || sys_sub_count < 0) {
                                flag = false;
                            }
                            break;

                    }
                }
                if (flag) {
                    FamilyRoomBO familyRoomBO = new FamilyRoomBO();
                    // 1. 家庭信息
                    familyRoomBO.setFamilyId(String.valueOf(roomDO.getId()));
                    familyRoomBO.setFamilyCode(familyDO.getCode());
                    familyRoomBO.setFamilyName(roomDO.getName());
                    familyRoomBO.setTemplateId(BeanUtil.convertLong2String(templateId));

                    // 2. 楼层信息
                    familyRoomBO.setFloorId(roomDO.getFloor());
                    familyRoomBO.setFloorName(roomDO.getFloor());
                    familyRoomBO.setFloorNum(roomDO.getFloor());

                    // 3. 房间信息
                    familyRoomBO.setRoomId(String.valueOf(roomDO.getId()));
                    familyRoomBO.setRoomName(roomDO.getName());
                    familyRoomBO.setRoomIcon1(roomDO.getIcon());
                    familyRoomBO.setRoomIcon2(roomDO.getImgIcon());
                    familyRoomBO.setImgApplets(roomDO.getImgApplets());
                    familyRoomBO.setImgExpand(roomDO.getImgExpand());
//            familyRoomBO.setRoomCode(templateRoomDO.getCode());
                    familyRoomBO.setRoomTypeEnum(RoomTypeEnum.getInstByType(roomDO.getType()));

                    familyRoomBOList.add(familyRoomBO);
                }
            }
        }
        return familyRoomBOList;
    }


    @Override
    public List<String> getAppShowDeviceAttrs(String deviceId) {
        TemplateDeviceDO deviceDO = houseTemplateDeviceService.getById(BeanUtil.convertString2Long(deviceId));
        List<ScreenProductAttrBO> attributes = contactScreenService.getDeviceFunctionAttrsByProductCode(deviceDO.getProductCode());
        if (!CollectionUtil.isEmpty(attributes)) {
            return attributes.stream().map(i -> {
                return attributeShortCodeConvertFilter.convert(i.getAttrCode());
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void updateFamilysTempalteId(FamilyTempalteUpdateDTO request) {
        update(new LambdaUpdateWrapper<HomeAutoFamilyDO>().in(HomeAutoFamilyDO::getId, request.getFamilyIds()).set(HomeAutoFamilyDO::getTemplateId, request.getTemplateId()));
    }

    @Override
    public List<ProjectFamilyTotalVO> getProjectFamilyTotal(Long projcetId) {
        List<ProjectFamilyTotalVO> result = Lists.newArrayList();
        List<ProjectFamilyTotalBO> data = this.baseMapper.getProjectFamilyTotal(projcetId);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        Map<String, List<ProjectFamilyTotalBO>> dataMap = data.stream().collect(Collectors.groupingBy(ProjectFamilyTotalBO::getBuildingCode));
        dataMap.forEach((bulidCode, list) -> {
            int familyNum = list.size();
            int unitNum = list.stream().map(o -> o.getUnitCode()).collect(Collectors.toSet()).size();
            int floor = list.stream().map(o -> o.getFloor()).collect(Collectors.toSet()).size();
            int template = list.stream().map(o -> o.getTemplateId()).collect(Collectors.toSet()).size();
            result.add(ProjectFamilyTotalVO.builder().buildingCode(bulidCode).familyNum(familyNum).templateNum(template).floorNum(floor).unitNum(unitNum).build());
        });
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(ProjectConfigDeleteBatchDTO request) {
        List<String> familyCodeList = this.baseMapper.getFamilyCodelistByIds(request.getIds());
        removeByIds(request.getIds());
        familyUserService.remove(new LambdaQueryWrapper<FamilyUserDO>().in(FamilyUserDO::getFamilyId, request.getIds()));
//        iMqttUserService.removeByFamilyIds(request.getIds());
        List<String> keys = familyCodeList.stream().map(data -> {
            return String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE, data);
        }).collect(Collectors.toList());
        iFamilyRoomService.remove(new LambdaQueryWrapper<FamilyRoomDO>().in(FamilyRoomDO::getFamilyId, request.getIds()));
        iFamilySceneService.removeByFamilyIds(request.getIds());
        redisUtils.pipleSet((RedisConnection connection) -> {
            keys.forEach(key -> {
                connection.del(key.getBytes());
            });
            return null;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(FamilyAddBatchDTO request) {
        String lock = String.format(RedisCacheConst.FAMILY_IMPORT, String.valueOf(request.getRealestateId()), request.getBuildingCode());
        if (!redisUtils.getLock(lock, 5 * 60L)) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(), "该楼盘楼栋有人在做批量操作请稍等");
        }
        String buildCode = request.getBuildingCode();
        Long realestateId = request.getRealestateId();
        Long projcetId = request.getProjectId();
        String prefix = request.getPrefix();
        String suffix = request.getSuffix();
        try {
            String[] floors = request.getFloor().split(",");
            int startFloor = Integer.parseInt(floors[0]);
            int endFloor = Integer.parseInt(floors[1]);
            Set<Integer> skipFloor = null;
            if (!CollectionUtils.isEmpty(request.getSkipFloor())) {
                skipFloor = request.getSkipFloor().stream().collect(Collectors.toSet());
            }
            List<HomeAutoFamilyDO> familyDOlist = list(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getRealestateId, request.getRealestateId()).eq(HomeAutoFamilyDO::getBuildingCode, request.getBuildingCode()).select(HomeAutoFamilyDO::getId, HomeAutoFamilyDO::getDoorplate, HomeAutoFamilyDO::getUnitCode));
            //原有的家庭 会覆盖关联的户型
            List<HomeAutoFamilyDO> updateList = Lists.newArrayList();
            Map<String, Long> familyMap = Maps.newHashMap();

            if (!CollectionUtils.isEmpty(familyDOlist)) {
                familyDOlist.forEach(data -> {
                    familyMap.put(data.getUnitCode().concat(data.getDoorplate()), data.getId());
                });
            }
            List<HomeAutoFamilyDO> data = Lists.newArrayList();
            List<FamilyAddBatchDTO.UnitInfo> units = request.getUnits();
            for (int i = 0; i < units.size(); i++) {
                String unitCode = String.valueOf(i + 1);
                for (int j = startFloor; j <= endFloor; j++) {
                    String floor = String.valueOf(j);
                    if (Objects.nonNull(skipFloor) && skipFloor.contains(j)) {
                        continue;
                    }
                    List<FamilyAddBatchDTO.UnitRoomInfo> roomList = units.get(i).getRooms();
                    if (CollectionUtils.isEmpty(roomList)) {
                        continue;
                    }

                    List<String> roomNumList = roomList.stream().map(FamilyAddBatchDTO.UnitRoomInfo::getRoomNo).collect(Collectors.toList());
                    Set<String> roomSet = Sets.newHashSet(roomNumList);
                    if (roomNumList.size() != roomSet.size()) {
                        throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(), unitCode.concat("单元房号重复!"));
                    }
                    for (int i1 = 0; i1 < roomList.size(); i1++) {
                        FamilyAddDTO familyAddDTO = FamilyAddDTO.builder().buildingCode(buildCode).unitCode(unitCode).floor(floor).roomNo(roomList.get(i1).getRoomNo()).templateId(roomList.get(i1).getTemplateId()).realestateId(realestateId).projectId(projcetId).prefix(prefix).suffix(suffix).build();
                        buildDoorPlate(familyAddDTO);
                        String key = familyAddDTO.getUnitCode().concat(familyAddDTO.getDoorplate());
                        if (Objects.nonNull(familyMap) && familyMap.containsKey(key)) {
                            Long familyId = familyMap.get(key);
                            HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
                            familyDO.setId(familyId);
                            familyDO.setTemplateId(roomList.get(i1).getTemplateId());
                            updateList.add(familyDO);
                            continue;
                        }
                        buildCode(familyAddDTO);
                        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(familyAddDTO, HomeAutoFamilyDO.class);
                        familyDO.setEnableStatus(1);
                        familyDO.setScreenMac(StringUtils.EMPTY);
                        familyDO.setBuildingName(familyDO.getBuildingCode().concat(BUILDING_NAME));
                        familyDO.setUnitName(familyDO.getBuildingCode().concat(UNIT_NAME));
                        data.add(familyDO);
                    }
                }
            }
            int total = data.size();
            List<Long> ids = idService.getListSegmentId(total);
            Map<Long,Long> familyTempalte = Maps.newHashMap();
            for (int i = 0; i < data.size(); i++) {
                HomeAutoFamilyDO familyDO = data.get(i);
                familyDO.setId(ids.get(i));
                familyTempalte.put(familyDO.getId(),familyDO.getTemplateId());
                familyDO.setPath(familyDO.getPath().replace("null", String.valueOf(familyDO.getId())));
                familyDO.setPath1(familyDO.getPath1().replace("null", String.valueOf(familyDO.getId())));
                familyDO.setPath2(familyDO.getPath2().replace("null", String.valueOf(familyDO.getId())));
            }
            if (!CollectionUtils.isEmpty(data)) {
                saveBatch(data);
                iFamilyRoomService.addRoomOnImportFamily(familyTempalte);
//                iFamilyDeviceService.addBatchFamilyDevice(data);
//            saveBatchMqttUser(data);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                updateBatchById(updateList);
            }
        } finally {
            redisUtils.del(lock);
        }
    }

    @Override
    public BasePageVO<SpaceManageStaticPageVO> spaceManageStatistics(SpaceManageStaticQryDTO requestBody) {
        BasePageVO<SpaceManageStaticPageVO> result = new BasePageVO<SpaceManageStaticPageVO>();
        List<SpaceManageStaticPageVO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        data = homeAutoFamilyMapper.spaceManageStatistics(requestBody.getRealestateId(), requestBody.getProjectId(), requestBody.getBuildingCode());
        PageInfo pageInfo = new PageInfo(data);
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public BasePageVO<FamilyDevicePageVO> listFamilyDevicePage(FamilyDeviceQryDTO requestBody) {
        BasePageVO<FamilyDevicePageVO> result = new BasePageVO<FamilyDevicePageVO>();
        List<FamilyDevicePageVO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        //获取家庭系统产品信息
        data = homeAutoFamilyMapper.listFamilyDevice(requestBody.getRealestateId(), requestBody.getProjectId(),
                requestBody.getBuildingCode(), requestBody.getFamilyName(), requestBody.getDeviceName(),requestBody.getSystemFlag(), requestBody.getDeviceSn(),requestBody.getFamilyId());
        SysProduct sysProduct = iSysProductService.getSysProductByProjectId(requestBody.getProjectId());
        data.forEach(obj->{
            if(DeviceTypeEnum.PUTONG.getType().equals(obj.getSystemFlag())){
                obj.setSysProductName("-");
            }else if ((DeviceTypeEnum.SUB_SYSTEM.getType().equals(obj.getSystemFlag()))){
                obj.setSysProductName(Objects.isNull(sysProduct)?"-":sysProduct.getName());
            }
            if(Objects.isNull(obj.getOnlineFlag())){
                obj.setOnlineFlagStr("-");
            }else {
                obj.setOnlineFlagStr(OnlineStatusEnum.getInstByType(obj.getOnlineFlag()) == null ? "-" : OnlineStatusEnum.getInstByType(obj.getOnlineFlag()).getName());
            }
        });
        PageInfo pageInfo = new PageInfo(data);
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindMac(Long projectId, String buildingCode, String unitCode, String doorplate, String terminalMac) {
        if (StringUtils.isEmpty(buildingCode) ||
                StringUtils.isEmpty(unitCode) ||
                StringUtils.isEmpty(doorplate) ||
                StringUtils.isEmpty(terminalMac)
        ) {
            throw new BusinessException("缺少必要参数!");
        }
        QueryWrapper<HomeAutoFamilyDO> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("screen_mac",terminalMac);

        QueryWrapper<HomeAutoFamilyDO> queryWrapper2 = new QueryWrapper<HomeAutoFamilyDO>();
        queryWrapper2.eq("project_id", projectId);
        queryWrapper2.eq("building_code", buildingCode);
        queryWrapper2.eq("unit_code", unitCode);
        queryWrapper2.eq("doorplate", doorplate);

        HomeAutoFamilyDO exits = getOne(queryWrapper1);
        HomeAutoFamilyDO updateFamily = getOne(queryWrapper2);
        if(Objects.isNull(updateFamily)){
            throw new BusinessException("参数不正确，该家庭不存在");
        }
        if(!Objects.isNull(exits)){
           if(exits.getId().longValue()!=updateFamily.getId().longValue()){
               throw new BusinessException("mac地址已被其它家庭绑定");
           }
        }
        updateFamily.setScreenMac(terminalMac);
        boolean update = updateById(updateFamily);
        if (!update) {
            throw new BusinessException("家庭不存在!");
        }
    }

    @Override
    public List<String> getListBuildByProjectId(Long projectId) {
        List<String> data = this.baseMapper.getListBuildByProjectId(projectId);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1) - Integer.valueOf(o2);
            }
        });
        return data;
    }

    @Override
    public List<SelectedVO> getSelectsUnitByBuild(Long projectId, String buildCode) {
        List<String> units = this.baseMapper.getSelectsUnitByBuild(projectId, buildCode);
        if (CollectionUtils.isEmpty(units)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SelectedVO> data = units.stream().map(o -> {
            return new SelectedVO(o.concat("单元"), o);
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public List<SelectedVO> getSelectsfloorByBuild(Long projectId, String buildCode) {
        List<String> floors = this.baseMapper.getSelectsfloorByBuild(projectId, buildCode);
        if (CollectionUtils.isEmpty(floors)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SelectedVO> data = floors.stream().map(o -> {
            return new SelectedVO(o.concat("楼"), o);
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public void removeBuilding(FamilyBuildDTO familyBuildDTO) {
        remove(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getProjectId, familyBuildDTO.getProjectId()).eq(HomeAutoFamilyDO::getBuildingCode, familyBuildDTO.getBuildingCode()));
    }

    @Override
    public List<Long> getFamilyIdsBind(Long templateId) {
        return this.baseMapper.getFamilyIdsBind(templateId);
    }

    @Override
    public List<SelectedVO> getSelectBuildByProjectId(Long projectId) {
        List<String> buildList = this.getListBuildByProjectId(projectId);
        if (CollectionUtils.isEmpty(buildList)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SelectedVO> builds = buildList.stream().map(o -> {
            return new SelectedVO(o.concat("栋"), o);
        }).collect(Collectors.toList());
        return builds;
    }

    @Override
    public HomeAutoFamilyDO getFamilyByMac(String mac) {
        if (StringUtils.isEmpty(mac)) {
            return null;
        }
        QueryWrapper<HomeAutoFamilyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("screen_mac", mac);
        return getOne(queryWrapper);
    }

    @Override
    public List<CascadeStringVo> getCascadeBuildUnit(Long realestateId) {
        List<HomeAutoFamilyDO> familyDOS = list(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getRealestateId, realestateId).select(HomeAutoFamilyDO::getId, HomeAutoFamilyDO::getBuildingCode, HomeAutoFamilyDO::getUnitCode, HomeAutoFamilyDO::getDoorplate));
        if (CollectionUtils.isEmpty(familyDOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        Map<String, List<HomeAutoFamilyDO>> buildMap = familyDOS.stream().collect(Collectors.groupingBy(HomeAutoFamilyDO::getBuildingCode));
        List<CascadeStringVo> result = Lists.newArrayListWithExpectedSize(buildMap.size());
        buildMap.forEach((build, units) -> {
            CascadeStringVo buildVO = CascadeStringVo.builder().label(build.concat("栋")).value(build).build();
            Map<String, List<HomeAutoFamilyDO>> unitMap = units.stream().collect(Collectors.groupingBy(HomeAutoFamilyDO::getUnitCode));
            List<CascadeStringVo> unitsVOs = Lists.newArrayListWithExpectedSize(unitMap.size());
            unitMap.forEach((unit, familys) -> {
                CascadeStringVo unitVO = CascadeStringVo.builder().label(unit.concat("单元")).value(unit).build();
                List<CascadeLongVo> familyVOs = Lists.newArrayListWithExpectedSize(familys.size());
                familys.forEach(family -> {
                    CascadeLongVo famulyVO = CascadeLongVo.builder().label(family.getRoomNo()).value(family.getId()).build();
                    familyVOs.add(famulyVO);
                });
                unitVO.setChildren(familyVOs);
                unitsVOs.add(unitVO);
            });
            buildVO.setChildren(unitsVOs);
            result.add(buildVO);
        });
        return result;
    }

    @Override
    public List<Long> getListFamilyIdsByPath2(List<String> pathList) {
        return this.baseMapper.getListFamilyIdsByPath2(pathList);
    }

    @Override
    public List<MyFamilyInfoVO> getMyFamily(String userId) {
        return this.baseMapper.getMyFamily(userId);
    }

    @Override
    public List<FamilyUserInfoVO> getMyFamilyUserInfo(Long familyId) {
        return this.baseMapper.getMyFamilyUserInfo(familyId);
    }

    @Override
    public List<HomeAutoFamilyDO> getFamilyByProject(Long projectId) {
        QueryWrapper<HomeAutoFamilyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",projectId);
        return list(queryWrapper);
    }

    @Override
    public Integer countByProject(Long projectId) {
        QueryWrapper<HomeAutoFamilyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",projectId);
        return count(queryWrapper);
    }

    @Override
    public List<Long> getListIdByRooms(FamilyDTO2 familyDTO2, Long realestateId) {

        return this.baseMapper.getListIdByRooms(familyDTO2,realestateId);
    }

    @Override
    public List<CategoryBaseInfoVO> getListDeviceCategory(Long templateId) {
        return this.baseMapper.getListDeviceCategory(templateId);
    }


    @Override
    public List<FamilyStatistics> getFamilyCountByPath2(List<String> paths) {
        return this.baseMapper.getFamilyCountByPath2(paths);
    }

    @Override
    public FamilyDeviceDetailVO getFamilyDeviceDetail(Long familyId, Long deviceId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        Long templateId = iHomeAutoFamilyService.getTemplateIdById(familyId);

        FamilyDeviceDetailVO result = this.baseMapper.getFamilyDeviceDetail(familyId,templateId,deviceId);

        if(result !=null) {
            result.setFamilyName(familyDO.getName());
            SysProduct sysproduct = iSysProductService.getSysProductByProjectId(result.getProjectId());
            if(Objects.nonNull(sysproduct)){
                result.setSysProductName(sysproduct.getName());
            }
            if (Objects.isNull(result.getOnlineFlag())){
                //没查到就是离线
                result.setOnlineFlag(0);
                result.setOnlineFlagStr("离线");
            }
        }
        return result;
    }

    @Override
    public void updateFamilyMacAndIp(FamilyUpMacIpDTO requestDTO) {

        HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
        familyDO.setIp(requestDTO.getIp());
        familyDO.setScreenMac(requestDTO.getScreenMac());
        familyDO.setId(requestDTO.getFamilyId());
        updateFamilyMacAndIpCheck(requestDTO);
        updateById(familyDO);
    }

    private void updateFamilyMacAndIpCheck(FamilyUpMacIpDTO requestDTO) {
        if (!StringUtil.isEmpty(requestDTO.getScreenMac())){
            HomeAutoFamilyDO familyDO1 = getOne(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getScreenMac,requestDTO.getScreenMac()).select(HomeAutoFamilyDO::getId,HomeAutoFamilyDO::getScreenMac));
            if(Objects.isNull(familyDO1)){
                return;
            }
            if(familyDO1.getId().equals(requestDTO.getFamilyId())){
                return;
            }
            throw new BusinessException("该Mac已经被绑定！");
        }
    }

    @Override
    public Long getTemplateIdById(Long familyId) {
        return baseMapper.getTemplateIdById(familyId);
    }

    /**
     * 根据家庭及设备编码获取设备
     *
     * @param familyId 家庭Id
     * @param deviceSn 设备号
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/1/15 15:19
     */
    @Override
    public TemplateDeviceDO getDeviceByDeviceCode(Long familyId, String deviceSn) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        Long templateId = familyDO.getTemplateId();
        TemplateDeviceDO result = houseTemplateDeviceService.getDeviceByTemplateAndCode(templateId, deviceSn);
        return result;
    }

    @Override
    public String getScreenMacByFamilyId(String familyId) {
        return this.baseMapper.getScreenMacByFamilyId(familyId);
    }

    @Override
    public void bind(String terminalMac, String fimilyCode) {

        HomeAutoFamilyDO familyDO = getOne(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getScreenMac, terminalMac).select(HomeAutoFamilyDO::getCode));
        if (Objects.nonNull(familyDO) && !familyDO.getCode().equals(fimilyCode)) {
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
    public HomeAutoProduct getFamilyDeviceProduct(Long familyId, String deviceCode) {
        TemplateDeviceDO device = getDeviceByDeviceCode(familyId, deviceCode);
        HomeAutoProduct autoProduct = productService.getById(device.getProductId());
        return autoProduct;
    }

    @Override
    public FamilyBaseInfoVO getfamilyBaseInfoById(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        FamilyBaseInfoVO baseInfoVO = BeanUtil.mapperBean(familyDO, FamilyBaseInfoVO.class);
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
    public BasePageVO<DeviceMangeFamilyPageVO2> getListDeviceMangeFamilyPage2(List<Long> familyIds,String deviceName,String categoryCode,Integer pageSize,Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize, true);
        List<DeviceMangeFamilyPageVO2> result = this.baseMapper.getListDeviceMangeFamilyPage2(familyIds, deviceName,categoryCode);
        if (CollectionUtils.isEmpty(result)) {
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }

        for (DeviceMangeFamilyPageVO2 vo2:result) {

            FamilyDeviceInfoStatus status =  iFamilyDeviceInfoStatusService.getFamilyDeviceInfoStatus(vo2.getFamilyId(),vo2.getDeviceId());

            if (status != null && status.getOnlineFlag() == 1){
                vo2.setOnline("在线");
            }else {
                vo2.setOnline("离线");
            }

        }

        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<DeviceMangeFamilyPageVO2> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public List<String> getScreenMacList() {
        return this.baseMapper.getScreenMacList();
    }

    @Override
    public String getTemplateIdByFamilyCode(String familyCode) {
        if (StringUtil.isEmpty(familyCode)) {
            return null;
        }
        String templateId = (String) redisUtils.get(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE, familyCode));
        if (StringUtil.isEmpty(templateId)) {
            templateId = baseMapper.getTemplateIdByFamilyCode(familyCode);
            redisUtils.set(String.format(RedisCacheConst.FAMILYCDE_TO_TEMPLATE, familyCode), templateId);
        }
        return templateId;
    }


    @Override
    public BasePageVO<TemplateDevicePageVO> getListDeviceByFamilyId(String familyId, Integer pageSize, Integer pageNum) {
        Long templateId = this.getTemplateIdById(Long.valueOf(familyId));
        BasePageVO<TemplateDevicePageVO> data = iHouseTemplateDeviceService.getListPageByTemplateId(templateId, pageNum, pageSize);
        return data;
    }

    @Override
    public List<FamilyDeviceDetailVO> getListDeviceByCategory(Long familyId, String categoryCode) {


        List<FamilyDeviceDetailVO> familyDeviceDetailVOS = Lists.newArrayList();
        Long templateId = getTemplateIdById(familyId);

        String categoryName = "";
        List<CategoryBaseInfoVO> catList = iHomeAutoCategoryService.getListCategoryBaseInfo().stream().
                filter(s->s.getCode().equals(categoryCode)).
                collect(Collectors.toList());

        if(catList.size()>0){
            categoryName = catList.get(0).getName();
        }
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.getTemplateDevices(templateId);

        if (deviceDOS.size()<=0){
            return familyDeviceDetailVOS;
        }

        for (TemplateDeviceDO item:deviceDOS){
            if (categoryCode.equals(item.getCategoryCode())){
                FamilyDeviceDetailVO familyDeviceDetailVO = getFamilyDeviceDetail(familyId,item.getId());
                String productName = "";
                if (item.getSystemFlag()==2){
                    SysProductDetailVO sysName = iSysProductService.getDetailSysProdut(item.getProductId());
                    if (sysName !=null){
                        productName = sysName.getName();
                    }


                }else {
                    ProductDetailVO pdVo =  productService.getProductDetailInfo(item.getProductId());
                    if (pdVo !=null){
                        productName = pdVo.getName();
                    }
                }

                if(familyDeviceDetailVO != null && StringUtils.isNotBlank(familyDeviceDetailVO.getDeviceSn()) ){
                    familyDeviceDetailVO.setCategoryName(categoryName);
                    familyDeviceDetailVO.setProductName(productName);
                    familyDeviceDetailVOS.add(familyDeviceDetailVO);
                }
            }

        }
        return familyDeviceDetailVOS;
    }

    @Override
    public BasePageVO<FaultMangeFamilyPageVO> getListFaultMangeFamilyPage2(List<Long> familyIds2, String faultMsg, String startTime, String endTime, Integer pageSize, Integer pageNum) {

        BasePageVO<FaultMangeFamilyPageVO> result = new BasePageVO<FaultMangeFamilyPageVO>();
        List<FaultMangeFamilyPageVO> data = Lists.newArrayList();
        PageHelper.startPage(pageNum, pageSize, true);

        LambdaQueryWrapper<HomeAutoFaultDeviceHavcDO> queryWrapper = new LambdaQueryWrapper<>();

        if (!CollectionUtils.isEmpty(familyIds2)){
            queryWrapper.in(HomeAutoFaultDeviceHavcDO::getFamilyId ,familyIds2);
        }

        if (StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
            queryWrapper.apply("(fault_time>= TO_DATE('" + startTime + "','yyyy-mm-dd') and fault_time<= TO_DATE('" + endTime + "','yyyy-mm-dd')) ");
        }

        if (StringUtils.isNotBlank(faultMsg)){
            queryWrapper.like(HomeAutoFaultDeviceHavcDO::getFaultMsg,faultMsg);
        }

        queryWrapper.orderByDesc(HomeAutoFaultDeviceHavcDO::getFaultTime);


        List<HomeAutoFaultDeviceHavcDO> records = havcService.list(queryWrapper);

        PageInfo pageInfo = new PageInfo(records);
        if (!org.springframework.util.CollectionUtils.isEmpty(records)) {
            data.addAll(records.stream().map(i -> {
                return convertToVO2(i);
            }).collect(Collectors.toList()));
        }
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;



    }

    @Override
    public Long getFamilyIdByQryObj(Long realestateId, JZFamilyQryDTO request) {
        return this.baseMapper.getFamilyIdByQryObj(realestateId,request.getDoorplate(),request.getBuildCode(),request.getUnitCode());
    }

    @Override
    public FamilyBaseInfoBO getFamilyInfoByQryObj(Long realestateId, JZFamilyQryDTO request) {
        return this.baseMapper.getFamilyInfoByQryObj(realestateId,request.getDoorplate(),request.getBuildCode(),request.getUnitCode());
    }


    private FaultMangeFamilyPageVO convertToVO2(HomeAutoFaultDeviceHavcDO record) {
        FaultMangeFamilyPageVO vo = new FaultMangeFamilyPageVO();
        BeanUtils.copyProperties(record, vo);
        /**
         * 转换类型
         * 获取家庭信息
         */
        HomeAutoFamilyDO familyDO = getById(record.getFamilyId());
        if (familyDO != null) {
            vo.setBuildingCode(familyDO.getBuildingCode());
            vo.setUnitCode(familyDO.getUnitCode());
            vo.setFamilyCode(familyDO.getCode());
            vo.setDoorplate(familyDO.getDoorplate());
            vo.setTemplateId(familyDO.getTemplateId());

        }

        List<FamilyUserPageVO> familyUserPageVOS = familyUserService.getListFamilyMember(record.getFamilyId());

        if (!CollectionUtils.isEmpty(familyUserPageVOS)){
            vo.setName(familyUserPageVOS.get(0).getName());
        }

        Long templateId = this.getTemplateIdById(record.getFamilyId());
        if(templateId > 0 && StringUtils.isNotBlank(record.getDeviceSn())) {
            TemplateDeviceDO deviceDO =  iHouseTemplateDeviceService.getDeviceByTemplateAndCode(templateId,record.getDeviceSn());
            if (deviceDO != null){
                vo.setDeviceName(deviceDO.getName());
                vo.setCategoryCode(deviceDO.getCategoryCode());

                if (deviceDO.getRoomId() > 0) {
                    TemplateRoomDO room = roomService.getById(deviceDO.getRoomId());
                    vo.setRoomId(deviceDO.getRoomId());
                    vo.setDeviceId(deviceDO.getId());

                    if (room !=null){
                        vo.setRoomName(room.getName());
                    }

                }
            }




        }

        return vo;
    }


}
