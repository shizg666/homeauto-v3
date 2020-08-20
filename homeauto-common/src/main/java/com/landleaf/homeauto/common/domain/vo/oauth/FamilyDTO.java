package com.landleaf.homeauto.common.domain.vo.oauth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@ApiModel("FamilyDTO")
public class FamilyDTO {


        @ApiModelProperty("家庭ID")
        private String familyId;

        @ApiModelProperty("家庭名称")
        private String familyName;




}
