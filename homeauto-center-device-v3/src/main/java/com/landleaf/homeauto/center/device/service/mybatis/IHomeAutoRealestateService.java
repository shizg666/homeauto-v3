package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeQryDTO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeStatusVO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeUpdateVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeLongVo;
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
    void deleteById(Long id);

    /**
     * 楼盘下拉列表获取
     * @return
     */
    List<SelectedLongVO> ListSelects();

    /**
     * 根据楼盘id获取楼盘开发商和地址信息
     * @return
     */
    RealestateDeveloperVO getDeveloperInfoById(Long id);


    /**
     * 楼盘下拉列表（根据用户权限配置）
     * @return
     */
    List<SelectedVO> getListSeclectsByUser();

    /**
     * 获取楼盘编号
     * @param realestateId
     * @return
     */
    String getRealestateCodeById(Long realestateId);

    /**
     * 获取楼盘path信息
     * @param realestateId
     * @return
     */
    PathBO getRealestatePathInfoById(Long realestateId);

    /**
     * 根据权限获取楼盘列表
     * @return
     */
    List<CascadeLongVo> getListCascadeSeclects(List<Long> ids);

    /**
     * 楼盘模式状态获取
     * @return
     */
    BasePageVO<RealestateModeStatusVO> getListSeclectsByProject(RealestateModeQryDTO request);

    /**
     * 获取模式下拉列表
     * @return
     */
    List<SelectedVO> getModeStatusSeclects();

    /**
     * 更改楼盘模式
     * @param request
     */
    void updateModeStatus(RealestateModeUpdateVO request);


    /**
     * 楼盘项目级联数据获取
     * @return
     */
    List<CascadeLongVo> cascadeRealestateProject(String name);

    /**
     * 楼盘项目楼栋级联数据获取 根据楼盘名称模糊查询
     * @return
     */
    List<CascadeLongVo> cascadeRealestateProjectBuild(String name);
}
