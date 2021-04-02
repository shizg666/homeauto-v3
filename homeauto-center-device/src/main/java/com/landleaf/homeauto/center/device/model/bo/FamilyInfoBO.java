package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭信息业务对象
 *
 * @author Yujiumin
 * @version 2020/8/21
 */
@Data
@NoArgsConstructor
public class FamilyInfoBO {

    private String familyId;

    private String familyCode;
    /**
     * 户型ID(因为所有业务基于户型)
     */
    private String houseTemplateId;

}
