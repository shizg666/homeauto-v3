package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.model.bo.FamilyCommonDeviceBO;
import com.landleaf.homeauto.model.po.device.FamilyCommonDevicePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonDeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonDeviceServiceImpl extends ServiceImpl<FamilyCommonDeviceMapper, FamilyCommonDevicePO> implements IFamilyCommonDeviceService {

    private FamilyCommonDeviceMapper familyCommonDeviceMapper;

    @Override
    public List<FamilyCommonDeviceVO> getCommonDevicesByFamilyId(String familyId) {
        List<FamilyCommonDeviceBO> commonDeviceBOList = familyCommonDeviceMapper.getCommonDevicesByFamilyId(familyId);
        List<FamilyCommonDeviceVO> familyCommonDeviceVOList = new LinkedList<>();
        for (FamilyCommonDeviceBO commonDeviceBO : commonDeviceBOList) {
            FamilyCommonDeviceVO familyCommonDeviceVO = new FamilyCommonDeviceVO();
            familyCommonDeviceVO.setDeviceId(commonDeviceBO.getDeviceId());
            familyCommonDeviceVO.setDeviceName(commonDeviceBO.getDeviceName());
            familyCommonDeviceVO.setPosition(String.format("%s-%s", commonDeviceBO.getFloorName(), commonDeviceBO.getRoomName()));
            familyCommonDeviceVO.setDeviceIcon(commonDeviceBO.getDeviceIcon());
            familyCommonDeviceVO.setIndex(commonDeviceBO.getIndex());
            familyCommonDeviceVOList.add(familyCommonDeviceVO);
        }
        for (FamilyCommonDeviceVO commonDeviceVO : familyCommonDeviceVOList) {
            // TODO: 设备的开关状态

        }
        return familyCommonDeviceVOList;
    }

    @Autowired
    public void setFamilyCommonDeviceMapper(FamilyCommonDeviceMapper familyCommonDeviceMapper) {
        this.familyCommonDeviceMapper = familyCommonDeviceMapper;
    }
}
