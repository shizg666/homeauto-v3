package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.model.bo.FamilyCommonSceneBO;
import com.landleaf.homeauto.model.po.device.FamilyCommonScenePO;
import com.landleaf.homeauto.model.po.device.FamilyScenePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonSceneVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 家庭常用场景表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyCommonSceneMapper extends BaseMapper<FamilyCommonScenePO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 家庭常用场景
     */
    List<FamilyCommonSceneBO> getCommonScenesByFamilyId(@RequestParam String familyId);

}
