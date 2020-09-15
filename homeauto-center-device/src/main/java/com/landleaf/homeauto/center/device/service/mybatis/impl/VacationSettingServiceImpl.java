package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.VacationSetting;
import com.landleaf.homeauto.center.device.model.mapper.VacationSettingMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IVacationSettingService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 假期记录表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-15
 */
@Service
public class VacationSettingServiceImpl extends ServiceImpl<VacationSettingMapper, VacationSetting> implements IVacationSettingService {
    //假期
    public static final Integer HOLIDAY = 1;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Integer getSomeDayType(String day) {
        if (StringUtil.isEmpty(day)){
            return null;
        }
        String key = String.format(RedisCacheConst.DAY_TYPE_KEY,day);
        Integer type = (Integer) redisUtils.get(key);
        if (type != null){
            return type;
        }
        type = this.baseMapper.getSomeDayType(day);
        if (type == null){
            int dayOfWeek = LocalDateTimeUtil.parseStr2LocalDate(day,LocalDateTimeUtil.YYYY_MM_DD).getDayOfWeek().getValue();
            if (dayOfWeek == 7 || dayOfWeek == 6){
                type = 1;
            }else {
                type = 0;
            }
        }else if (HOLIDAY.equals(type)){
            type = 1;
        }else {
            type = 0;
        }
        redisUtils.set(key,type,86400L);
        return type;
    }
}
