package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoAddress;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;

import java.util.List;

/**
 * <p>
 * 地址 服务类
 * </p>
 *
 * @author shizg
 */
public interface AddressService extends IService<HomeAutoAddress> {




    List<CascadeVo> cascadeList(List<String> paths);



}
