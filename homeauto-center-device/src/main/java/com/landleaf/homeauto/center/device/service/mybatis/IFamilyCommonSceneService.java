package com.landleaf.homeauto.center.device.service.mybatis;


import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.FamilyCommonScenePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonSceneVO;

import java.util.List;

/**
 * <p>
 * 家庭常用场景表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonSceneService extends IService<FamilyCommonScenePO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景列表
     */
    List<FamilyCommonSceneVO> getCommonScenesByFamilyId(String familyId);

}
