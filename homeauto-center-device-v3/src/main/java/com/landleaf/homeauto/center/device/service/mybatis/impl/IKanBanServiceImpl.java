package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.model.vo.project.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.project.KanBanStatisticsQry;
import com.landleaf.homeauto.center.device.service.mybatis.IKanBanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Service
public class IKanBanServiceImpl implements IKanBanService {

    @Override
    public List<KanBanStatistics> getKanbanStatistics(KanBanStatisticsQry request) {
        CountDownLatch countDownLatch = new CountDownLatch(8);

        return null;
    }
}
