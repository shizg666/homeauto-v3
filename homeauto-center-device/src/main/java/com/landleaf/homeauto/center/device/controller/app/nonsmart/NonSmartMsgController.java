package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgNoticeService;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgReadNoteService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/19
 */
@Slf4j
@RestController
@RequestMapping("/app/non-smart/msg")
@Api(tags = "自由方舟app消息接口")
public class NonSmartMsgController extends BaseController {

    @Autowired
    private IMsgNoticeService iMsgNoticeService;

    @Autowired
    private IMsgReadNoteService iMsgReadNoteService;




    @GetMapping("/list/{familyId}")
    @ApiOperation("获取消息公告列表")
    public Response<List<MsgNoticeAppDTO>> getMsglist(@PathVariable("familyId") String familyId) {
        List<MsgNoticeAppDTO> msglist = iMsgNoticeService.getMsglist(familyId);
        return returnSuccess(msglist);
    }

    @PostMapping("/add-read/note")
    @ApiOperation("添加消息已读记录")
    public Response addReadNote(@RequestBody MsgReadNoteDTO request) {
        iMsgReadNoteService.addReadNote(request);
        return returnSuccess();
    }



}
