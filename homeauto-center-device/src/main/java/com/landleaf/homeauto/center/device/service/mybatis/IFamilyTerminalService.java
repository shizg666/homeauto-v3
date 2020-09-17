package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalOperateVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalVO;
import com.landleaf.homeauto.common.domain.dto.device.family.TerminalInfoDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 家庭终端表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyTerminalService extends IService<FamilyTerminalDO> {

    /**
     * 获取家庭中的主网关设备
     *
     * @param familyId 家庭ID
     * @return 网关/大屏
     */
    FamilyTerminalDO getMasterTerminal(String familyId);

    void add(FamilyTerminalVO request);

    void update(FamilyTerminalVO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 获取家庭网关下拉列表
     * @param familyId
     * @return
     */
    List<SelectedVO> getTerminalSelects(String familyId);

    /**
     * 设置主大屏/网关
     * @param request
     */
    void switchMaster(FamilyTerminalOperateVO request);

    /**
     * 获取终端集合
     * @param familyId
     * @return
     */
    List<FamilyTerminalPageVO> getListByFamilyId(String familyId);

    /**
     * 获取家庭主终端设备信息
     * @param familyId
     * @return
     */
    TerminalInfoDTO getMasterMacByFamilyid(String familyId);
}
