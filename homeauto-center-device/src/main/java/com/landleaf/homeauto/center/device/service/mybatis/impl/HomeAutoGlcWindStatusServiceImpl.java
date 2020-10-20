package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoGlcWindStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoGlcWindStatusMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoGlcWindStatusService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-20
 */
@Service
public class HomeAutoGlcWindStatusServiceImpl extends ServiceImpl<HomeAutoGlcWindStatusMapper, HomeAutoGlcWindStatusDO> implements IHomeAutoGlcWindStatusService {

    private static String default_rule = "命令索引:3,起始地址:0,长度:43;命令索引:4,起始地址:50,长度:62;命令索引:5,起始地址:200,长度:52;命令索引:6,起始地址:300,长度:37。";

    @Override
    public void saveRecord(String familyId, String deviceSn, String productCode, String code, String value) {
        HomeAutoGlcWindStatusDO saveData = new HomeAutoGlcWindStatusDO();
        saveData.setFamilyId(familyId);
        saveData.setDeviceSn(deviceSn);
        saveData.setProductCode(productCode);
        saveData.setValue(value);
        saveData.setDesc(default_rule);
        save(saveData);
    }
}
