package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.domain.DeviceRepairGuide;
import com.landleaf.homeauto.center.device.model.mapper.DeviceRepairGuideMapper;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceRepairGuideDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceRepairGuideService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备维修指南 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-23
 */
@Service
public class DeviceRepairGuideServiceImpl extends ServiceImpl<DeviceRepairGuideMapper, DeviceRepairGuide> implements IDeviceRepairGuideService {

    @Override
    public BasePageVO<DeviceGuideVO> page(DeviceGuideQryDTO request) {
        List<DeviceGuideVO> guideVOS = this.baseMapper.page(request);
        PageInfo pageInfo = new PageInfo(guideVOS);
        BasePageVO<DeviceGuideVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public void addOrUpdate(DeviceRepairGuideDTO request) {
        DeviceRepairGuide guide = BeanUtil.mapperBean(request,DeviceRepairGuide.class);
        saveOrUpdate(guide);
    }

    @Override
    public void add(DeviceRepairGuideDTO request) {
        DeviceRepairGuide guide = BeanUtil.mapperBean(request,DeviceRepairGuide.class);
        save(guide);
    }

    @Override
    public void update(DeviceRepairGuideDTO request) {
        DeviceRepairGuide guide = BeanUtil.mapperBean(request,DeviceRepairGuide.class);
        updateById(guide);
    }
}
