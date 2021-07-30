package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.LocalCollectExtranetUrlConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 本地数采外网URL配置 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
public interface LocalCollectExtranetUrlConfigMapper extends BaseMapper<LocalCollectExtranetUrlConfig> {

    /*
    获取楼盘本地数采外网ip
     */
    @Select("select cc.url from local_collect_extranet_url_config cc where cc.realestate_code = #{realestateCode}")
    String getLocalCollectConfig(@Param("realestateCode") String realestateCode);
}
