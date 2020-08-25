package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateCopyDTO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateDetailVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.center.device.model.vo.project.ProjectHouseTemplateDTO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;

import java.util.List;

/**
 * <p>
 * 项目户型表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IProjectHouseTemplateService extends IService<ProjectHouseTemplate> {

    void add(ProjectHouseTemplateDTO request);

    void update(ProjectHouseTemplateDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据项目id获取户型列表
     * @param id
     * @return
     */
    List<HouseTemplatePageVO> getListByProjectId(String id);


    /**
     * 查看户型详情信息
     * @param id
     * @return
     */
    HouseTemplateDetailVO getDeatil(String id);

    /**
     * 复制户型
     * @param request
     */
    void copy(ProjectHouseTemplateDTO request);
}
