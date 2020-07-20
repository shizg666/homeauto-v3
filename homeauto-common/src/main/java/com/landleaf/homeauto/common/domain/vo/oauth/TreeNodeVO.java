package com.landleaf.homeauto.common.domain.vo.oauth;


import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wyl
 * @description 前台展示树形控件的数据模型
 * @return
 */
@Data
public class TreeNodeVO implements Serializable, Comparable<TreeNodeVO> {

    private static final long serialVersionUID = 2362948436301127870L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "编码")
    private String permissionCode;

    @ApiModelProperty(value = "父级别菜单ID")
    private String pid;

    @ApiModelProperty(value = "后端接口校验凭据")
    private String path;

    @ApiModelProperty(value = "前端组件名")
    private String componentName;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "权限类型（菜单、按钮）")
    private Integer permissionType;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "meta")
    private String meta;
    /**
     * 子节点
     */
    private List<TreeNodeVO> children = new ArrayList<TreeNodeVO>();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TreeNodeVO)) {
            return false;
        }
        TreeNodeVO other = (TreeNodeVO) obj;
        return StringUtils.equals(this.permissionCode, other.permissionCode)
                && StringUtils.equals(this.permissionName, other.permissionName)
                ;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(TreeNodeVO o) {
        if (this.sort == null) {
            this.sort = 0;
        }
        if (o.sort == null) {
            o.sort = 0;
        }
        return (int) Math.signum(this.sort - o.sort);
    }

    /**
     * 向当前节点添加子节点，添加完成后按显示顺序排序
     *
     * @param child
     * @author wyl
     * @date 2017年09月12日23:40:30
     */
    public void addChild(TreeNodeVO child) {
        if (child != null) {
            this.children.add(child);
            Collections.sort(this.children);
        }
    }

}
