package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 工单分类信息
 *
 * @author wenyilu
 */
@Data
public class SobotTicketTypeDTO {

    @ApiModelProperty(value = "分类ID", name = "typeid", required = true, notes = "")
    private String typeid;

    @ApiModelProperty(value = "公司ID", name = "companyid", required = true, notes = "")
    private String companyid;

    @ApiModelProperty(value = "分类名称", name = "type_name", required = true, notes = "")
    private String type_name;

    @ApiModelProperty(value = "父ID", name = "parentid", required = true, notes = "")
    private String parentid;

    @ApiModelProperty(value = "分类级别", name = "type_level", required = true, notes = "最多五级（第一级，2：第二级，3：第三级，4：第四级，5：第五级）")
    private String type_level;

    @ApiModelProperty(value = "是否叶子节点", name = "node_flag", required = true, notes = "0 是 1 不是")
    private String node_flag;

    @ApiModelProperty(value = "子分类信息", name = "sub_type_list", required = true, notes = "内容与ticket_type_list一致")
    private List<SobotTicketTypeDTO> sub_type_list;


}
