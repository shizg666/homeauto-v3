package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.enums.SceneEnum;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SwitchSceneUpdateFlagDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
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
     * 通过场景ID获取家庭所有场景
     *
     * @param sceneId 场景ID
     * @return 家庭场景
     */
    List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId);

    /**
     * 通过 familyId 和 sceneEnum 获取实体列表
     *
     * @param familyId  家庭ID
     * @param sceneEnum 场景类型
     * @return 场景实体列表
     */
    List<FamilySceneDO> getFamilySceneByType(String familyId, SceneEnum sceneEnum);

    /**
     * 通知大屏配置更新
     *
     * @param familyId 家庭ID
     */
    void notifyConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum);


    /**
     * web端新增场景
     *
     * @param request
     */
    void add(FamilySceneDTO request);

    /**
     * web端修改场景
     *
     * @param request
     */
    void update(FamilySceneDTO request);

    /**
     * web端删除场景
     *
     * @param request
     */
    void delete(ProjectConfigDeleteDTO request);

    /**
     * 修改app/大屏场景修改标志
     *
     * @param request
     */
    void updateAppOrScreenFlag(SwitchSceneUpdateFlagDTO request);

    /**
     * 查询家庭场景结合
     *
     * @param familyId
     * @return
     */
    List<FamilyScenePageVO> getListScene(String familyId);

    /**
     * 查看场景
     *
     * @param request
     * @return
     */
    WebSceneDetailDTO getSceneDetail(FamilySceneDetailQryDTO request);


    /**
     * 大屏拉取家庭场景列表（大屏同步场景数据）
     *
     * @param familyId
     * @return
     */
    List<SyncSceneInfoDTO> getListSyncScene(String familyId);

    /**
     * 网关家庭信息同步
     *
     * @param familyId
     * @return
     */
    void getSyncInfo(String familyId);

    /**
     * 删除家庭场景配置
     *
     * @param familyId
     */
    void deleteByFamilyId(String familyId);

    /**
     * 通过familyId, 查询常用场景信息
     *
     * @param familyId 家庭ID
     * @return 常用场景信息列表
     */
    List<FamilySceneDO> listByFamilyId(String familyId);

    /**
     * 获取带有索引的场景列表
     *
     * @param familySceneDOList       家庭里所有的场景
     * @param familyCommonSceneDOList 家庭里常用场景
     * @param commonUse               是否返回常用的
     * @return 带索引的场景列表
     */
    List<FamilySceneBO> getFamilySceneWithIndex(List<FamilySceneDO> familySceneDOList, List<FamilyCommonSceneDO> familyCommonSceneDOList, boolean commonUse);

    AdapterConfigUpdateDTO getSyncConfigInfo(String familyId);

    /**
     * 通过 sceneId 获取场景联动设备信息
     *
     * @param sceneId 场景ID
     * @return
     */
    List<FamilyDeviceBO> getLinkageDevice(String sceneId);
}
