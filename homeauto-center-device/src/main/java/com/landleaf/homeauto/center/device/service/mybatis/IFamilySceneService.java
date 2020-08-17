package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.SceneDetailVO;

import java.util.List;

/**
 * <p>
 * 家庭场景表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneService extends IService<FamilySceneDO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景列表
     */
    List<FamilySceneVO> getCommonScenesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取不常用的场景(也就是所有场景中,常用场景的补集)
     *
     * @param familyId 家庭ID
     * @return 不常用的场景集合
     */
    List<FamilySceneVO> getUncommonScenesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取全屋场景
     *
     * @param familyId 家庭ID
     * @return 全屋场景集合
     */
    List<FamilySceneVO> getWholeHouseScenesByFamilyId(String familyId);

    /**
     * 通过场景ID获取场景详情
     *
     * @param sceneId 场景ID
     * @return 场景详情集合
     */
    List<SceneDetailVO> getSceneDetailBySceneId(String sceneId);

}
