package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneTimingMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneTimingService;
import com.landleaf.homeauto.model.po.device.FamilySceneTimingPO;
import com.landleaf.homeauto.model.vo.device.TimingSceneDetailVO;
import com.landleaf.homeauto.model.vo.device.TimingSceneVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 场景定时配置表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneTimingServiceImpl extends ServiceImpl<FamilySceneTimingMapper, FamilySceneTimingPO> implements IFamilySceneTimingService {

    @Override
    public List<TimingSceneVO> getTimingScenesByFamilyId(String familyId) {
        return null;
    }

    @Override
    public TimingSceneDetailVO getTimingSceneDetailByTimingId(String timingId) {
        return null;
    }

}
