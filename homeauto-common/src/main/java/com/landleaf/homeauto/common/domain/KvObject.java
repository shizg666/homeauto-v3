package com.landleaf.homeauto.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * @author Lokiy
 * @date 2019/9/18 16:39
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("key-value对象")
public class KvObject {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KvObject kvObject = (KvObject) o;
        return Objects.equals(key, kvObject.key) &&
                Objects.equals(value, kvObject.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
