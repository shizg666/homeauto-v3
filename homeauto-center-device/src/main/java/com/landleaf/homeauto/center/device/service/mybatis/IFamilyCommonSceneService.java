package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;

import java.util.List;

/**
 * <p>
 * 家庭常用场景表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonSceneService extends IService<FamilyCommonSceneDO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景
     */
    List<String> getCommonSceneIdListByFamilyId(String familyId);

    /**
     * 删除家庭常用场景
     *
     * @param familyId
     * @param sceneId
     */
    void deleteFamilySceneCommonScene(String familyId, String sceneId);

    /**
     * 场景是否存在
     *
     * @param familyId
     * @param sceneId
     * @return
     */
    boolean isExist(String familyId, String sceneId);

    /**
     * 获取通过familyId获取家庭常用场景
     *
     * @param familyId 家庭ID
     * @return 常用场景列表信息
     */
    List<FamilyCommonSceneDO> listCommonScenesByFamilyId(String familyId);

    /**
     * 通过familyId删除实体
     *
     * @param familyId 家庭ID
     */
    void deleteByFamilyId(String familyId);

    /**
     *  APP保存常用场景
     * @param familyId  家庭ID
     * @param sceneIds 场景Ids
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:30
     */
    void saveCommonSceneList(String familyId, List<String> sceneIds);

    /**
     * 获取APP常用场景信息
     * @param familyId
     * @param templateId
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO>
     * @author wenyilu
     * @date  2020/12/25 11:15
     */
    List<FamilySceneVO> getCommonScenesByFamilyId4VO(String familyId, String templateId);

    /**
     * APP获取不常用场景
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO>
     * @author wenyilu
     * @date  2020/12/25 17:04
     */
    List<FamilySceneVO> getFamilyUncommonScenes4VOByFamilyId(String familyId);
}
