package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAlarmMessageMapper;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordItemVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报警信息 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-15
 */
@Service
public class HomeAutoAlarmMessageServiceImpl extends ServiceImpl<HomeAutoAlarmMessageMapper, HomeAutoAlarmMessageDO> implements IHomeAutoAlarmMessageService {

    public static final int ALARM_TYPE_1 = 1;

    private static final String SECURITY_ALARM = "安防报警";

    private static final String LU = "路:";

    private DateTimeFormatter dateDf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<AlarmMessageRecordVO> getAlarmlistByDeviceId(String deviceId) {
        List<HomeAutoAlarmMessageDO> sams = this.list(new LambdaQueryWrapper<HomeAutoAlarmMessageDO>()
                .eq(HomeAutoAlarmMessageDO::getDeviceId, deviceId)
                .eq(HomeAutoAlarmMessageDO::getAlarmType, ALARM_TYPE_1)
                .orderByDesc(HomeAutoAlarmMessageDO::getAlarmTime)
                .last("limit 30")
        );
        if(CollectionUtil.isEmpty(sams)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "安防无报警记录");
        }
        List<AlarmMessageRecordVO> result = Lists.newArrayList();
        Map<String, List<AlarmMessageRecordItemVO>> map =
                Maps.newTreeMap((o1, o2) -> (int) (LocalDate.parse(o2, dateDf).toEpochDay() - LocalDate.parse(o1, dateDf).toEpochDay()));
        sams.forEach( sam -> {
//            LocalDateTime localDateTime = LocalAndDateUtil.date2LocalDateTime(sam.getAlarmTime());
//            String dateStr = localDateTime.format(dateDf);
            String dateStr = LocalDateTimeUtil.formatTime(sam.getAlarmTime(),LocalDateTimeUtil.YYYY_MM_DD);
            String dateStr2 = LocalDateTimeUtil.formatTime(sam.getAlarmTime(),LocalDateTimeUtil.HH_MM_SS);
            map.putIfAbsent(dateStr, Lists.newArrayList());
            map.get(dateStr).add(new AlarmMessageRecordItemVO(
                    LocalDateTimeUtil.formatTime(sam.getAlarmTime(),LocalDateTimeUtil.HH_MM_SS),
                    sam.getAlarmZone() + LU + sam.getAlarmDevice(),
                    sam.getAlarmContext(),
                    sam.getAlarmTime()
            ));
        });

        map.forEach((k,v) -> result.add(new AlarmMessageRecordVO(k, v)));
        return result;
    }
}
