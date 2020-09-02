package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Mapper
@Repository
public interface FamilySceneHvacConfigMapper extends BaseMapper<FamilySceneHvacConfig> {

}
