package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品故障属性表
 * </p>
 *类型为 1错误码的时候  根据desc字段解析故障（按序号从低到高排序返回）
 * 型为 2 通信故障的时候 默认 0正常 1故障
 * 型为 3 数值故障的时候 根据max和min字段判断是否故障
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeErrorDTO", description="故障属性传输对象")
public class AttributeErrorDTO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "故障类型 ")
    /**
     * {@link ProtocolAttrValTypeEnum}
     */
    private Integer type;

    @ApiModelProperty(value = "故障代码")
    private String code;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

    @ApiModelProperty(value = "故障集合 map key 0,1 value 值 位从小到大")
    private List<Map<String,String>> desc;

//    @ApiModelProperty(value = "产品code")
//    private String productCode;

}
