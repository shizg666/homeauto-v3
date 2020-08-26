package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailPageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailResDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 大屏apk更新记录详情 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
public interface HomeAutoScreenApkUpdateDetailMapper extends BaseMapper<HomeAutoScreenApkUpdateDetailDO> {

    void updateHistoryUnSuccessRecordsToFail(@Param("familyIds") List<String> familyIds);


    List<ApkUpdateDetailResDTO> getHistoryDetails(@Param("apkName")String apkName, @Param("projectId")String projectId,
                                                  @Param("realestateId")String realestateId, @Param("versionCode")String versionCode,
                                                  @Param("startTime")String startTime,@Param("endTime") String endTime);
}
