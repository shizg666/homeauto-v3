package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceLinkDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultDeviceLinkMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
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
public class HomeAutoFaultDeviceLinkServiceImpl extends ServiceImpl<HomeAutoFaultDeviceLinkMapper, HomeAutoFaultDeviceLinkDO> implements IHomeAutoFaultDeviceLinkService {

    @Override
    public void batchSave(List<HomeAutoFaultDeviceLinkDTO> data) {


        List<HomeAutoFaultDeviceLinkDO> saveDatas = data.stream().map(i -> {
            HomeAutoFaultDeviceLinkDO saveData = new HomeAutoFaultDeviceLinkDO();
            BeanUtils.copyProperties(i, saveData);
            return saveData;
        }).collect(Collectors.toList());
        saveBatch(saveDatas);

    }
}
