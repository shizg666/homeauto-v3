package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateFloorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateFloorService;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 户型楼层表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateFloorServiceImpl extends ServiceImpl<TemplateFloorMapper, TemplateFloorDO> implements IHouseTemplateFloorService {

    @Override
    public void add(TemplateFloorDTO request) {

    }

    @Override
    public void update(TemplateFloorDTO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
