package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.vo.category.*;

import java.util.List;

/**
 * <p>
 * 产品故障属性表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface IProductAttributeErrorService  {

    /**
     * 获取故障属性信息
     * @param request
     * @return
     */
    AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request);


}
