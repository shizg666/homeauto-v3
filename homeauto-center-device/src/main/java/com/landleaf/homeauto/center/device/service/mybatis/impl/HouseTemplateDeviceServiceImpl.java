package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDeviceDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 户型设备表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateDeviceServiceImpl extends ServiceImpl<TemplateDeviceMapper, TemplateDeviceDO> implements IHouseTemplateDeviceService {

    @Override
    public void add(TemplateDeviceDTO request) {
        addCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request,TemplateDeviceDO.class);
        save(deviceDO);
    }

    private void addCheck(TemplateDeviceDTO request) {
       int count = this.baseMapper.existParam(request.getName(),null,request.getHouseTemplateId());
       if (count >0){
           throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
       }
        int countSn = this.baseMapper.existParam(null,request.getSn(),request.getHouseTemplateId());
        if (countSn >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
        }
    }

    @Override
    public void update(TemplateDeviceDTO request) {
        updateCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request,TemplateDeviceDO.class);
        updateById(deviceDO);
    }

    private void updateCheck(TemplateDeviceDTO request) {
        TemplateDeviceDO deviceDO = getById(request.getId());
        if (request.getName().equals(deviceDO.getName()) && request.getSn().equals(deviceDO.getSn())){
            return;
        }
        if (!request.getName().equals(deviceDO.getName())){
            int count = this.baseMapper.existParam(request.getName(),null,request.getHouseTemplateId());
            if (count >0){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
            }
        }
        if (!request.getSn().equals(deviceDO.getSn())){
            int count = this.baseMapper.existParam(null,request.getSn(),request.getHouseTemplateId());
            if (count >0){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
            }
        }
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo 删除逻辑
        removeById(request.getId());
    }

    @Override
    public List<CountBO> countDeviceByRoomIds(List<String> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CountBO> data = this.baseMapper.countDeviceByRoomIds(roomIds);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }
}
