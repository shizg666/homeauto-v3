package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceValueDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultDeviceValueMapper;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class HomeAutoFaultDeviceValueServiceImpl extends ServiceImpl<HomeAutoFaultDeviceValueMapper, HomeAutoFaultDeviceValueDO> implements IHomeAutoFaultDeviceValueService {

    @Override
    public void batchSave(List<HomeAutoFaultDeviceValueDTO> data) {
        List<HomeAutoFaultDeviceValueDO> saveDatas = data.stream().map(i -> {
            HomeAutoFaultDeviceValueDO saveData = new HomeAutoFaultDeviceValueDO();
            BeanUtils.copyProperties(i, saveData);
            return saveData;
        }).collect(Collectors.toList());
        saveBatch(saveDatas);
    }

    @Override
    public List<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request) {
        return null;
    }
}
