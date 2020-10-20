package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingVO;

import java.util.List;

/**
 * <p>
 * 场景定时配置表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneTimingService extends IService<FamilySceneTimingDO> {

    /**
     * 根据家庭ID获取家庭场景
     *
     * @param familyId 家庭ID
     * @return 定时场景列表
     */
    List<FamilySceneTimingBO> getTimingScenesByFamilyId(String familyId);

    /**
     * 批量删除家庭下场景定时配置
     *
     * @param timingIds 定时场景配置id集合
     * @param familyId  家庭id
     */
    void deleteTimingScene(List<String> timingIds, String familyId);

    /**
     * 更新场景的启用和禁用情况
     *
     * @param sceneTimingId 定时场景ID
     * @param enabled       0|1 禁用|启用
     */
    void updateEnabled(String sceneTimingId, boolean enabled);


}
