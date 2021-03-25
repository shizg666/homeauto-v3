package com.landleaf.homeauto.center.device.model.dto.protocol;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName ProtocolDTO
 * @Description: TODO
 * @Author shizg
 * @Date 2020/12/28
 * @Version V1.0
 **/
@Data
@ToString
@ApiModel(value="ProtocolQryDTO", description="协议信息")
public class ProtocolQryDTO extends BaseQry {


}
