package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceErrorService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName IDeviceErrorServiceImpl
 * @Description: 设备故障
 * @Author shizg
 * @Date 2020/9/21
 * @Version V1.0
 **/
@Service
public class IDeviceErrorServiceImpl implements IDeviceErrorService {

    @Autowired
    private IHomeAutoFaultDeviceHavcService iHomeAutoFaultDeviceHavcService;

    @Autowired
    private IHomeAutoFaultDeviceValueService iHomeAutoFaultDeviceValueService;


    @Autowired
    private IHomeAutoFaultDeviceLinkService iHomeAutoFaultDeviceLinkService;

    @Override
    public List<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request) {
        if (request.getType() == null){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<DeviceErrorVO> result = null;
        if (!StringUtil.isEmpty(request.getPath())){
            patsePathInfo(request);
        }
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request)){
            result = iHomeAutoFaultDeviceHavcService.getListDeviceError(request);
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request)){
            result = iHomeAutoFaultDeviceLinkService.getListDeviceError(request);
        }else {
            result = iHomeAutoFaultDeviceValueService.getListDeviceError(request);
        }
        return result;
    }

    /**
     *解析path
     * @param request
     */
    private void patsePathInfo(DeviceErrorQryDTO request) {
        String[] paths = request.getPath().split("/");
        if (paths == null){
            return;
        }
        request.setRealestateId(paths[0]);
        if (paths.length > 1){
            request.setProjectId(paths[1]);
        }
    }
}
