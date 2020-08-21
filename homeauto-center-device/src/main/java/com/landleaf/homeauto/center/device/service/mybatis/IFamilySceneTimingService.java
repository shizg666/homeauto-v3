package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.dto.FamilySceneTimingDTO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneDetailVO;
import com.landleaf.homeauto.center.device.model.vo.TimingSceneVO;

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
     * 通过家庭ID获取定时场景
     *
     * @param familyId 家庭ID
     * @return 定时场景列表
     */
    List<TimingSceneVO> getTimingScenesByFamilyId(String familyId);

    /**
     * 通过定时场景ID获取定时场景详情
     *
     * @param timingId 定时场景ID
     * @return 定时场景详情
     */
    TimingSceneDetailVO getTimingSceneDetailByTimingId(String timingId);

    /**
     * 添加定时场景
     *
     * @param familySceneTimingDTO 定时场景数据对象
     * @return 主键
     */
    String insertFamilySceneTiming(FamilySceneTimingDTO familySceneTimingDTO);

    /**
     * 通过主键删除定时场景
     *
     * @param timingId 定时场景主键
     */
    void deleteFamilySceneById(String timingId);

    /**
     * 通过主键更新定时场景
     *
     * @param familySceneTimingDTO 定时场景主键
     */
    void updateFamilySceneById(FamilySceneTimingDTO familySceneTimingDTO);

}
