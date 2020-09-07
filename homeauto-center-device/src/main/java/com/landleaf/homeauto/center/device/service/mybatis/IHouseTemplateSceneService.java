package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

/**
 * <p>
 * 户型情景表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface IHouseTemplateSceneService extends IService<HouseTemplateScene> {

    void add(HouseSceneDTO request);

    void update(HouseSceneDTO request);


    void delete(ProjectConfigDeleteDTO request);
}
