package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuildingUnit;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 楼栋单元表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IProjectBuildingUnitService extends IService<ProjectBuildingUnit> {

    void add(ProjectBuildingUnitDTO request);

    void update(ProjectBuildingUnitDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据楼栋id获取单元列表
     * @param id
     * @return
     */
    List<ProjectBuildingUnitVO> getListByProjectId(String id);
}
