package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@ApiModel(value="RealestateQryDTO", description="楼盘查询对象")
public class RealestateQryDTO extends BaseQry {


    private static final long serialVersionUID = -1083009607018779779L;

    @ApiModelProperty(value = "楼盘名称 ")
    private String name;
//
//    @ApiModelProperty(value = "用户paths ")
//    private List<String> paths;





}
