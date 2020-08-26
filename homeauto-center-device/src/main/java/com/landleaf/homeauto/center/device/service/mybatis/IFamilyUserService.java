package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;

import java.util.List;

/**
 * <p>
 * 家庭组表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
public interface IFamilyUserService extends IService<FamilyUserDO> {

    /**
     * 切换家庭
     *
     * @param userId 用户ID
     * @param familyId 家庭ID
     */
    void checkoutFamily(String userId, String familyId);

    /**
     * 检查用户需要切换的家庭是否存在
     *
     * @param userId   用户id
     * @param familyId 家庭Id
     * @return 是否存在
     */
    boolean isFamilyExisted(String userId, String familyId);

    /**
     * 根据用户id获取用户关联家庭id列表
     * @return
     */
    List<String> getFamilyIdsByUserId(String userId);

    /**
     * 统计家庭用户数量
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);
}
