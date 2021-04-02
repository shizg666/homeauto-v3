package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAppVersionDO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionQry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
public interface HomeAutoAppVersionMapper extends BaseMapper<HomeAutoAppVersionDO> {

    /**
     * 获取当前已发布的最新版本
     * @param appType      app类型 安卓，IOS
     * @param belongApp    所属app平台 智能家居\自由方舟
     * @return
     */
    AppVersionDTO getCurrentVersion(@Param("appType") Integer appType,@Param("belongApp") String belongApp);

    List<AppVersionDTO> queryAppVersionDTOList(AppVersionQry appVersionQry);

    List<AppVersionDTO> getAppVersionsSelect( @Param("belongApp") String belongApp);

}
