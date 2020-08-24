package com.landleaf.homeauto.center.device.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 家庭常用场景业务对象
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilySceneBO {

    private String sceneId;

    private String sceneName;

    private String sceneIcon;

    private Integer index;

    /**
     * 如果两个FamilySceneBO对象的sceneId相等
     * 则认为它们相等
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FamilySceneBO that = (FamilySceneBO) o;
        return Objects.equals(sceneId, that.sceneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, sceneName, sceneIcon, index);
    }
}
