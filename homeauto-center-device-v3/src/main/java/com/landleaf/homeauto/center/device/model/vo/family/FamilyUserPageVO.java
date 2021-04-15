package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyUserPageVO", description="用户家庭成员列表对象")
public class FamilyUserPageVO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "类型1 管理员账户 0普通用户 2 运维人员账户")
    private Integer type;

    @ApiModelProperty(value = "类型1 管理员账户 0普通用户 2 运维人员账户")
    private String typeStr;

}
