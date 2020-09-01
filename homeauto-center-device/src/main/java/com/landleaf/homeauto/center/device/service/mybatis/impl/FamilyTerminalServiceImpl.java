package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyTerminalMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 家庭终端表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyTerminalServiceImpl extends ServiceImpl<FamilyTerminalMapper, FamilyTerminalDO> implements IFamilyTerminalService {

    @Override
    public FamilyTerminalDO getMasterTerminal(String familyId) {
        QueryWrapper<FamilyTerminalDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        queryWrapper.eq("master_flag", 1);
        return getOne(queryWrapper, true);
    }

    @Override
    public void add(FamilyTerminalVO request) {
        addCheck(request);
        FamilyTerminalDO familyTerminalDO = BeanUtil.mapperBean(request,FamilyTerminalDO.class);
        save(familyTerminalDO);
    }

    private void addCheck(FamilyTerminalVO request) {
        int count = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getName,request.getName()).eq(FamilyTerminalDO::getFamilyId,request.getFamilyId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关名称已存在");
        }
    }

    @Override
    public void update(FamilyTerminalVO request) {
        updateCheck(request);
        FamilyTerminalDO familyTerminalDO = BeanUtil.mapperBean(request,FamilyTerminalDO.class);
        updateById(familyTerminalDO);
    }

    private void updateCheck(FamilyTerminalVO request) {
        FamilyTerminalDO terminalDO = getById(request.getId());
        if (request.getName().equals(terminalDO.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
