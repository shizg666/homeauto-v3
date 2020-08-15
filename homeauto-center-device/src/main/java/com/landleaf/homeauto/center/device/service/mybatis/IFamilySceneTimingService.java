package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.FamilySceneTimingPO;
import com.landleaf.homeauto.model.vo.device.TimingSceneDetailVO;
import com.landleaf.homeauto.model.vo.device.TimingSceneVO;

import java.util.List;

/**
 * <p>
 * 场景定时配置表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneTimingService extends IService<FamilySceneTimingPO> {

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

}
