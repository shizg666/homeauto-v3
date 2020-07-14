package com.landleaf.homeauto.common.domain.vo.dic;

import lombok.*;

import java.io.Serializable;

/**
 * 字典表视图层对象
 *
 * @author Yujiumin
 * @version 2020/07/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DicVO implements Serializable {

    private Integer id;
    private String name;
    private String code;
    private String parentCode;
    private String description;
    private String valueType;
    private Integer sysCode;
    private Integer order;
    private Object value;
    private Boolean enabled;

}
