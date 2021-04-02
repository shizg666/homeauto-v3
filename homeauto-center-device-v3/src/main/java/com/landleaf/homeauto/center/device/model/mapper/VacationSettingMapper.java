package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.VacationSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 假期记录表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-15
 */
public interface VacationSettingMapper extends BaseMapper<VacationSetting> {

    /**
     * 判断某天的类别 正常日期-0  节假日（包括周末（法定节假日）-1 补班日2
     * @param day
     * @return
     */
   @Select("select type from vacation_setting where day = TO_TIMESTAMP(#{day}, 'yyyy-mm-dd') limit 1")
    Integer getSomeDayType(@Param("day") String day);
}
