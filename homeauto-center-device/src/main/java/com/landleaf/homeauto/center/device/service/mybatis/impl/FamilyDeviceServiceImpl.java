package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.model.po.device.FamilyDevicePO;
import com.landleaf.homeauto.model.vo.device.FamilyDeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 家庭设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDevicePO> implements IFamilyDeviceService {

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
    public List<FamilyDeviceVO> getUncommonDevicesByFamilyId(String familyId) {
        return null;
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId) {
        // TODO:这里要把设备SN码一块查出来
        return familyDeviceMapper.getDeviceInfoByDeviceSn(sceneId);
    }

    @Autowired
    public void setFamilyDeviceMapper(FamilyDeviceMapper familyDeviceMapper) {
        this.familyDeviceMapper = familyDeviceMapper;
    }
}
