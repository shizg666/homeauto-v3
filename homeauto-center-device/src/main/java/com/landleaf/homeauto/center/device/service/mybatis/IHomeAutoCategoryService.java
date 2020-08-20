package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;

import java.util.List;

/**
 * <p>
 * 品类表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface IHomeAutoCategoryService extends IService<HomeAutoCategory> {

    /**
     * 添加类别
     * @param request
     */
    void add(CategoryDTO request);

    /**
     * 修改类别
     * @param request
     */
    void update(CategoryDTO request);

    /**
     * 根据主键id删除类别
     * @param categoryId
     */
    void deleteById(String categoryId);

    /**
     * 品类分页查询
     * @param request
     * @return
     */
    BasePageVO<CategoryPageVO> pageList(CategoryQryDTO request);

    List<SelectedVO> getCategorys();

    /**
     * 根据id获取协议下拉列表
     * @param categoryId
     * @return
     */
    List<SelectedVO> getProtocolsByid(String categoryId);


    /**
     * 新增产品时获取品类下拉列表
     * @return
     */
    List<SelectedVO> getListSelectedVO();
}
