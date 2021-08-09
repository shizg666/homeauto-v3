package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Lokiy
 * @Date 2021/8/9 14:43
 * @Description 项目模板请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectHouseTypeRequestPayload extends ProjectRequestPayload{

    /**
     * 户型号
     */
    private String houseType;

}
