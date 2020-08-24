package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateFloorDetailVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型楼层表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateFloorService extends IService<TemplateFloorDO> {

    void add(TemplateFloorDTO request);

    void update(TemplateFloorDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据户型id获取楼房房间的相信信息
     * @param id
     * @return
     */
    List<TemplateFloorDetailVO> getListFloorDetail(String templateId);
}
