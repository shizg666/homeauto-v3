package com.landleaf.homeauto.center.device.controller.app.smart;


import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 报警信息 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/app/smart/alarm-message")
@Api(tags = "安防报警相关")
public class AlarmMessageController extends BaseController {

    @Autowired
    private IHomeAutoAlarmMessageService iHomeAutoAlarmMessageService;

    @GetMapping("/list/{deviceId}")
    @ApiOperation("获取报警记录列表")
    public Response<List<AlarmMessageRecordVO>> getAlarmlist(@PathVariable("deviceId") String deviceId) {
        List<AlarmMessageRecordVO> msglist = iHomeAutoAlarmMessageService.getAlarmlistByDeviceId(deviceId);
        return returnSuccess(msglist);
    }

}
