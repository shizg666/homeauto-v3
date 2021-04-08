package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SwitchSceneUpdateFlagDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

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

    /**
     * 查询户型场景列表
     * @param templageId
     * @return
     */
    List<HouseScenePageVO> getListScene(String templageId);

    /**
     * 查看场景
     * @param request
     * @return
     */
    WebSceneDetailDTO getSceneDetail(SceneDetailQryDTO request);

    /**
     * 删除户型下的所有场景
     * @param templateId
     */
    void deleteByTempalteId(String templateId);

    /**
     * 根据户型ID查询所属场景
     * @param templateId   户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene>
     * @author wenyilu
     * @date  2021/1/5 15:42
     */
    List<HouseTemplateScene> getScenesByTemplate(String templateId);



    /**
     * 获取带有索引的场景列表
     *
     *
     * @param familyId
     * @param templateId
     * @param templateScenes       家庭里所有的场景
     * @param familyCommonSceneDOList 家庭里常用场景
     * @param commonUse               是否返回常用的
     * @return 带索引的场景列表
     */
    List<FamilySceneBO> getFamilySceneWithIndex(String familyId, String templateId, List<HouseTemplateScene> templateScenes, List<FamilyCommonSceneDO> familyCommonSceneDOList, boolean commonUse);

    /**
     * 切换场景可修改标志
     * @param sceneId
     */
    void switchUpdateFlagStatus(Long sceneId);
}
