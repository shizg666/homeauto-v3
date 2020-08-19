package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyForAppBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface HomeAutoFamilyMapper extends BaseMapper<HomeAutoFamilyDO> {

    /**
     * 通过用户ID获取家庭
     *
     * @param userId 用户ID
     * @return 家庭列表
     */
    List<FamilyForAppBO> getFamilyByUserId(@Param("userId") String userId);

}
