package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.constant.CategoryConstant;
import com.landleaf.homeauto.center.device.enums.HouseTemplateFloorTypeEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.excel.importfamily.HouseTemplateConfig;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProduct;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目户型表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectHouseTemplateServiceImpl extends ServiceImpl<ProjectHouseTemplateMapper, ProjectHouseTemplate> implements IProjectHouseTemplateService {


    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private ISysProductService iSysProductService;
    @Autowired
    private IdService idService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ProjectHouseTemplateDTO request) {
        addCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        save(template);
        request.setId(template.getId());
        //默认创建全类型的房间并在下面创建一个系统设备
        defaultCreateSysProduct(request);
    }

    /**
     * 创建默认的系统设备相关
     * @param request
     */
    private void defaultCreateSysProduct(ProjectHouseTemplateDTO request) {
        SysProduct sysProduct = iSysProductService.getSysProductByProjectId(request.getProjectId());
        if (Objects.isNull(sysProduct)){
            return;
        }
        //创建全屋房间
        TemplateRoomDTO templateRoomDTO = TemplateRoomDTO.builder().floor("1").houseTemplateId(request.getId()).name(RoomTypeEnum.WHOLE.getName()).type(RoomTypeEnum.WHOLE.getType()).projectId(request.getProjectId()).area(request.getArea()).build();
        templateRoomDTO.setId(idService.getSegmentId());
        iHouseTemplateRoomService.add(templateRoomDTO);
        //创建系统设备

        TemplateDeviceDO deviceAddDTO = TemplateDeviceDO.builder().name(sysProduct.getName()).productId(sysProduct.getId()).productCode(sysProduct.getCode()).categoryCode(CategoryConstant.SYS_PRODCUT_CODE).roomId(templateRoomDTO.getId()).houseTemplateId(request.getId()).systemFlag(FamilySystemFlagEnum.SYS_DEVICE.getType()).sn(String.valueOf(idService.getSegmentId(CommonConst.HOMEAUTO_DEVICE_SN))).build();
        iHouseTemplateDeviceService.save(deviceAddDTO);
    }

    private void addCheck(ProjectHouseTemplateDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectHouseTemplate>().eq(ProjectHouseTemplate::getName,request.getName()).eq(ProjectHouseTemplate::getProjectId,request.getProjectId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型已存在");
        }
    }

    @Override
    public void update(ProjectHouseTemplateDTO request) {
        updateCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        updateById(template);
    }

    private void updateCheck(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());
        if (template.getName().equals(request.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iHomeAutoFamilyService.count(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getTemplateId,request.getId()).last("limit 1"));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该户型有家庭关联不删除！");
        }
        int countRoom = iHouseTemplateRoomService.count(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()).last("limit 1"));
        if (countRoom > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该户型有房间存在不可删除！");
        }
        removeById(request.getId());
        //删除户型配置
