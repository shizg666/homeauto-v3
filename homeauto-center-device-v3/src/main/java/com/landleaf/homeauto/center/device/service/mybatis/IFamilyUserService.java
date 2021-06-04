package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateWebDTO;
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
    List<CountBO> getCountByFamilyIds(List<Long> familyIds);

   /**
    * APP移除家庭成员
    * @param familuserDeleteVO 移除成员信息
    * @return void
    * @author wenyilu
    * @date  2020/12/28 17:07
    */
    void deleteFamilyMember(FamiluserDeleteVO familuserDeleteVO);

    /**
     *  判断某一用户在家庭里是否是管理员 不是的话直接报错
     * @param familyId  家庭ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:38
     */
    void checkAdmin(Long familyId);

    /**
     * 判断某一用户在家庭里是否是管理员
     *
     * @param familyId
     * @return
     */
    boolean checkAdminReturn(Long familyId);
    /**
     *  退出家庭
     * @param familyId  家庭ID
     * @param userId    用户ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:30
     */
    void quitFamily(Long familyId,String userId);

   /**
    *  绑定家庭
    * @param familuseAddDTO   家庭ID/用户类型
    * @param userId            用户ID
    * @return void
    * @author wenyilu
    * @date 2021/1/12 11:33
    */
    void addFamilyMember(FamiluseAddDTO familuseAddDTO,String userId);

    public void addFamilyMemberById(Long familyId, String userId);

    /**
     * 扫码绑定家庭（渠道app/大屏）
     * @param familyId       type:familyId/家庭编号
    * @param userId         用户ID
     * @return void
     * @author wenyilu
     * @date  2021/1/6 10:32
     */
    void addFamilyMember(String familyId,String userId);
    /**
     * 删除家庭下的运维角色
     *
     * @param familyId
     */
    void deleteOperation(Long familyId);

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
    void deleteById(Long id);

    /**
     *  通过APP设置管理员
     * @param familyUserOperateDTO  家庭Id、记录Id
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:40
     */
    void settingAdmin(FamilyUserOperateDTO familyUserOperateDTO);

  /**
   *  web设置管理员------因为App的返回对象主键id是字符串，跟数据库不一致所以web接口单拎出来
   * @param familyUserOperateDTO  家庭Id、记录Id
   * @return void
   * @author shizg
   * @date 2021/1/12 11:40
   */
  void settingAdminWeb(FamilyUserOperateWebDTO familyUserOperateDTO);

    /**
     * 获取家庭组信息列表
     *
     * @param familyId
     * @return
     */
    List<FamilyUserPageVO> getListFamilyMember(Long familyId);


    /**
     * 查看用户在所绑定的所有家庭下是否是管理员（有一个是就是）
     *
     * @param userId
     * @return
     */
    Boolean checkAdminByUser(String userId);

   /**
    *  根据用户ID获取用户绑定家庭列表
    * @param userId  用户ID
    * @return  java.util.List<com.landleaf.homeauto.center.device.model.domain.FamilyUserDO>
    * @author  wenyilu
    * @date  2021/1/12 9:23
    */
    List<FamilyUserDO> listByUserId(String userId);
}
