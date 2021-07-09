package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;

import java.util.List;

/**
 * <p>
 * 报警信息 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-15
 */
public interface IHomeAutoAlarmMessageService extends IService<HomeAutoAlarmMessageDO> {


    List<AlarmMessageRecordVO> getAlarmlistByDeviceId(Long deviceId, Long familyId);

    /**
     * 获取家庭报警信息
     * @param familyId
     * @return
     */
    List<HomeAutoAlarmMessageDO> getAlarmlistByFamilyId(Long familyId);
}
