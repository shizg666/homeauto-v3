package com.landleaf.homeauto.center.device.controller.web;

import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.enums.MsgReleaseStatusEnum;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeWebDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebQry;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebSaveDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebUpdateDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgNoticeService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.mqtt.annotation.ParamCheck;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description 消息公告模块
 * @Author zhanghongbin
 * @Date 2020/9/1 9:54
 */
@RestController
@RequestMapping("/web/msg/")
@Api(value = "/web/msg/", tags = {"消息公告模块"})
public class MsgWebController extends BaseController {

    @Autowired
    private IMsgNoticeService iMsgNoticeService;


    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;

    @ApiOperation(value = "新增消息")
    @PostMapping("save")
    @LogAnnotation(name = "新增消息公告")
    @ParamCheck({"name<=20:标题不能为空并且小于20个字",
            "content<=100:消息正常不能超过100个字"})
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    public Response saveApk(@RequestBody MsgWebSaveDTO requestBody) {
        iMsgNoticeService.saveMsgNotice(requestBody);
        return returnSuccess();
    }


    @ApiOperation("楼盘项目下拉列表")
    @PostMapping("/get/project")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    public Response getListCascadeSeclects() {
        List<CascadeVo> cascadeVos = iHomeAutoProjectService.getListCascadeSeclects();
        return returnSuccess(cascadeVos);
    }


    @ApiOperation("发布公告消息，此接口仅修改发布状态和发布时间、发布人")
    @PostMapping("/{id}")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    public Response<MsgNoticeWebDTO> get(@PathVariable("id") String id) throws Exception {
        iMsgNoticeService.releaseState(id, MsgReleaseStatusEnum.PUBLISHED.getType());
        return returnSuccess();
    }


    @ApiOperation(value = "修改公告信息")
    @PostMapping("update")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @LogAnnotation(name = "修改消息公告")
    @ParamCheck({"name<=20:标题不能为空并且小于20个字",
            "content<=100:消息正常不能超过100个字"})
    public Response updateApk(@RequestBody MsgWebUpdateDTO requestBody) {
        iMsgNoticeService.updateMsg(requestBody);
        return returnSuccess();
    }

    @ApiOperation("公告消息分页查询")
    @ParamCheck({"releaseFlag:发布标识不能为空"})
    @PostMapping("/list")
    public Response<BasePageVO<MsgNoticeWebDTO>> list(@RequestBody MsgWebQry msgWebQry) {
        PageInfo<MsgNoticeWebDTO> temp = iMsgNoticeService.queryMsgNoticeWebDTOList(msgWebQry);
        BasePageVO<MsgNoticeWebDTO> result = new BasePageVO<>(temp);
        return returnSuccess(result);
    }

    @ApiOperation(value = "删除消息公告")
    @PostMapping(value = "/delete")
    public Response deleteMsgByIds(@RequestBody List<String> ids) {
        iMsgNoticeService.deleteMsgByIds(ids);
        return returnSuccess();
    }



}
