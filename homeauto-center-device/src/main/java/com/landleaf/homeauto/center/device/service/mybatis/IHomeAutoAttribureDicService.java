package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicPageVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicQryDTO;

import java.util.List;

/**
 * <p>
 * 属性字典表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface IHomeAutoAttribureDicService extends IService<HomeAutoAttribureDic> {

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
    List<SelectedVO> getAttributeDicTypes();

    /**
     * 获取属性性质 AttributeNatureEnum
     * @return
     */
    List<SelectedVO> getAttributeDicNatures();
}
