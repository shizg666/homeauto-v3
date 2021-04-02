package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="AttributeScopeVO", description="属性范围对象")
public class AttributeScopeVO {

    @ApiModelProperty("类别code")
    private String max;

    @ApiModelProperty(value = "设备号")
    private String min;

    @ApiModelProperty(value = "名称")
    private String step;

    @ApiModelProperty(value = "精度")
    private String precision;

}
