package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import com.landleaf.homeauto.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryPageVO", description="品类分页对象")
public class CategoryPageVO {


    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "协议 ,号分隔 ps 1,2")
    private String protocol;

    @ApiModelProperty(value = "协议字符串")
    private String protocolStr;

    @ApiModelProperty(value = "品类属性集合")
    private List<CategoryAttributeVO> attributes;

    public void setProtocol(String protocol) {
        this.protocol = protocol;
        if (StringUtil.isEmpty(protocol)){
            return;
        }
        String[] strArray = protocol.split(",");
        if (strArray.length == 0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strArray) {
            sb.append(ProtocolEnum.getInstByType(s) != null? ProtocolEnum.getInstByType(s).getName():"").append(",");
        }
        this.protocolStr = sb.toString().substring(0,sb.toString().length()-1);
    }
}
