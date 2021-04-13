package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDeleteDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDeviceConfigVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneAcionQueryVO;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *户型场景动作配置业务
 * @author lokiy
 * @since 2021-04-07
 */
public interface ITemplateSceneActionConfigService extends IService<TemplateSceneActionConfig> {

    List<TemplateSceneActionConfig> getActionsByTemplateId(Long houseTemplateId);

    /**
     * 新增场景动作
     * @param requestObject
     */
    void addSeceneAction(HouseSceneInfoDTO requestObject);

    /**
     * 修改场景动作
     * @param requestObject
     */
    void updateSecneAction(HouseSceneInfoDTO requestObject);

    /**
     * 删除场景动作
     * @param sceneDeleteDTO
     */
    void deleteSecneAction(HouseSceneDeleteDTO sceneDeleteDTO);

    /**
     * 根据场景id删除其下的动作配置
     * @param sceneId
     */
    void deleteSecneActionBySeneId(Long sceneId);

    /**
     * 查看场景下某一设备的配置（修改场景动作）
     * @param requestObject
     * @return
     */
    HouseSceneDeviceConfigVO getDeviceAction(SceneAcionQueryVO requestObject);
}
