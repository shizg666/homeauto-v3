package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Lokiy
 * @Date 2021/8/9 14:47
 * @Description 项目 模板类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenHttpProjectHouseTypeDTO extends ScreenHttpProjectDTO{

    /**
     * 模板名称
     */
    private String houseType;


    public ScreenHttpProjectHouseTypeDTO(String projectCode, String houseType) {
        super(projectCode);
        this.houseType = houseType;
    }
}
