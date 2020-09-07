package com.landleaf.homeauto.center.device.model.dto.msg;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "MsgWebQry",description = "Web消息查询对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MsgWebQry extends BaseQry {

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "发布标识", required = true)
    private Integer releaseFlag;

    @ApiModelProperty( value = "项目名称列表", required = true)
    private List<String> projectNames;

    @ApiModelProperty("发布开始时间")
    private String startTime;

    @ApiModelProperty("发布结束时间")
    private String endTime;
}
