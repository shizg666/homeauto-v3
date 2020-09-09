package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectStatusDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IHomeAutoProjectService extends IService<HomeAutoProject> {

    /**
     * 根据楼盘id集合获取楼盘下的项目数量
     * @param ids
     * @return
     */
    Map<String,Integer> countByRealestateIds(List<String> ids);

    void add(ProjectDTO request);

    void update(ProjectDTO request);

    void deleteById(String id);

    /**
     * 分页查询
     * @param request
     * @return
     */
    BasePageVO<ProjectVO> page(ProjectQryDTO request);

    /**
     * 项目类型列表
     * @return
     */
    List<SelectedIntegerVO> types();

    /**
     * 项目状态切换
     * @param projectStatusDTO
     */
    void statusSwitch(ProjectStatusDTO projectStatusDTO);

    /**
     * 获取项目列表包括层级关系
     * @param fliter 为true 则根据用户权限过滤
     * @return
     */
    List<CascadeVo> getListPathProjects(boolean fliter);

    /**
     *
     * @return
     */
    List<SelectedVO> getListSeclects();

    /**
     * 楼盘项目下拉列表（根据用户权限配置）
     * @return
     */
    List<CascadeVo> getListCascadeSeclects();


}
