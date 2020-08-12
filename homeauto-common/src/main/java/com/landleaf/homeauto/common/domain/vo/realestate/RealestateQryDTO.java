package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="RealestateQryDTO", description="楼盘查询对象")
public class RealestateQryDTO extends BaseQry {


    private static final long serialVersionUID = -1083009607018779779L;


    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "地址path")
    private String path;




}