//        iHouseTemplateRoomService.remove(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()));
//        iHouseTemplateDeviceService.remove(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,request.getId()));
//        iHouseTemplateSceneService.deleteByTempalteId(request.getId());
    }

    @Override
    public List<HouseTemplatePageVO> getListByProjectId(Long id) {
        List<HouseTemplatePageVO> data = this.baseMapper.getListByProjectId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CountLongBO> countLongBOS = this.baseMapper.getRoomNumByTemplateId(id);
        if (CollectionUtils.isEmpty(countLongBOS)){
            return data;
        }
        Map<Long,Integer> mapData = countLongBOS.stream().collect(Collectors.toMap(CountLongBO::getId,CountLongBO::getCount));
        data.forEach(obj->{
            if (mapData.containsKey(obj.getId())){
                obj.setRoomNum(mapData.get(obj.getId()));
            }else {
                obj.setRoomNum(0);
            }
        });
        return data;
    }

    @Override
    public HouseTemplateDetailVO getDeatil(Long templateId) {
        List<TemplateRoomPageVO> rooms = iHouseTemplateRoomService.getListRoomByTemplateId(templateId);
//        List<TemplateDevicePageVO> devices = iHouseTemplateDeviceService.getListByTemplateId(templateId);
        List<HouseScenePageVO> scenes = iHouseTemplateSceneService.getListScene(templateId);
        HouseTemplateDetailVO detailVO = new HouseTemplateDetailVO();
        detailVO.setRooms(rooms);
        detailVO.setScenes(scenes);
        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(ProjectHouseTemplateDTO request) {
//        ProjectHouseTemplate template = getById(request.getId());
//
//        if (template == null){
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型id不存在");
//        }
//        //生成新的户型
//        template.setId(idService.getSegmentId());
//        addCheck(request);
//        template.setName(request.getName());
//        template.setArea(request.getArea());
//
//        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()).select(TemplateRoomDO::getName,TemplateRoomDO::getFloorId,TemplateRoomDO::getHouseTemplateId,TemplateRoomDO::getType,TemplateRoomDO::getSortNo,TemplateRoomDO::getIcon,TemplateRoomDO::getId));
//        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,request.getId()));
//
////        Map<String, String> roomMap = copyRoom(roomDOS,floorMap,template.getId());
////        copyDevice(deviceDOS,roomMap,template.getId());
//
//        //场景主信息
//        List<HouseTemplateScene> templateScenes = iHouseTemplateSceneService.list(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId, request.getId()));
//        if (CollectionUtils.isEmpty(templateScenes)){
//            return;
//        }
//        copyScene(templateScenes, template.getId());
//        save(template);
    }

    /**
     * 场景主信息复制
     * @param templateScenes
     * @param houseTemplateId
     * @return
     */
    private Map<String, String> copyScene(List<HouseTemplateScene> templateScenes, Long houseTemplateId) {

        Map<String, String> sceneMap = Maps.newHashMapWithExpectedSize(templateScenes.size());
//        templateScenes.forEach(templateScene->{
//            String sceneId = templateScene.getId();
//            templateScene.setId(IdGeneratorUtil.getUUID32());
//            sceneMap.put(sceneId,templateScene.getId());
////            templateScene.setHouseTemplateId(houseTemplateId);
//        });
        iHouseTemplateSceneService.saveBatch(templateScenes);
        return sceneMap;
    }


    @Override
    public List<TemplateSelectedVO> getListSelectByProjectId(Long projectId) {
        return this.baseMapper.getListSelectByProjectId(projectId);
    }

    @Override
    public Map<Long, Integer> countByProjectIds(List<Long> projectIds) {
        if (CollectionUtils.isEmpty(projectIds)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        List<CountLongBO> countList = this.baseMapper.countByProjectIds(projectIds);
        if (CollectionUtils.isEmpty(countList)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<Long, Integer> count = countList.stream().collect(Collectors.toMap(CountLongBO::getId, CountLongBO::getCount));
        return count;
    }

    @Override
    public List<String> getListHoustTemplateNames(String projectId) {
        return this.baseMapper.getListHoustTemplateNames(projectId);
    }

    @Override
    public HouseTemplateConfig getImportTempalteConfig(String templateId) {

//        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId, templateId).select(TemplateRoomDO::getName, TemplateRoomDO::getFloorId, TemplateRoomDO::getHouseTemplateId, TemplateRoomDO::getType, TemplateRoomDO::getSortNo, TemplateRoomDO::getIcon, TemplateRoomDO::getId));
//        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId, templateId));
//
//        //场景主信息
//        List<HouseTemplateScene> templateScenes = iHouseTemplateSceneService.list(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId, templateId));
//        HouseTemplateConfig config = new HouseTemplateConfig();
//        config.setDeviceDOS(deviceDOS);
//        config.setRoomDOS(roomDOS);
//
//        config.setTemplateScenes(templateScenes);
//        return config;
        return null;
    }

    @Override
    public String getTemplateArea(String templateId) {
        return this.baseMapper.getTemplateArea(templateId);
    }

    @Override
    public List<SelectedIntegerVO> getTemplateTypeSelects() {
        List<SelectedIntegerVO> data = Lists.newArrayListWithCapacity(HouseTemplateFloorTypeEnum.values().length);
        for (HouseTemplateFloorTypeEnum value : HouseTemplateFloorTypeEnum.values()) {
            data.add(new SelectedIntegerVO(value.getName(),value.getType()));
        }
        return data;
    }

    @Override
    public Boolean isGateWayProject(Long houseTemplateId) {
        int flag = this.baseMapper.isGateWayProject(houseTemplateId);
        if (flag == 0){
            return false;
        }
        return true;
    }




    private void copyDevice(List<TemplateDeviceDO> deviceDOS, Map<String, String> roomMap,String houseTemplateId) {
//        Map<String, String> deviceMap = Maps.newHashMapWithExpectedSize(deviceDOS.size());
        if (CollectionUtils.isEmpty(deviceDOS)){
            return ;
        }
        List<TemplateDeviceDO> data = Lists.newArrayListWithCapacity(deviceDOS.size());
//        deviceDOS.forEach(device->{
//            String deviceId = IdGeneratorUtil.getUUID32();
//            device.setId(deviceId);
//            device.setHouseTemplateId(houseTemplateId);
//            device.setRoomId(roomMap.get(device.getRoomId()));
//            data.add(device);
//        });
        iHouseTemplateDeviceService.saveBatch(data);
    }

//    private Map<String, String> copyRoom(List<TemplateRoomDO> roomDOS, Map<String, String> floorMap, String houseTemplateId) {
//        if (CollectionUtils.isEmpty(roomDOS)){
//            return Maps.newHashMapWithExpectedSize(0);
//        }
//        Map<String, String> roomMap = Maps.newHashMapWithExpectedSize(roomDOS.size());
//        List<TemplateRoomDO> data = Lists.newArrayListWithCapacity(roomDOS.size());
//        roomDOS.forEach(room->{
//            String roomId = IdGeneratorUtil.getUUID32();
//            roomMap.put(room.getId(),roomId);
//            room.setId(roomId);
//            room.setHouseTemplateId(houseTemplateId);
//            room.setFloorId(floorMap.get(room.getFloorId()));
//            data.add(room);
//        });
//        iHouseTemplateRoomService.saveBatch(data);
//        return roomMap;
//    }


}
