package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.maintenance.FamilyMaintenanceRecord;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenancePageRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceUpdateRequestDTO;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import java.util.List;

/**
 * <p>
 * 家庭维修记录 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-05-24
 */
public interface IFamilyMaintenanceRecordService extends IService<FamilyMaintenanceRecord> {

    /*
     * @param: requestBody
     * @description: 条件分页查询维保记录
     * @return: com.landleaf.homeauto.common.domain.vo.BasePageVO<com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO>
     * @author: wyl
     * @date: 2021/5/25
     */
    BasePageVO<FamilyMaintenanceRecordVO> pageListMaintenanceRecord(FamilyMaintenancePageRequestDTO requestBody);

    /*
     * @param: id 维保记录主键ID
     * @description: 获取详情
     * @return: com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO
     * @author: wyl
     * @date: 2021/5/25
     */
    FamilyMaintenanceRecordVO detail(Long id);

    /*
     * @param: requestDTO
     * @description: 新增
     * @return: void
     * @author: wyl
     * @date: 2021/5/25
     */
    void addRecord(FamilyMaintenanceAddRequestDTO requestDTO);

    /*
     * @param: requestDTO
     * @description: 修改
     * @return: void
     * @author: wyl
     * @date: 2021/5/25
     */
    void updateRecord(FamilyMaintenanceUpdateRequestDTO requestDTO);

    /*
     * @param: id 主键
     * @description: 删除
     * @return: void
     * @author: wyl
     * @date: 2021/5/25
     */
    void delete(Long id);

    List<FamilyMaintenanceRecordVO> listByFamily(Long familyId);
}

