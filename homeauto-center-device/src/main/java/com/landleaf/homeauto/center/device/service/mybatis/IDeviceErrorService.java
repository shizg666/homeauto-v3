package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
public interface IDeviceErrorService {



    /**
     * 设备故障信息获取
     * @param request
     * @return
     */
    BasePageVO<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request);

    /**
     * 批量更新状态
     * @param request
     */
    void updateBatchStatus(DeviceErrorUpdateDTO request);


    /**
     * 导出数据
     * @param request
     * @param response
     */
    void exportListDeviceError(DeviceErrorQryDTO request, HttpServletResponse response) throws IOException;
}
