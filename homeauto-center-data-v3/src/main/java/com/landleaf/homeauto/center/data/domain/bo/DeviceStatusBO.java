package com.landleaf.homeauto.center.data.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pilo
 */
@Data
@NoArgsConstructor
public class DeviceStatusBO {

    private String familyId;

    private String familyCode;

    private String productCode;

    private String categoryCode;

    private String deviceCode;

    private String statusCode;

    private String statusValue;

}
