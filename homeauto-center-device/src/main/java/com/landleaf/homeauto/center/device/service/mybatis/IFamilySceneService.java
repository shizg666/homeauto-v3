package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;

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
    List<SceneVO> getCommonScenesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取不常用的场景(也就是所有场景中,常用场景的补集)
     *
     * @param familyId 家庭ID
     * @return 不常用的场景集合
     */
    List<SceneVO> getUncommonScenesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取全屋场景
     *
     * @param familyId 家庭ID
     * @return 全屋场景集合
     */
    List<SceneVO> getWholeHouseScenesByFamilyId(String familyId);

    /**
     * 通过场景ID获取场景详情
     *
     * @param sceneId 场景ID
     * @return 场景详情集合
     */
    List<SceneDetailVO> getSceneDetailBySceneId(String sceneId);

    /**
     * 通过家庭ID获取定时场景
     *
     * @param familyId 家庭ID
     * @return 定时场景列表
     */
    List<SceneTimingVO> getTimingScenesByFamilyId(String familyId);

    /**
     * 通过定时场景ID获取定时场景内容
     *
     * @param timingId 定时场景ID
     * @return 定时场景内容
     */
    SceneTimingDetailVO getTimingSceneDetailByTimingId(String timingId);

    /**
     * 通过场景ID获取家庭所有场景
     *
     * @param sceneId 场景ID
     * @return 家庭场景
     */
    List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId);

    /**
     * 添加常用场景
     *
     * @param familySceneCommonDTOList 常用场景信息
     */
    void insertFamilyCommonScene(FamilySceneCommonDTO familySceneCommonDTOList);

    /**
     * 检查场景是否存在
     *
     * @param sceneId 场景ID
     * @return 是否存在
     */
    boolean isSceneExists(String sceneId);

    /**
     * 批量获取场景
     *
     * @param sceneIdList
     * @return
     */
    List<FamilySceneDO> getSceneListBySceneIds(List<String> sceneIdList);
}
