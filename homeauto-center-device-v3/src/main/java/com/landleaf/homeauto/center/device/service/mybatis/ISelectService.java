package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;

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
    List<SelectedLongVO> ListSelectsRealestate();




    /**
     * 根据家庭获取家庭设备下拉列表
     * @param familyId
     * @return
     */
    List<SelectedVO> getSelectByFamilyId(String familyId);
}
