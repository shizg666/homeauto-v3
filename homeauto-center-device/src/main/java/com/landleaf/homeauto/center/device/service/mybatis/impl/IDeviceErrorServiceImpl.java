package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceLinkDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceValueDO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceErrorService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductPageVO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public BasePageVO<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request) {

        List<DeviceErrorVO> result = null;

        if (!StringUtil.isEmpty(request.getPath())){
            patsePathInfo(request);
        }
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request.getType())){
            result = iHomeAutoFaultDeviceHavcService.getListDeviceError(request);
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request.getType())){
            result = iHomeAutoFaultDeviceLinkService.getListDeviceError(request);
        }else {
            result = iHomeAutoFaultDeviceValueService.getListDeviceError(request);
        }
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<DeviceErrorVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public void updateBatchStatus(DeviceErrorUpdateDTO request) {
        if (request.getType() == null || CollectionUtils.isEmpty(request.getIds())){
            return;
        }
        List<DeviceErrorVO> result = null;
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request)){
            iHomeAutoFaultDeviceHavcService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceHavcDO>().in(HomeAutoFaultDeviceHavcDO::getId,request.getIds()).set(HomeAutoFaultDeviceHavcDO::getFaultStatus,request.getFaultstatus()));
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request)){
            iHomeAutoFaultDeviceLinkService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceLinkDO>().in(HomeAutoFaultDeviceLinkDO::getId,request.getIds()).set(HomeAutoFaultDeviceLinkDO::getFaultStatus,request.getFaultstatus()));
        }else {
            iHomeAutoFaultDeviceValueService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceValueDO>().in(HomeAutoFaultDeviceValueDO::getId,request.getIds()).set(HomeAutoFaultDeviceValueDO::getFaultStatus,request.getFaultstatus()));
        }
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
