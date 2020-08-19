package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 户式化APP家庭业务对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
public class FamilyForAppBO {

    private String familyId;

    private String familyName;

    private Integer lastChecked;

}
