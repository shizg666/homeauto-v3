package com.landleaf.homeauto.center.device.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.Md5Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotProperties;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotTicketProperties;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketTypeFieldService;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketTypeFiledOptionService;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketTypeService;
import com.landleaf.homeauto.center.device.util.SobotUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.FaultReportAttributeCodeEnumConst;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.constant.enums.SobotErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketExtendFieldDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeautoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketType;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeField;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.THIRD_SOBOT_TICKET_TOKEN;

/**
 * @ClassName SobotServiceImpl
 * @Description: 智齿平台服务
 * @Author wyl
 * @Date 2020/8/15
 * @Version V1.0
 **/
@Service
@Slf4j
public class SobotServiceImpl implements SobotService {

    @Autowired
    private SobotUtils sobotUtils;
    @Autowired
    private HomeAutoSobotProperties homeAutoSobotProperties;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISobotTicketTypeService sobotTicketTypeService;
    @Autowired
    private ISobotTicketTypeFieldService sobotTicketTypeFieldService;
    @Autowired
    private ISobotTicketTypeFiledOptionService sobotTicketTypeFiledOptionService;

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getTicketToken() {
        SobotTokenResponseDTO tokenResponseDTO = null;
        try {
            Object o = redisUtils.get(RedisCacheConst.THIRD_SOBOT_TICKET_TOKEN);
            if (o != null) {
                tokenResponseDTO = JSON.parseObject(JSON.toJSONString(o), SobotTokenResponseDTO.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (tokenResponseDTO != null) {
            return tokenResponseDTO.getItem().getToken();
        }
        SobotTokenResponseDTO ticketTokenFromRemote = getTicketTokenFromRemote();

        if (ticketTokenFromRemote != null && StringUtils.equals(ticketTokenFromRemote.getRet_code(), SobotErrorCodeEnumConst.SUCCESS_CODE.getCode())) {
            redisUtils.set(THIRD_SOBOT_TICKET_TOKEN, ticketTokenFromRemote, Long.parseLong(ticketTokenFromRemote.getItem().getExpires_in()));
        }
        return ticketTokenFromRemote.getItem().getToken();

    }

    @Override
    public SobotTokenResponseDTO getTicketTokenFromRemote() {
        HomeAutoSobotTicketProperties ticketProperties = homeAutoSobotProperties.getTicket();
        String appid = ticketProperties.getAppid();
        String appkey = ticketProperties.getAppkey();

        BigDecimal bigDecimal = new BigDecimal(System.currentTimeMillis()).divide(new BigDecimal(1000));
        String create_time = String.valueOf(bigDecimal.longValue());

        String sign = Md5Utils.getMD5(appid.concat(create_time).concat(appkey), "utf-8");

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("create_time", create_time);
        paramMap.put("appid", appid);
        paramMap.put("sign", sign);
        SobotTokenResponseDTO tickeToken = null;
        tickeToken = sobotUtils.getTickeToken(paramMap);
        return tickeToken;
    }

    @Override
    public SobotDataDicResponseDTO getTicketDicDataRemote() {
        String token = getTicketToken();
        return sobotUtils.getTicketDataDict(token);
    }

    @Override
    public String getDefaultTypeId() {
        List<SobotTicketType> list = sobotTicketTypeService.list();

        return list.get(0).getTypeid();
    }

    @Override
    public String getRepirFieldId(String typeId) {

        return sobotTicketTypeFieldService.getRepirFieldId(typeId, FaultReportAttributeCodeEnumConst.REPAIR_APPEARANCE.getCode());
    }

    @Override
    public SobotQueryFieldsByTypeIdResponseDTO queryTicketFiledsByTypeid(String typeId) {
        String token = getTicketToken();
        return sobotUtils.queryTicketFiledsByTypeid(typeId, token);
    }

    @Override
    public void saveExtendFieldOptions(List<SobotTicketTypeFiledOption> saveDatas) {
        sobotTicketTypeFiledOptionService.saveOptionData(saveDatas);
    }

    @Override
    public List<SobotTicketTypeFiledOption> getRepirApperanceOptions() {
        String defaultTypeId = getDefaultTypeId();
        String repirFieldId = getRepirFieldId(defaultTypeId);

        return sobotTicketTypeFiledOptionService.getByFieldid(repirFieldId);
    }

    @Override
    public HomeautoFaultReport createUserTicket(String description, String repairAppearance, String phone) {

        SobotTicketType sobotTicketType = sobotTicketTypeService.list().get(0);

        List<SobotTicketTypeField> fields = sobotTicketTypeFieldService.getFieldByTypeId(sobotTicketType.getTypeid());
        Map<String, String> fieldMap = fields.stream().collect(Collectors.toMap(SobotTicketTypeField::getAttributeCode, SobotTicketTypeField::getFieldid, (i1, i2) -> i2));

        SobotSaveUserTicketRequestDTO requestDTO = new SobotSaveUserTicketRequestDTO();
        requestDTO.setCompanyid(sobotTicketType.getCompanyid());
        requestDTO.setTicket_content(description);
        requestDTO.setTicket_from("1");
        requestDTO.setTicket_typeid(sobotTicketType.getTypeid());
        requestDTO.setUser_tels(phone);
        List<SobotSaveUserTicketExtendFieldDTO> extend_fields = Lists.newArrayList();
        fieldMap.forEach((k, v) -> {
            SobotSaveUserTicketExtendFieldDTO extendFieldDTO = new SobotSaveUserTicketExtendFieldDTO();
            extendFieldDTO.setFieldid(v);
            String fieldValue = null;
            if (StringUtils.equals(v, FaultReportAttributeCodeEnumConst.REPAIR_APPEARANCE.getCode())) {

                fieldValue = repairAppearance;
            } else if (StringUtils.equals(v, FaultReportAttributeCodeEnumConst.REPAIR_TIME.getCode())) {
                fieldValue = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            } else if (StringUtils.equals(v, FaultReportAttributeCodeEnumConst.CUSTOMER_FEEDBACK.getCode())) {
                fieldValue = description;
            } else if (StringUtils.equals(v, FaultReportAttributeCodeEnumConst.DEAL_USER.getCode())) {
                fieldValue = "户式化平台";
            }
            extendFieldDTO.setField_value(fieldValue);
            extend_fields.add(extendFieldDTO);
        });

        requestDTO.setExtend_fields(extend_fields);

        SobotSaveUserTicketResponseDTO responseDTO = sobotUtils.saveUserTicket(requestDTO, getTicketToken());
        String ticketid = responseDTO.getItem().getTicketid();
         // 组装本地报修记录
        HomeautoFaultReport homeautoFaultReport = new HomeautoFaultReport();
        homeautoFaultReport.setSobotCompanyid(sobotTicketType.getCompanyid());
        homeautoFaultReport.setStatus(FaultReportStatusEnum.NOT_ACCEPTED.getCode());
        homeautoFaultReport.setRepairAppearance(repairAppearance);
        homeautoFaultReport.setDescription(description);
        homeautoFaultReport.setDealUser("户式化平台");
        homeautoFaultReport.setRepairAppearanceName(FaultReportStatusEnum.NOT_ACCEPTED.getMsg());
        homeautoFaultReport.setRepairTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        homeautoFaultReport.setRepairUserId(null);
        homeautoFaultReport.setRepairUserPhone(phone);
        homeautoFaultReport.setSobotFrom("1");
        homeautoFaultReport.setSobotTicketId(ticketid);
        return homeautoFaultReport;


    }


}
