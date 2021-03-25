package com.landleaf.homeauto.center.device.model.vo;

import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;

import java.util.List;

/**
 * @ClassName BasePageVOFactory
 * @Description: TODO
 * @Author shizg
 * @Date 2021/1/6
 * @Version V1.0
 **/
public class BasePageVOFactory {

    public static  <T>   BasePageVO  getBasePage(List<T> data){
        PageInfo<T> pageInfo = new PageInfo(data);
        BasePageVO<T> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }
}
