package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Mapper
@Repository
public interface FamilySceneHvacConfigActionMapper extends BaseMapper<FamilySceneHvacConfigAction> {

    /**
     * 根据暖通配置id集合 查询暖通动作id集合
     * @param hvacConfigIds
     * @return
     */
    List<String> getListIds(@Param("hvacConfigIds") List<String> hvacConfigIds);

}
