package com.landleaf.homeauto.center.device.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.Md5Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotProperties;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotTicketProperties;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.SobotUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.FaultReportAttributeCodeEnumConst;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.constant.enums.SobotErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketExtendFieldDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query.SobotQueryTicketDetailResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketType;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeField;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
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
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private ISobotTicketService sobotTicketService;

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getTicketToken() {
        /**
         *因为生产与测试用的是同一个地址,避免token失效，每次重新获取token
         */
//        SobotTokenResponseDTO tokenResponseDTO = null;
//        try {
//            Object o = redisUtils.get(RedisCacheConst.THIRD_SOBOT_TICKET_TOKEN);
//            if (o != null) {
//                tokenResponseDTO = JSON.parseObject(JSON.toJSONString(o), SobotTokenResponseDTO.class);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        if (tokenResponseDTO != null) {
//            return tokenResponseDTO.getItem().getToken();
//        }
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
    @Transactional(rollbackFor = Exception.class)
    public HomeAutoFaultReport createUserTicket(String deviceName, String contentCode, String familyId, String phone, String userId) {

        // 家庭全信息
        String familyTitle = getFamilyTitle(familyId);

        // 报修现象
        String content = getContentByCode(contentCode);

        SobotSaveUserTicketRequestDTO requestDTO = buildTicketRequestDTO(deviceName, contentCode, content, phone, familyTitle);


        SobotSaveUserTicketResponseDTO responseDTO = sobotUtils.saveUserTicket(requestDTO, getTicketToken());

        String ticketid = responseDTO.getItem().getTicketid();

        SobotQueryTicketDetailResponseDTO ticketDetail = sobotUtils.getTicketById(ticketid, getTicketToken());


        log.info(JSON.toJSONString(requestDTO));
        // 保存工单
        sobotTicketService.saveTicket(requestDTO, ticketid);

        // 组装本地报修记录
        HomeAutoFaultReport homeautoFaultReport = new HomeAutoFaultReport();
        homeautoFaultReport.setStatus(Integer.parseInt(FaultReportStatusEnum.PENDING.getCode()));
        homeautoFaultReport.setContent(content);
        homeautoFaultReport.setDeviceName(deviceName);
        homeautoFaultReport.setRepairUserPhone(phone);
        homeautoFaultReport.setRepairTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        homeautoFaultReport.setRepairUserId(userId);
        homeautoFaultReport.setRepairUserPhone(phone);
        homeautoFaultReport.setSobotTicketId(ticketid);
        homeautoFaultReport.setFamilyId(familyId);
        homeautoFaultReport.setSobotTicketCode(ticketDetail.getItem().getTicket_code());
        return homeautoFaultReport;


    }

    /**
     * 根据报修code获取内容
     *
     * @param contentCode
     * @return
     */
    private String getContentByCode(String contentCode) {
        List<SobotTicketTypeFiledOption> repirApperanceOptions = getRepirApperanceOptions();

        Map<String, String> contentMap = repirApperanceOptions.stream().collect(Collectors.toMap(SobotTicketTypeFiledOption::getDataValue, SobotTicketTypeFiledOption::getDataName, (o, n) -> n));
        return contentMap.get(contentCode);
    }

    /**
     * 获取家庭标题
     *
     * @param familyId
     * @return
     */
    private String getFamilyTitle(String familyId) {
        FamilyInfoForSobotDTO familyInfo = homeAutoFamilyService.getFamilyInfoForSobotById(familyId);

        String familyTitle = String.format("%s-%s-%s-%s-%s", familyInfo.getRealestateName(), familyInfo.getProjectName()
                , familyInfo.getBuildingName(), familyInfo.getUnitName(), familyInfo.getRoomNo());
        return familyTitle;
    }

    /**
     * 组装请求客服平台创建工单参数
     *
     * @param deviceName
     * @param contentCode
     * @param phone
     * @param familyTitle
     * @return
     */
    private SobotSaveUserTicketRequestDTO buildTicketRequestDTO(String deviceName, String contentCode, String content, String phone, String familyTitle) {
        SobotTicketType sobotTicketType = sobotTicketTypeService.list().get(0);

        List<SobotTicketTypeField> fields = sobotTicketTypeFieldService.getFieldByTypeId(sobotTicketType.getTypeid());
        Map<String, String> fieldMap = fields.stream().collect(Collectors.toMap(SobotTicketTypeField::getAttributeCode, SobotTicketTypeField::getFieldid, (i1, i2) -> i2));

        SobotSaveUserTicketRequestDTO requestDTO = new SobotSaveUserTicketRequestDTO();
        // 标题
        requestDTO.setTicket_title(DateFormatUtils.format(new Date(),"M.d").concat(" ").concat(familyTitle).concat("报修"));
        requestDTO.setCompanyid(sobotTicketType.getCompanyid());
        // 内容为 设备名称+故障描述
        String ticketContent = deviceName.concat(" ").concat(content);
        requestDTO.setTicket_content(ticketContent);
        // 来源默认为PC 1
        requestDTO.setTicket_from("1");
        // 工单模板为默认"报修"模板
        requestDTO.setTicket_typeid(sobotTicketType.getTypeid());
        // 用户手机号
        requestDTO.setUser_tels(phone);
        // 自定义字段
        List<SobotSaveUserTicketExtendFieldDTO> extend_fields = Lists.newArrayList();
        fieldMap.forEach((k, v) -> {
            SobotSaveUserTicketExtendFieldDTO extendFieldDTO = new SobotSaveUserTicketExtendFieldDTO();
            extendFieldDTO.setFieldid(v);
            String fieldValue = null;
            if (StringUtils.equals(k, FaultReportAttributeCodeEnumConst.REPAIR_APPEARANCE.getCode())) {
                fieldValue = contentCode;
            } else if (StringUtils.equals(k, FaultReportAttributeCodeEnumConst.REPAIR_TIME.getCode())) {
                fieldValue = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            } else if (StringUtils.equals(k, FaultReportAttributeCodeEnumConst.CUSTOMER_FEEDBACK.getCode())) {
                fieldValue = ticketContent;
            } else if (StringUtils.equals(k, FaultReportAttributeCodeEnumConst.DEAL_USER.getCode())) {
                fieldValue = "户式平台";
            }
            extendFieldDTO.setField_value(fieldValue);
            extend_fields.add(extendFieldDTO);
        });

        requestDTO.setExtend_fields(extend_fields);
        return requestDTO;
    }


}
