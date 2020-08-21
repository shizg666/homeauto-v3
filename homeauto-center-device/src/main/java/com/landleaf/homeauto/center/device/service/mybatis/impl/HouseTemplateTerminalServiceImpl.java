package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.mapper.TemplateTerminalMapper;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateTerminalService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.category.CheckEnum;
import com.landleaf.homeauto.common.enums.realestate.TerminalTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
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
public class HouseTemplateTerminalServiceImpl extends ServiceImpl<TemplateTerminalMapper, TemplateTerminalDO> implements IHouseTemplateTerminalService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HouseTemplateTerminalVO request) {
        addCheck(request);
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
        //todo 判断下面是否挂设备
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
}
