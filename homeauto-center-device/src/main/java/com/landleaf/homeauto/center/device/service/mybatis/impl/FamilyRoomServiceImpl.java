package com.landleaf.homeauto.center.device.service.mybatis.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭房间表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyRoomServiceImpl extends ServiceImpl<FamilyRoomMapper, FamilyRoomDO> implements IFamilyRoomService {

    private FamilyRoomMapper familyRoomMapper;

    private IFamilyDeviceService familyDeviceService;

    @Override
    public List<RoomVO> getRoomListByFamilyId(String familyId) {
        List<FamilyRoomBO> familyRoomBOList = familyRoomMapper.getRoomListByFamilyId(familyId);

        // 按楼层将房间分类
        Map<String, List<FamilyRoomBO>> map = new LinkedHashMap<>();
        for (FamilyRoomBO familyRoomBO : familyRoomBOList) {
            String key = familyRoomBO.getFloorId() + "-" + familyRoomBO.getFloorName();
            if (map.containsKey(key)) {
                map.get(key).add(familyRoomBO);
            } else {
                map.put(key, CollectionUtil.list(true, familyRoomBO));
            }
        }

        // 组装
        List<RoomVO> roomVOList = new LinkedList<>();
        for (String key : map.keySet()) {
            List<FamilyRoomBO> familyRoomList = map.get(key);
            List<FamilySimpleRoomBO> familySimpleRoomBOList = new LinkedList<>();
            for (FamilyRoomBO familyRoomBO : familyRoomList) {
                FamilySimpleRoomBO familySimpleRoomBO = new FamilySimpleRoomBO();
                familySimpleRoomBO.setRoomId(familyRoomBO.getRoomId());
                familySimpleRoomBO.setRoomName(familyRoomBO.getRoomName());
                familySimpleRoomBO.setRoomPicUrl(familyRoomBO.getRoomPicUrl());
                familySimpleRoomBOList.add(familySimpleRoomBO);
            }
            String[] keySplit = key.split("-");
            RoomVO roomVO = new RoomVO();
            roomVO.setFloorId(keySplit[0]);
            roomVO.setFloorName(keySplit[1]);
            roomVO.setRoomList(familySimpleRoomBOList);
            roomVOList.add(roomVO);
        }
        return roomVOList;
    }

    @Override
    public List<DeviceSimpleVO> getDeviceListByRoomId(String roomId) {
        List<FamilyDeviceBO> familyRoomBOList = familyDeviceService.getDeviceListByRoomId(roomId);
        List<DeviceSimpleVO> deviceSimpleVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyRoomBOList) {
            DeviceSimpleVO deviceSimpleVO = new DeviceSimpleVO();
            deviceSimpleVO.setDeviceId(familyDeviceBO.getDeviceId());
            deviceSimpleVO.setDeviceName(familyDeviceBO.getDeviceName());
            deviceSimpleVO.setDeviceIcon(familyDeviceBO.getDevicePicUrl());
            deviceSimpleVOList.add(deviceSimpleVO);
        }
        return deviceSimpleVOList;
    }

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {

        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        return null;
    }

    @Autowired
    public void setFamilyRoomMapper(FamilyRoomMapper familyRoomMapper) {
        this.familyRoomMapper = familyRoomMapper;
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }
}
