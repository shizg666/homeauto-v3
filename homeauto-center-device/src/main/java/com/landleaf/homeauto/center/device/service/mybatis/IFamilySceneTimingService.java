package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneAppletsDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO;
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


    List<ScreenFamilySceneTimingBO> getTimingScenesByFamilyId(String familyId);

    /**
     * 根据家庭ID获取家庭场景
     *
     * @param familyId 家庭ID
     * @return 定时场景列表
     */
    List<FamilySceneTimingBO> listFamilySceneTiming(String familyId);

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
    void updateEnabled(String sceneTimingId, Integer enabled);
    /**
     * APP获取场景定时列表
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO>
     * @author wenyilu
     * @date  2020/12/28 10:41
     */
    List<FamilySceneTimingVO> getTimingSceneList(String familyId);

    /**
     * APP查看场景定时记录详情
     * @param timingId  场景定时记录ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO
     * @author wenyilu
     * @date  2020/12/28 10:48
     */
    SceneTimingDetailVO getTimingSceneDetail(String timingId);

    /**
     * APP添加场景定时记录
     * @param timingSceneDTO
     * @return boolean
     * @author wenyilu
     * @date  2020/12/28 10:56
     */
    boolean saveTimingScene(TimingSceneDTO timingSceneDTO);

}
