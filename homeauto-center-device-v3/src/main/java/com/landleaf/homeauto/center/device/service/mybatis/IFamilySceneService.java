package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZFamilySceneDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZSceneDetailVO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZUpdateSceneDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
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
public interface IFamilySceneService extends IService<FamilyScene> {


    /**
     * 获取家庭场景
     * @param familyId
     * @return
     */
    List<FamilyScene> getListSceneByfId(Long familyId);

    /**
     * 获取三方家庭场景（场景图片是三方的）
     * @param familyId
     * @return
     */
    List<FamilyScene> getListThirdSceneByfId(Long familyId);

    /**
     * 新增场景
     * @param request
     * @return
     */
    Long addScene(Long FamilyId ,JZFamilySceneDTO request);

    /**
     * 修改场景
     * @param familyId
     * @param request
     */
    void updateScene(Long familyId, JZFamilySceneDTO request);

    /**
     * 查看场景详情
     * @param sceneId
     * @return
     */
    JZSceneDetailVO getDetailBySceneId(Long sceneId);

    void removeByFamilyIds(List<Long> ids);

    void removeByFamilyId(Long familyId);

    /**
     * 获取家庭id
     * @param sceneId
     * @return
     */
    Long getFamilyIdById(Long sceneId);

    /**
     * 删除场景
     * @param sceneId
     */
    void removeBySceneId(Long sceneId);

    /**
     * 同步家庭场景数据
     * @param familyId
     * @return
     */
    List<SyncSceneInfoDTO> getListSyncSceneByfId(Long familyId);
}
