package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SwitchSceneUpdateFlagDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;

import java.util.List;

/**
 * <p>
 * 家庭场景表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneService extends IService<FamilySceneDO> {

    /**
     * 获取所有场景
     *
     * @param familyId
     * @return
     */
    List<FamilySceneBO> getAllSceneList(String familyId);

    /**
     * 获取常用场景
     *
     * @param familyId
     * @return
     */
    List<FamilySceneBO> getCommonSceneList(String familyId);

    /**
     * 通过场景ID获取家庭所有场景
     *
     * @param sceneId 场景ID
     * @return 家庭场景
     */
    List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId);

    /**
     * 通知大屏配置更新
     *
     * @param familyId 家庭ID
     */
    AdapterConfigUpdateAckDTO notifyConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum);


    /**
     * web端新增场景
     * @param request
     */
    void add(FamilySceneDTO request);

    /**
     * web端修改场景
     * @param request
     */
    void update(FamilySceneDTO request);

    /**
     * web端删除场景
     * @param request
     */
    void delete(ProjectConfigDeleteDTO request);

    /**
     * 修改app/大屏场景修改标志
     * @param request
     */
    void updateAppOrScreenFlag(SwitchSceneUpdateFlagDTO request);

    /**
     * 查询家庭场景结合
     * @param familyId
     * @return
     */
    List<FamilyScenePageVO> getListScene(String familyId);

    /**
     * 查看场景
     * @param request
     * @return
     */
    WebSceneDetailDTO getSceneDetail(FamilySceneDetailQryDTO request);
}
