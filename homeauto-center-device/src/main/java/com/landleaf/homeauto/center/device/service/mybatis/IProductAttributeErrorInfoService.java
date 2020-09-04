package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface IProductAttributeErrorInfoService extends IService<ProductAttributeErrorInfo> {

    /**
     * 查询故障描述信息
     * @param errorAttributeId
     * @return
     */
    List<String> getListDesc( String errorAttributeId);
}
