package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluseAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.common.domain.dto.device.family.familyUerRemoveDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;

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
     * 统计家庭用户数量
     *
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);

    /**
     * 移除家庭成员
     *
     * @param request
     */
    void deleteFamilyMember(FamiluserDeleteVO request);

    /**
     * 判断某一用户在家庭里是否是管理员 不是的话直接报错
     *
     * @param familyId
     * @return
     */
    void checkAdmin(String familyId);

    /**
     * 判断某一用户在家庭里是否是管理员
     *
     * @param familyId
     * @return
     */
    boolean checkAdminReturn(String familyId);

    /**
     * 退出家庭
     *
     * @param familyId
     */
    void quitFamily(String familyId);

    /**
     * 绑定家庭
     *
     * @param request
     */
    void addFamilyMember(FamiluseAddDTO request);

    /**
     * 绑定家庭
     *
     * @param familyId
     */
    void addFamilyMember(String familyId);

    /**
     * 删除家庭下的运维角色
     *
     * @param familyId
     */
    void deleteOperation(String familyId);

    void removeUser(familyUerRemoveDTO request);

    /**
     * 获取家庭成员类型列表
     *
     * @return
     */
    List<SelectedIntegerVO> getMenberTypes();

    /**
     * 添加成员
     *
     * @param request
     */
    void addMember(FamilyUserDTO request);

    /**
     * 删除成员
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 设置为管理员
     *
     * @param request
     */
    void settingAdmin(FamilyUserOperateDTO request);

    /**
     * 获取家庭组信息列表
     *
     * @param familyId
     * @return
     */
    List<FamilyUserPageVO> getListFamilyMember(String familyId);


    /**
     * 查看用户在所绑定的所有家庭下是否是管理员（有一个是就是）
     *
     * @param userId
     * @return
     */
    Boolean checkAdminByUser(String userId);

    /**
     * 通过userId查询实体列表
     *
     * @param userId 用户ID
     * @return 实体列表
     */
    List<FamilyUserDO> listByUserId(String userId);
}
