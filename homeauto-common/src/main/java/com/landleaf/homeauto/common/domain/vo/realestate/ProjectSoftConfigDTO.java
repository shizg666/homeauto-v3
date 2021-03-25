package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目软件配置表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectSoftConfigDTO", description="ProjectSoftConfigDTO")
public class ProjectSoftConfigDTO{

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "是否显示 0否 1是")
    private Integer showFlag;

    @ApiModelProperty(value = "业务类型 1小区室内环境配置 2.功能显示相关3.大屏同步相关4.设备控制逻辑 5 暖通模式温度范围")
    private Integer bizType;

    @ApiModelProperty(value = "系统类型1 App配置 2 大屏相关配置")
    private Integer sysType;

    @ApiModelProperty(value = "数值 （max-min）")
    private String value;

}
