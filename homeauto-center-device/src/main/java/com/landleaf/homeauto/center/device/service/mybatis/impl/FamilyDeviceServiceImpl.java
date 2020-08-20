package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.AttributionVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 家庭设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDeviceDO> implements IFamilyDeviceService {

    private FamilyDeviceMapper familyDeviceMapper;

    private IFamilyDeviceStatusService familyDeviceStatusService;

    @Override
    public List<FamilyDeviceVO> getCommonDevicesByFamilyId(String familyId) {
        List<FamilyDeviceWithPositionBO> allDeviceBOList = familyDeviceMapper.getAllDevicesByFamilyId(familyId);
        List<FamilyDeviceWithPositionBO> commonDeviceBOList = familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
        allDeviceBOList.removeIf(commonDeviceBO -> !commonDeviceBOList.contains(commonDeviceBO));
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO commonDeviceBO : allDeviceBOList) {
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(commonDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(commonDeviceBO.getDeviceName());
            familyDeviceVO.setPosition(String.format("%s-%s", commonDeviceBO.getFloorName(), commonDeviceBO.getRoomName()));
            familyDeviceVO.setDeviceIcon(commonDeviceBO.getDeviceIcon());
            familyDeviceVO.setIndex(commonDeviceBO.getIndex());
            familyDeviceVOList.add(familyDeviceVO);
        }
        for (FamilyDeviceVO commonDeviceVO : familyDeviceVOList) {
            // TODO: 设备的开关状态

        }
        return familyDeviceVOList;
    }

    @Override
    public List<FamilyDevicesExcludeCommonVO> getUncommonDevicesByFamilyId(String familyId) {
        // 获取家庭所有的设备
        List<FamilyDeviceWithPositionBO> allDeviceList = familyDeviceMapper.getAllDevicesByFamilyId(familyId);
        Map<String, List<FamilyDeviceWithPositionBO>> map = new LinkedHashMap<>();
        // 遍历不常用设备
        for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : allDeviceList) {
            // 先将所有的设备按位置分类
            // 位置信息: 楼层-房间
            String position = familyDeviceWithPositionBO.getFloorName() + "-" + familyDeviceWithPositionBO.getRoomName();
            if (map.containsKey(position)) {
                map.get(position).add(familyDeviceWithPositionBO);
            } else {
                map.put(position, CollectionUtil.list(true, familyDeviceWithPositionBO));
            }
        }

        // 获取家庭常用设备
        List<FamilyDeviceWithPositionBO> commonDeviceList = familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
        for (FamilyDeviceWithPositionBO commonDevice : commonDeviceList) {
            // 从全部设备中移除所有常用设备
            String position = commonDevice.getFloorName() + "-" + commonDevice.getRoomName();
            map.get(position).remove(commonDevice);
        }

        // 现在这里的只有不常用的设备了,即使是房间内没有设备,也会显示空数组
        List<FamilyDevicesExcludeCommonVO> familyDevicesExcludeCommonVOList = new LinkedList<>();
        for (String key : map.keySet()) {
            List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
            List<FamilyDeviceWithPositionBO> familyDeviceBOList = map.get(key);
            for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : familyDeviceBOList) {
                FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
                familyDeviceVO.setDeviceId(familyDeviceWithPositionBO.getDeviceId());
                familyDeviceVO.setDeviceName(familyDeviceWithPositionBO.getDeviceName());
                familyDeviceVO.setDeviceIcon(familyDeviceWithPositionBO.getDeviceIcon());
                familyDeviceVO.setIndex(familyDeviceWithPositionBO.getIndex());
                familyDeviceVOList.add(familyDeviceVO);
            }
            FamilyDevicesExcludeCommonVO familyDevicesExcludeCommonVO = new FamilyDevicesExcludeCommonVO();
            familyDevicesExcludeCommonVO.setPositionName(key);
            familyDevicesExcludeCommonVO.setDevices(familyDeviceVOList);
            familyDevicesExcludeCommonVOList.add(familyDevicesExcludeCommonVO);
        }
        return familyDevicesExcludeCommonVOList;
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId) {
        return familyDeviceMapper.getDeviceInfoByDeviceSn(sceneId);
    }

    @Autowired
    public void setFamilyDeviceStatusService(IFamilyDeviceStatusService familyDeviceStatusService) {
        this.familyDeviceStatusService = familyDeviceStatusService;
    }

    @Override
    public boolean existByProductId(String id) {
        int count = this.baseMapper.existByProductId(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Autowired
    public void setFamilyDeviceMapper(FamilyDeviceMapper familyDeviceMapper) {
        this.familyDeviceMapper = familyDeviceMapper;
    }

}
