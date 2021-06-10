package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductCategory;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统产品关联品类表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
public interface ISysProductCategoryService extends IService<SysProductCategory> {

    /**
     * 批量新增系统类别属性
     * @param sysProductId 系统产品id
     * @param sysProductCode 系统产品code
     * @param categorys
     */
    void saveBathCategorAttribute(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys);


    /**
     *批量新增系统绑定的产品
     * @param sysProductId 系统产品id
     * @param sysProductCode 系统产品code
     * @param categorys
     */
    void saveBathProductCategory(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys);

    /**
     * 根据系统产品id删除关联的品类信息
     * @param sysProductId
     */
    void deleteBySysProductId(Long sysProductId);

    /**
     * 获取系统产品 关联品类VO对象列表
     * @param sysProductId
     * @return
     */
    List<SysProductCategoryVO> getListSysProductCategoryVO(Long sysProductId);

    /**
     * 获取系统产品下关联的品类数
     * @param sysPids
     * @return
     */
    Map<Long, Integer> getCountBySysPids(List<Long> sysPids);

    /**
     * 获取系统产品下关联的品类产品级联信息
     * @param sysPid
     * @return
     */
    List<SelectedVO> getListCategoryBySysPid(Long sysPid);

    /**
     * 批量获取系统产品关联的品类信息
     * @param sysPids
     * @return
     */
    List<SysProductCategory> getListCategoryBySysPids(List<Long> sysPids);

    /**
     * 获取系统产品所绑定的某一品类可配置的数量
     * @param sysPid
     * @param categoryCode
     * @return
     */
    int getCategoryNumBySysPid(Long sysPid, String categoryCode);
}
