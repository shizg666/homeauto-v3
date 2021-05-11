package com.landleaf.homeauto.center.data.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pilo
 */
@Data
@NoArgsConstructor
public class DeviceStatusBO {

    private Long familyId;

    private Long projectId;

    private Long realestateId;

    private String familyCode;

    private String productCode;

    private String categoryCode;

    private String deviceSn;

    private String statusCode;

    private String statusValue;

}
