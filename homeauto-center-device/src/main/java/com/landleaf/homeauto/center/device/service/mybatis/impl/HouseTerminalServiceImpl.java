package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.mapper.TemplateTerminalMapper;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateTerminalOperateVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateTerminalService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.category.CheckEnum;
import com.landleaf.homeauto.common.enums.realestate.TerminalTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 户型终端表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTerminalServiceImpl extends ServiceImpl<TemplateTerminalMapper, TemplateTerminalDO> implements IHouseTemplateTerminalService {

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HouseTemplateTerminalVO request) {
        addCheck(request);
        int count = count(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId,request.getHouseTemplateId()));
        if (count == 0){
            request.setMasterFlag(1);
        }else {
            request.setMasterFlag(0);
        }
        TemplateTerminalDO templateTerminalDO = BeanUtil.mapperBean(request,TemplateTerminalDO.class);
        save(templateTerminalDO);
    }

    private void addCheck(HouseTemplateTerminalVO request) {
        int count = count(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getName,request.getName()).eq(TemplateTerminalDO::getHouseTemplateId,request.getHouseTemplateId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关名称已存在");
        }
    }

    @Override
    public void update(HouseTemplateTerminalVO request) {
        updateCheck(request);
        TemplateTerminalDO templateTerminalDO = BeanUtil.mapperBean(request,TemplateTerminalDO.class);
        updateById(templateTerminalDO);
    }

    private void updateCheck(HouseTemplateTerminalVO request) {
        TemplateTerminalDO terminalDO = getById(request.getId());
        if (request.getName().equals(terminalDO.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iHouseTemplateDeviceService.count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getTerminalId,request.getId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关有设备存在");
        }
        removeById(request.getId());

    }

    @Override
    public List<SelectedIntegerVO> getTerminalTypes() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (TerminalTypeEnum value : TerminalTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getTerminalSelects(String houseTemplateId) {
        return this.baseMapper.getTerminalSelects(houseTemplateId);
    }

    @Override
    public void switchMaster(TemplateTerminalOperateVO request) {
        String id = this.baseMapper.getMasterID(request.getHouseTemplateId());
        TemplateTerminalDO terminalDO = new TemplateTerminalDO();
        terminalDO.setId(id);
        terminalDO.setMasterFlag(0);
        TemplateTerminalDO terminalDO2 = new TemplateTerminalDO();
        terminalDO2.setId(request.getId());
        terminalDO2.setMasterFlag(1);
        List<TemplateTerminalDO> list = Lists.newArrayListWithCapacity(2);
        list.add(terminalDO);
        list.add(terminalDO2);
        updateBatchById(list);
    }

    @Override
    public List<String> getListByTempalteId(String templateId) {
        return this.baseMapper.getListByTempalteId(templateId);
    }
}
