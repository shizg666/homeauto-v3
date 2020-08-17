package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;

import java.util.List;

/**
 * <p>
 * 智齿客服平台-工单分类-自定义字段-可选项 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface ISobotTicketTypeFiledOptionService extends IService<SobotTicketTypeFiledOption> {

    void saveOptionData(List<SobotTicketTypeFiledOption> saveDatas);

    List<SobotTicketTypeFiledOption> getByFieldid(String repirFieldId);
}
