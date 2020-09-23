package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.excel.importfamily.DeviceErrorExportVO;
import com.landleaf.homeauto.center.device.excel.importfamily.ImporFamilyResultVO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceLinkDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceValueDO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductPageVO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName IDeviceErrorServiceImpl
 * @Description: 设备故障
 * @Author shizg
 * @Date 2020/9/21
 * @Version V1.0
 **/
@Service
@Slf4j
public class IDeviceErrorServiceImpl implements IDeviceErrorService {

    @Autowired
    private IHomeAutoFaultDeviceHavcService iHomeAutoFaultDeviceHavcService;

    @Autowired
    private IHomeAutoFaultDeviceValueService iHomeAutoFaultDeviceValueService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;


    @Autowired
    private IHomeAutoFaultDeviceLinkService iHomeAutoFaultDeviceLinkService;

    @Override
    public BasePageVO<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request) {
        List<DeviceErrorVO> result = getListErrorData(request);
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<DeviceErrorVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public void updateBatchStatus(DeviceErrorUpdateDTO request) {
        if (request.getType() == null || CollectionUtils.isEmpty(request.getIds())){
            return;
        }
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request.getType())){
            iHomeAutoFaultDeviceHavcService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceHavcDO>().in(HomeAutoFaultDeviceHavcDO::getId,request.getIds()).set(HomeAutoFaultDeviceHavcDO::getFaultStatus,request.getFaultstatus()));
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request.getType())){
            iHomeAutoFaultDeviceLinkService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceLinkDO>().in(HomeAutoFaultDeviceLinkDO::getId,request.getIds()).set(HomeAutoFaultDeviceLinkDO::getFaultStatus,request.getFaultstatus()));
        }else if (AttributeErrorTypeEnum.VAKUE.getType().equals(request.getType())){
            iHomeAutoFaultDeviceValueService.update(new LambdaUpdateWrapper<HomeAutoFaultDeviceValueDO>().in(HomeAutoFaultDeviceValueDO::getId,request.getIds()).set(HomeAutoFaultDeviceValueDO::getFaultStatus,request.getFaultstatus()));
        }
    }

    @Override
    public void exportListDeviceError(DeviceErrorQryDTO request, HttpServletResponse response) {
        String fileName = "设备故障";
        commonService.setResponseHeader(response,fileName);
        List<DeviceErrorVO> result = getListErrorData(request);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            Set<String> excludeColumnFiledNames = new HashSet<>();

            if (!AttributeErrorTypeEnum.VAKUE.getType().equals(request.getType())){
                excludeColumnFiledNames.add("reference");
                excludeColumnFiledNames.add("current");
            }

            List<DeviceErrorExportVO> familyResultVOS = BeanUtil.mapperList(result,DeviceErrorExportVO.class);
            EasyExcel.write(os, DeviceErrorExportVO.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("设备故障").doWrite(familyResultVOS);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("导出故障数据异常：{}",e.getMessage());
        }

    }

    private List<DeviceErrorVO> getListErrorData(DeviceErrorQryDTO request) {
        List<DeviceErrorVO> result = null;
        if (!StringUtil.isEmpty(request.getPath())){
            patsePathInfo(request);
        }
        if (StringUtil.isEmpty(request.getFamilyId())) {
            List<String> paths = commonService.getUserPathScope();
            List<String> familyIds = iHomeAutoFamilyService.getListIdByPaths(paths);
            request.setFamilyIds(familyIds);
        }
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request.getType())){
            result = iHomeAutoFaultDeviceHavcService.getListDeviceError(request);
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request.getType())){
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
