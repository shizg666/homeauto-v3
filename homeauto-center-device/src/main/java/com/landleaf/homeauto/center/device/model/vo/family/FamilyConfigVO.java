package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value="FamilyConfigVO", description="家庭配置对象")
public class FamilyConfigVO implements Serializable {

    private static final long serialVersionUID = -369270131656738281L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址值")
    private String mac;

    @ApiModelProperty(value = "类型 1终端 2设备")
    private Integer type = 2;


    @ApiModelProperty(value = "下级没有就是null")
    private List<FamilyConfigVO> children;

    public FamilyConfigVO(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public FamilyConfigVO(String name, String mac, List<FamilyConfigVO> children) {
        this.name = name;
        this.mac = mac;
        this.children = children;
    }

}
