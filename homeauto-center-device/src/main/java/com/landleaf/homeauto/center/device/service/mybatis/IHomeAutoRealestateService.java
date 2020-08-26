package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;

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
}
