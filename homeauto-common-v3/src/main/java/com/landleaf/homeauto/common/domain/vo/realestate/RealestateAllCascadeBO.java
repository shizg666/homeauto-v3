package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value="RealestateAllCascadeBO", description="RealestateAllCascadeBO")
public class RealestateAllCascadeBO<T> implements Serializable {

    private Long realestateId;

    private Long projectId;

    private Long familyId;

    private String realestateName;

    private String projectName;

    private String familyName;

    private String buildingCode;

    private String buildingName;

    private String unitCode;

    private String unitName;


}
