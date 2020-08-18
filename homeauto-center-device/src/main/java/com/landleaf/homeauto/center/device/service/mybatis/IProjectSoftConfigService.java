package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectSoftConfigDTO;

import java.util.List;

/**
 * <p>
 * 项目软件配置表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IProjectSoftConfigService extends IService<ProjectSoftConfig> {

    /**
     * 添加配置
     * @param request
     */
    void add(ProjectSoftConfigDTO request);

    /**
     * 修改配置
     * @param request
     */
    void update(ProjectSoftConfigDTO request);

    /**
     * 根据项目id获取项目的配置信息
     * @param id
     * @return
     */
    List<ProjectSoftConfigDTO> getConfigByProjectId(String id);
}
