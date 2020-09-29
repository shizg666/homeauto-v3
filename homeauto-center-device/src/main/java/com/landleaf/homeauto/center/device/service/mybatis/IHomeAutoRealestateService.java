package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.*;

import java.util.List;

/**
 * <p>
 * 楼盘表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IHomeAutoRealestateService extends IService<HomeAutoRealestate> {

    /**
     * 新增
     * @param request
     */
    void add(RealestateDTO request);


    /**
     * 修改
     * @param request
     */
    void update(RealestateDTO request);


    /**
     * 分页查询
     * @param request
     * @return
     */
    BasePageVO<RealestateVO> page(RealestateQryDTO request);

    /**
     * 删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 楼盘下拉列表获取
     * @return
     */
    List<SelectedVO> ListSelects();

    /**
     * 根据楼盘id获取楼盘开发商和地址信息
     * @return
     */
    RealestateDeveloperVO getDeveloperInfoById(String id);

    /**
     * 楼盘状态下拉列表获取
     * @return
     */
    List<SelectedIntegerVO> getRealestateStatus();

    /**
     * 楼盘下拉列表（根据用户权限配置）
     * @return
     */
    List<SelectedVO> getListSeclects();

    /**
     * 获取楼盘编号
     * @param realestateId
     * @return
     */
    String getRealestateNoById(String realestateId);

    /**
     * 获取楼盘path信息
     * @param realestateId
     * @return
     */
    PathBO getRealestatePathInfoById(String realestateId);

    /**
     * 根据权限获取楼盘列表
     * @return
     */
    List<CascadeVo> getListCascadeSeclects(List<String> ids);

    /**
     * 楼盘模式状态获取
     * @return
     */
    List<RealestateModeStatusVO> getListSeclectsByProject();

    /**
     * 获取模式下拉列表
     * @return
     */
    List<SelectedVO> getModeStatusSeclects();

    /**
     * 更改楼盘模式
     * @param realestateId
     */
    void updateModeStatus(String realestateId);
}
