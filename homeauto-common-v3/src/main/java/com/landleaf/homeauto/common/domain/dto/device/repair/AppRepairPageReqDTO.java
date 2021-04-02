package com.landleaf.homeauto.common.domain.dto.device.repair;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户故障报修分页查询
 *
 * @author wenyilu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户故障报修分页查询DTO", description = "客户故障报修分页查询DTO")
public class AppRepairPageReqDTO extends BaseQry {


}
