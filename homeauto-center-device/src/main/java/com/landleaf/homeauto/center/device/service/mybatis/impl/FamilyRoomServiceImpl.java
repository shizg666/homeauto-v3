package com.landleaf.homeauto.center.device.service.mybatis.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilyRoomVO;
import com.landleaf.homeauto.center.device.model.vo.FamilySimpleDeviceVO;
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
    public List<FamilyRoomVO> getRoomListByFamilyId(String familyId) {
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
        List<FamilyRoomVO> familyRoomVOList = new LinkedList<>();
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
            FamilyRoomVO familyRoomVO = new FamilyRoomVO();
            familyRoomVO.setFloorId(keySplit[0]);
            familyRoomVO.setFloorName(keySplit[1]);
            familyRoomVO.setRoomList(familySimpleRoomBOList);
            familyRoomVOList.add(familyRoomVO);
        }
        return familyRoomVOList;
    }

    @Override
    public List<FamilySimpleDeviceVO> getDeviceListByRoomId(String roomId) {
        List<FamilyDeviceBO> familyRoomBOList = familyDeviceService.getDeviceListByRoomId(roomId);
        List<FamilySimpleDeviceVO> familySimpleDeviceVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyRoomBOList) {
            FamilySimpleDeviceVO familySimpleDeviceVO = new FamilySimpleDeviceVO();
            familySimpleDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familySimpleDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familySimpleDeviceVO.setDeviceIcon(familyDeviceBO.getDevicePicUrl());
            familySimpleDeviceVOList.add(familySimpleDeviceVO);
        }
        return familySimpleDeviceVOList;
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
