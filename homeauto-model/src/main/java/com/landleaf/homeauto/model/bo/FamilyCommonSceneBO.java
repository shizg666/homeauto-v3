package com.landleaf.homeauto.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭常用场景业务对象
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyCommonSceneBO {

    private String sceneId;

    private String sceneName;

    private String sceneIcon;

    private Integer index;

}
