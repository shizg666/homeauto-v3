package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;

import java.util.List;

/**
 * <p>
 * 协议表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface ISelectService  {




    /**
     * 品类下拉列表
     * @return
     */
    List<SelectedVO> getListSelectCategory();

    /**
     * 楼盘下拉列表
     * @return
     */
    List<SelectedVO> ListSelectsRealestate();


    /**
     * 项目户型下拉列表
     * @param projectId
     * @return
     */
    List<SelectedVO> getListSelectTemplates(String projectId);


    /**
     * 根据家庭获取家庭设备下拉列表
     * @param familyId
     * @return
     */
    List<SelectedVO> getSelectByFamilyId(String familyId);
}
