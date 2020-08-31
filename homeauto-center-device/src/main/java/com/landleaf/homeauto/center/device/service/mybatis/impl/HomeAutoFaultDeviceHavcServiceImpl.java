package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultDeviceHavcMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@Service
public class HomeAutoFaultDeviceHavcServiceImpl extends ServiceImpl<HomeAutoFaultDeviceHavcMapper, HomeAutoFaultDeviceHavcDO> implements IHomeAutoFaultDeviceHavcService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSave(List<HomeAutoFaultDeviceHavcDTO> data) {

        List<HomeAutoFaultDeviceHavcDO> saveDatas = data.stream().map(i -> {
            HomeAutoFaultDeviceHavcDO saveData = new HomeAutoFaultDeviceHavcDO();
            BeanUtils.copyProperties(i, saveData);
            return saveData;
        }).collect(Collectors.toList());
        saveBatch(saveDatas);
    }
}
