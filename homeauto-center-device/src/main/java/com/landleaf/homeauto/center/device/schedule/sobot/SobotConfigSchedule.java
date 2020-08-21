package com.landleaf.homeauto.center.device.schedule.sobot;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.common.constant.enums.SobotErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDataDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.THIRD_SOBOT_TICKET_DIC_DATA_;
import static com.landleaf.homeauto.common.constant.RedisCacheConst.THIRD_SOBOT_TICKET_TOKEN;

/**
 * 智齿平台定时刷新任务
 **/
@Component
@Slf4j
public class SobotConfigSchedule {

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private SobotService sobotService;


    @Scheduled(cron = "0 0 0/12 * * ?")
    public void refreshTicketToken() {
        log.info("【智齿平台】获取工单token,开始时间:{}", new Date());


        redisUtils.del(THIRD_SOBOT_TICKET_TOKEN);
        SobotTokenResponseDTO tickeToken = null;
        try {
            tickeToken = sobotService.getTicketTokenFromRemote();

            if (tickeToken != null && StringUtils.equals(tickeToken.getRet_code(), SobotErrorCodeEnumConst.SUCCESS_CODE.getCode())) {
                redisUtils.set(THIRD_SOBOT_TICKET_TOKEN, tickeToken, Long.parseLong(tickeToken.getItem().getExpires_in()));
            }
        } catch (Exception e) {
            log.info("【智齿平台】获取工单token,异常:{}", e.getMessage(), e);
        }
        log.info("【智齿平台】获取工单token,结束时间:{},值:{}", new Date(), tickeToken == null ? null : JSON.toJSONString(tickeToken));
    }

    //    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshTicketTicketDic() {
        log.info("【智齿平台】获取[查询数据字典],开始时间:{}", new Date());
        redisUtils.del(THIRD_SOBOT_TICKET_DIC_DATA_);
        SobotDataDicResponseDTO responseDTO = null;
        try {
            responseDTO = sobotService.getTicketDicDataRemote();
            if (responseDTO != null && StringUtils.equals(responseDTO.getRet_code(), SobotErrorCodeEnumConst.SUCCESS_CODE.getCode())) {
                redisUtils.set(THIRD_SOBOT_TICKET_TOKEN, responseDTO);
            }
        } catch (Exception e) {
            log.info("【智齿平台】[查询数据字典],异常:{}", e.getMessage(), e);
        }
        log.info("【智齿平台】[查询数据字典],结束时间:{},值:{}", new Date(), responseDTO == null ? null : JSON.toJSONString(responseDTO));
    }

    @Scheduled(cron = "0 0 0/6 * * ?")
    public void refreshTicketExtendFields() {
        log.info("【智齿平台】获取[查询自定义字段定义信息],开始时间:{}", new Date());

        //默认工单分类id
        String typeId = sobotService.getDefaultTypeId();

        // 默认报修现象字段id
        String repairApperanceFieldId = sobotService.getRepirFieldId(typeId);

        List<SobotTicketTypeFiledOption> saveDatas = Lists.newArrayList();

        SobotQueryFieldsByTypeIdResponseDTO responseDTO = null;
        try {
            responseDTO = sobotService.queryTicketFiledsByTypeid(typeId);
            if (responseDTO != null && StringUtils.equals(responseDTO.getRet_code(), SobotErrorCodeEnumConst.SUCCESS_CODE.getCode())) {

                List<SobotExtendFieldDTO> items = responseDTO.getItems();
                for (SobotExtendFieldDTO item : items) {
                    if (StringUtils.equals(item.getFieldid(), repairApperanceFieldId)) {
                        List<SobotExtendFieldDataDTO> field_data_list = item.getField_data_list();
                        if (!CollectionUtils.isEmpty(field_data_list)) {
                            saveDatas.addAll(field_data_list.stream().map(i -> {
                                SobotTicketTypeFiledOption data = new SobotTicketTypeFiledOption();
                                data.setDataName(i.getData_name());
                                data.setDataValue(i.getData_value());
                                data.setFieldid(repairApperanceFieldId);
                                return data;
                            }).collect(Collectors.toList()));
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(saveDatas)) {
                sobotService.saveExtendFieldOptions(saveDatas);
            }
        } catch (Exception e) {
            log.info("【智齿平台】[查询自定义字段定义信息],异常:{}", e.getMessage(), e);
        }
        log.info("【智齿平台】[查询自定义字段定义信息],结束时间:{},值:{}", new Date(), responseDTO == null ? null : JSON.toJSONString(responseDTO));
    }
}
