package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuilding;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.common.domain.vo.realestate.*;

import java.util.List;

/**
 * <p>
 * 楼栋表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IProjectBuildingService extends IService<ProjectBuilding> {

    void addConfig(ProjectBuildingDTO request);

    void updateConfig(ProjectBuildingDTO request);

    /**
     * 根据工程id获取楼栋列表
     * @param id
     * @return
     */
    List<ProjectBuildingVO> getListByProjectId(String id);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 获取楼栋编号
     * @param buildingId
     * @return
     */
    String getBuildingNoById(String buildingId);

    /**
     * 获取楼栋path信息
     * @param buildingId
     * @return
     */
    PathBO getBuildingPathInfoById(String buildingId);
}
