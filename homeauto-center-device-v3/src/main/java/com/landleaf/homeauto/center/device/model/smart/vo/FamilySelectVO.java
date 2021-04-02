package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭选择视图对象
 *
 * @author Yujiumin
 * @version 2020/10/16
 */
@NoArgsConstructor
@ApiModel("选择家庭视图对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilySelectVO {


    @JsonInclude
    @ApiModelProperty("默认切换的家庭(兼容旧接口)")
    private HomeAutoFamilyVO current;

    @ApiModelProperty("家庭列表(兼容旧接口)")
    private List<HomeAutoFamilyVO> list;


    public HomeAutoFamilyVO getCurrent() {
        return current;
    }

    public void setCurrent(HomeAutoFamilyVO current) {
        this.current = current;
    }

    public List<HomeAutoFamilyVO> getList() {
        return list;
    }

    public void setList(List<HomeAutoFamilyVO> list) {
        this.list = list;
    }
}
