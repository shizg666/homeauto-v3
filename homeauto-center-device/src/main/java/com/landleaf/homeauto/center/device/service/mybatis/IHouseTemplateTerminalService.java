package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

/**
 * <p>
 * 户型终端表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateTerminalService extends IService<TemplateTerminalDO> {

    void add(HouseTemplateTerminalVO request);

    void update(HouseTemplateTerminalVO request);

    void delete(ProjectConfigDeleteDTO request);
}
