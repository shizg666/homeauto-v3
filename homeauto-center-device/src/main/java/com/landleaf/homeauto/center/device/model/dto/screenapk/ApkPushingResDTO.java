package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenyilu
 */
@ApiModel(value = "PushingResDTO", description = "正在推送记录返回对象")
@Data
public class ApkPushingResDTO extends BaseQry implements Serializable {

    private static final long serialVersionUID = -5672879399485371149L;
    @ApiModelProperty(value = "推送总数")
    private Integer totalCount;
    @ApiModelProperty(value = "未成功数")
    private Integer notSuccessCount;
    @ApiModelProperty(value = "记录列表")
    private List<ApkPushingDetailResDTO> pushingDetails;


}
