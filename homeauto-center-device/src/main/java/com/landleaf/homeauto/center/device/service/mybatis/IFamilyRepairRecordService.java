package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyRepairRecord;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordAddDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordPageRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyRepairRecordVO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.SysRoleDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

/**
 * <p>
 * 家庭维修记录 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
public interface IFamilyRepairRecordService extends IService<FamilyRepairRecord> {

    /**
     *  添加維修記錄
     * @param requestDTO
     * @return void
     * @author wenyilu
     * @date 2021/1/29 16:13
     */
    void addRecord(FamilyRepairRecordAddDTO requestDTO);

    /**
     * 修改維修記錄
     * @param requestDTO
     */
    void updateRecord(FamilyRepairRecordUpdateDTO requestDTO);

    /**
     * 根據 維修記錄ID查詢詳情
     * @param id
     * @return
     */
    FamilyRepairRecordVO detail(String id);

    /**
     *  分页查询
     * @param requestDTO
     * @return com.landleaf.homeauto.common.domain.vo.BasePageVO<com.landleaf.homeauto.center.device.model.vo.FamilyRepairRecordVO>
     * @author wenyilu
     * @date 2021/1/29 17:01
     */
    BasePageVO<FamilyRepairRecordVO> pageList(FamilyRepairRecordPageRequestDTO requestDTO);

    /**
     *  删除
     * @param id
     * @return void
     * @author wenyilu
     * @date 2021/1/29 17:10
     */
    void delete(String id);
}
