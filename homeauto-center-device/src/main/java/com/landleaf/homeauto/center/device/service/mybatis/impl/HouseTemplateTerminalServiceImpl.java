package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.mapper.TemplateTerminalMapper;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateTerminalService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;

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
    public void add(HouseTemplateTerminalVO request) {
        addCheck(request);
        TemplateTerminalDO templateTerminalDO = BeanUtil.mapperBean(request,TemplateTerminalDO.class);

    }

    private void addCheck(HouseTemplateTerminalVO request) {
        int count = count(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getName,request.getName()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目名称已存在");
        }
    }

    @Override
    public void update(HouseTemplateTerminalVO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
