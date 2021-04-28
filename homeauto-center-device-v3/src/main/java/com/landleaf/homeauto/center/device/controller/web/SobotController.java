package com.landleaf.homeauto.center.device.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeautoFaultReportService;
import com.landleaf.homeauto.center.device.util.Md5Utils;
import com.landleaf.homeauto.center.device.util.SobotUtils;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldsResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.receive.SobotReveiveMessageResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.receive.SobotReveiveMessageResponseItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback.SobotCallBackMessageDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveAgentTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query.SobotQueryTicketDetailResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query.SobotQueryTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.util.StreamUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName SobotController
 * @Description: 智齿客服系统相关接口
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@RestController
@RequestMapping("/sobot")
@Slf4j
public class SobotController extends BaseController {

    @Autowired
    private SobotUtils sobotUtils;
    @Autowired
    private IHomeautoFaultReportService homeautoFaultReportService;

    /**
     * 获取token
     */
    @ApiOperation("获取全局token")
    @GetMapping("/get_token")
    public Response getToken(@RequestParam("appid") String appid,
                             @RequestParam("app_key") String app_key,
                             @RequestParam("create_time") String create_time) {

        String sign = Md5Utils.getMD5(appid.concat(create_time).concat(app_key), "utf-8");

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("appid", appid);
        paramMap.put("create_time", create_time);
        paramMap.put("sign", sign);

        SobotTokenResponseDTO result = sobotUtils.getTickeToken(paramMap);

        return returnSuccess(result);
    }

    /**
     * 查询数据字典
     */
    @ApiOperation("查询数据字典")
    @GetMapping("/get_data_dict")
    public Response getDataDict(@RequestParam String token) {

        SobotDataDicResponseDTO result = sobotUtils.getTicketDataDict(token);

        return returnSuccess(result);
    }

    /**
     * 查询自定义字段
     */
    @ApiOperation("查询自定义字段")
    @GetMapping("/query_ticket_extend_fields")
    public Response queryTicketExtendFields(@RequestParam String token) {
        SobotExtendFieldsResponseDTO result = sobotUtils.queryTicketExtendFields(token);
        return returnSuccess(result);
    }

    /**
     * 查询工单分类关联的工单模板
     */
    @ApiOperation("查询工单分类关联的工单模板")
    @GetMapping("/query_fileds_by_typeid")
    public Response queryTicketFiledsByTypeid(@RequestParam String ticket_typeid, @RequestParam String token) {

        SobotQueryFieldsByTypeIdResponseDTO result = sobotUtils.queryTicketFiledsByTypeid(ticket_typeid, token);

        return returnSuccess(result);
    }

    /**
     * 创建工单-客户
     */
    @ApiOperation("创建工单（客户）")
    @PostMapping("/save_user_ticket")
    public Response saveUserTicket(@RequestBody SobotSaveUserTicketRequestDTO requestDTO, @RequestParam String token) {

        SobotSaveUserTicketResponseDTO result = sobotUtils.saveUserTicket(requestDTO, token);

        return returnSuccess(result);
    }

    /**
     * 创建工单-坐席
     */
    @ApiOperation("创建工单（坐席）")
    @PostMapping("/save_agent_ticket")
    public Response saveAgentTicket(@RequestBody SobotSaveAgentTicketRequestDTO requestDTO, @RequestParam String token) {

        SobotSaveUserTicketResponseItemDTO result = sobotUtils.saveAgentTicket(requestDTO, token);

        return returnSuccess(result);
    }

    /**
     * 查询工单列表
     */
    @ApiOperation("查询工单分类关联的工单模板")
    @GetMapping("/query_tickets")
    public Response queryTicket(@RequestParam String token,
                                @RequestParam("create_start_datetime") String create_start_datetime,
                                @RequestParam("create_end_datetime") String create_end_datetime,
                                @RequestParam("page_no") Integer page_no,
                                @RequestParam("page_size") Integer page_size) {

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("create_start_datetime", create_start_datetime);
        paramMap.put("create_end_datetime", create_end_datetime);
        paramMap.put("page_no", page_no);
        paramMap.put("page_size", page_size);

        SobotQueryTicketResponseDTO result = sobotUtils.queryTicket(paramMap, token);

        return returnSuccess(result);
    }

    /**
     * 查询工单详情
     */
    @ApiOperation("查询工单详情")
    @GetMapping("/get_ticket_by_id")
    public Response getTicketById(@RequestParam String ticketid, @RequestParam String token) {

        SobotQueryTicketDetailResponseDTO result = sobotUtils.getTicketById(ticketid, token);

        return returnSuccess(result);
    }

    /**
     * 工单消息转发
     */
    @ApiOperation("工单消息转发")
    @RequestMapping(value = "/message",method = {RequestMethod.GET,RequestMethod.POST})
    public SobotReveiveMessageResponseDTO receiveMessage(HttpServletRequest httpServletRequest) {

        try {
            byte[] body = StreamUtils.getByteByStream(httpServletRequest.getInputStream());
            String data = new String(body, StandardCharsets.UTF_8);
            SobotCallBackMessageDTO sobotCallBackMessageDTO = JSON.parseObject(data, SobotCallBackMessageDTO.class);
            String type = sobotCallBackMessageDTO.getType();
            if (StringUtils.equals("ticket", type)) {
                // 消息类型为工单消息才处理
                homeautoFaultReportService.updateStatus(sobotCallBackMessageDTO.getContent());
            }
            log.info("接收到[智齿客服平台]消息:{}", data);
        } catch (Exception e) {
            log.error("接收到[智齿客服平台]消息，错误信息：{}", e.getMessage(), e);
        }
        SobotReveiveMessageResponseDTO responseDTO = new SobotReveiveMessageResponseDTO();
        responseDTO.setRetCode("000000");
        responseDTO.setRetMsg("数据接收成功");
        SobotReveiveMessageResponseItemDTO item = new SobotReveiveMessageResponseItemDTO();
        item.setReceiveTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        responseDTO.setData(item);
        return responseDTO;
    }


}
