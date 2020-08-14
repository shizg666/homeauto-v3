package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonSceneMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.model.bo.FamilyCommonSceneBO;
import com.landleaf.homeauto.model.po.device.FamilyCommonScenePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonSceneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 家庭常用场景表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonSceneServiceImpl extends ServiceImpl<FamilyCommonSceneMapper, FamilyCommonScenePO> implements IFamilyCommonSceneService {

    private FamilyCommonSceneMapper familyCommonSceneMapper;

    @Override
    public List<FamilyCommonSceneVO> getCommonScenesByFamilyId(String familyId) {
        List<FamilyCommonSceneBO> commonSceneBOList = familyCommonSceneMapper.getCommonScenesByFamilyId(familyId);
        List<FamilyCommonSceneVO> commonSceneVOList = new LinkedList<>();
        for (FamilyCommonSceneBO commonSceneBO : commonSceneBOList) {
            FamilyCommonSceneVO commonSceneVO = new FamilyCommonSceneVO();
            commonSceneVO.setSceneId(commonSceneBO.getSceneId());
            commonSceneVO.setSceneName(commonSceneBO.getSceneName());
            commonSceneVO.setSceneIcon(commonSceneBO.getSceneIcon());
            commonSceneVO.setIndex(commonSceneBO.getIndex());
            commonSceneVOList.add(commonSceneVO);
        }
        return commonSceneVOList;
    }

    @Autowired
    public void setFamilyCommonSceneMapper(FamilyCommonSceneMapper familyCommonSceneMapper) {
        this.familyCommonSceneMapper = familyCommonSceneMapper;
    }
}
