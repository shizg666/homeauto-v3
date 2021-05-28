package com.landleaf.homeauto.contact.screen.dto.payload;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品属性值表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ContactScreenDeviceAttributeInfo", description="产品属性值表")
public class ContactScreenDeviceAttributeInfo {


    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String code;

    @ApiModelProperty(value = "排序号")
    private Integer sortNo;


}
