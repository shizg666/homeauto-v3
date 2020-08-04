package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicPageVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicQryDTO;

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
    void add(AttribureDicDTO request);

    /**
     * 更新属性
     * @param request
     */
    void update(AttribureDicDTO request);

    /**
     * 条件分页查询
     * @param request
     * @return
     */
    BasePageVO<AttribureDicPageVO> pageList(AttribureDicQryDTO request);

    /**
     * 根据主键查询属性详情
     * @param id
     * @return
     */
    AttribureDicDetailVO getDetailById(String id);

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
