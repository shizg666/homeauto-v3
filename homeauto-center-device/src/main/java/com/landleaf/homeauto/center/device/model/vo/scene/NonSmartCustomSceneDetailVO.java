package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自由方舟APP自定义场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("自由方舟APP自定义场景详情视图对象")
public class NonSmartCustomSceneDetailVO extends BaseSceneVO {

    @ApiModelProperty("场景联动组")
    List<CustomSceneLinkageGroupVO> linkageGroups;

}
