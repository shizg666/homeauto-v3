package com.landleaf.homeauto.center.device.model.bo;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/10/14
 */
@Data
@Deprecated
@ApiModel("楼层业务对象")
@NoArgsConstructor
@AllArgsConstructor
public class FamilyFloorBO {

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("楼层名")
    private String floorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilyFloorBO that = (FamilyFloorBO) o;
        return Objects.equal(floorId, that.floorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(floorId);
    }
}
