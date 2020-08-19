package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoAttributeDic;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;

import java.util.List;

/**
 * <p>
 * 属性字典表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface IHomeAutoAttributeDicService extends IService<HomeAutoAttributeDic> {

    /**
     * 字典属性添加
     * @param request
     */
    void add(AttributeDicDTO request);

    /**
     * 更新属性
     * @param request
     */
    void update(AttributeDicDTO request);

    /**
     * 条件分页查询
     * @param request
     * @return
     */
    BasePageVO<AttributeDicPageVO> pageList(AttributeDicQryDTO request);

    /**
     * 根据主键查询属性详情
     * @param id
     * @return
     */
    AttributeDicDetailVO getDetailById(String id);

    /**
     * 删除属性
     * @param id
     */
    void deleteById(String id);

    /**
     * 获取属性类别 AttributeTypeEnum
     * @return
     */
    List<SelectedIntegerVO> getAttributeDicTypes();

    /**
     * 获取属性性质 OperationLogTypeEnum
     * @return
     */
    List<SelectedIntegerVO> getAttributeDicNatures();

    /**
     * 产品功能属性名称下拉列表
     * @return
     */
    List<SelectedVO> getAttributes();

    /**
     * 根据属性code获取属性的级联信息
     * @param code
     * @return
     */
    AttributeCascadeVO getCascadeInfoByCode(String code);


    /**
     * 获取所有属性字典列表
     * @return
     */
    List<AttributeDicVO> getListAttributes();
}
