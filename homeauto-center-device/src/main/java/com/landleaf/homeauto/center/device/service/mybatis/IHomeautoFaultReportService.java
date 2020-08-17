package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairPageReqDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeautoFaultReport;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface IHomeautoFaultReportService extends IService<HomeautoFaultReport> {

    BasePageVO<AppRepairDetailDTO> pageRepirs(AppRepairPageReqDTO requestBody, String userId);

    void createRepair(RepairAddReqDTO requestBody, String userId,String phone);
}
