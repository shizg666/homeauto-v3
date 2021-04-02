package com.landleaf.homeauto.common.domain.vo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenyilu
 */
@Data
@ApiModel("分页返回对象")
@AllArgsConstructor
@NoArgsConstructor
public class BasePageVO<T> implements Serializable {

    /**
     * 返回结果
     */
    @ApiModelProperty("返回结果")
    private List<T> list;

    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Long total;


    @ApiModelProperty("总页数")
    private Integer pages;


    public BasePageVO(PageInfo<T> pageInfo) {
        this.list = pageInfo.getList();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
    }

}
