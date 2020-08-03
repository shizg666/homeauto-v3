package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;

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

    void update(AttribureDicDTO request);
}
