package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
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

    /**
     * 根据id获取品类详情信息
     * @param id
     * @return
     */
    CategoryDetailVO getDetailById(String id);

    /**
     * 获取协议下拉列表
     * @return
     */
    List<SelectedVO> getProtocols();
    /**
     * 获取波特率下拉列表
     * @return
     */
    List<SelectedVO> getBaudRates();
    /**
     * 获取校验模式下拉列表
     * @return
     */
    List<SelectedVO> getCheckModes();

    CategoryAttributeVO getAttributeInfo(CategoryAttributeQryDTO request);
}
