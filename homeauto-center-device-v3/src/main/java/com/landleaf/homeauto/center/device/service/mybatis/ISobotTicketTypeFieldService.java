package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeField;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface ISobotTicketTypeFieldService extends IService<SobotTicketTypeField> {

    /**
     * 查找报修现象id
     * @param typeId         工单分类ID
     * @param attributeCode  对应的属性编码
     * @return
     */
    String getRepirFieldId(String typeId, String attributeCode);

    List<SobotTicketTypeField> getFieldByTypeId(String typeid);
}
