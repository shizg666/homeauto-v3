package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.model.bo.FamilyCommonDeviceBO;
import com.landleaf.homeauto.model.po.device.FamilyCommonDevicePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonDeviceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 家庭常用设备表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyCommonDeviceMapper extends BaseMapper<FamilyCommonDevicePO> {

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyCommonDeviceBO> getCommonDevicesByFamilyId(@RequestParam String familyId);

}
