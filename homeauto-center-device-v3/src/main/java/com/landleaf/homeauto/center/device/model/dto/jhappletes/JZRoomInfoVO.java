package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.smart.vo.FamilyRoomVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 家庭所在城市天气信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel(value="JZRoomInfoVO", description="房间信息")
public class JZRoomInfoVO {

    @NotEmpty(message = "房间名称不能为空！")
    @Length(max = 5,message = "房间名称不能超5个字符！")
    @ApiModelProperty("房间名称")
    private String name;

    @ApiModelProperty("房间主键id")
    private Long roomId;


}
