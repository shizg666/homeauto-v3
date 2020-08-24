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

    AppVersionDTO getCurrentVersion(@Param("appType") Integer appType);

    List<AppVersionDTO> queryAppVersionDTOList(AppVersionQry appVersionQry);

    List<AppVersionDTO> getAppVersionsSelect();

}
