package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
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

    @Override
    public List<FamilyDeviceVO> getCommonDevicesByFamilyId(String familyId) {
        List<FamilyDeviceWithPositionBO> commonDeviceBOList = familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO commonDeviceBO : commonDeviceBOList) {
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
        List<FamilyDeviceWithPositionBO> familyDeviceWithPositionBOList = familyDeviceMapper.getUnCommonDevicesByFamilyId(familyId);
        Map<String, List<FamilyDeviceWithPositionBO>> map = new LinkedHashMap<>();
        for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : familyDeviceWithPositionBOList) {
            // 位置信息: 楼层-房间
            String position = familyDeviceWithPositionBO.getFloorName() + "-" + familyDeviceWithPositionBO.getRoomName();
            if (map.containsKey(position)) {
                map.get(position).add(familyDeviceWithPositionBO);
            } else {
                map.put(position, CollectionUtil.list(true, familyDeviceWithPositionBO));
            }
        }

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
