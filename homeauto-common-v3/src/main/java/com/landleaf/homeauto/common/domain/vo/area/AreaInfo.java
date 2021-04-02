package com.landleaf.homeauto.common.domain.vo.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AreaInfo implements Serializable {


    private static final long serialVersionUID = -2840298667041271588L;
    private String code;

    private String name;

    private String type;

    private String path;

    private String pathName;
}
