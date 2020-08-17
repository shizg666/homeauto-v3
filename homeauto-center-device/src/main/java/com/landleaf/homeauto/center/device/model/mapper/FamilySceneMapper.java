package com.landleaf.homeauto.center.device.model.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.model.bo.FamilySceneBO;
import com.landleaf.homeauto.model.po.device.FamilyScenePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 家庭情景表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilySceneMapper extends BaseMapper<FamilyScenePO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 家庭常用场景
     */
    List<FamilySceneBO> getCommonScenesByFamilyId(@RequestParam String familyId);

    /**
     * 通过家庭ID获取不常用场景
     *
     * @param familyId 家庭ID
     * @return 家庭不常用场景
     */
    List<FamilySceneBO> getUncommonScenesByFamilyId(@RequestParam String familyId);

}
