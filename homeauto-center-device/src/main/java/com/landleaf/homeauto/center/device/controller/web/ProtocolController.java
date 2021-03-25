package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.dto.protocol.*;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolInfoService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.file.ProtocolFileVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName ProtocolController
 * @Description: TODO
 * @Author shizg
 * @Date 2020/12/25
 * @Version V1.0
 **/
@Controller
@RequestMapping("/web/protocol/")
@Api(value = "/web/protocol/", tags = {"协议配置接口"})
public class ProtocolController extends BaseController {
    @Autowired
    private IProtocolInfoService iProtocolInfoService;

    @Autowired
    private IProtocolAttrInfoService iProtocolAttrInfoService;

    @ApiOperation("获取协议导入模板")
    @GetMapping("/get-template")
    public void downLoadTemplate(HttpServletResponse response) {
        iProtocolAttrInfoService.getdownLoadTemplate(response);
    }

    @ApiOperation("导入协议")
    @PostMapping("/import")
    public void importTemplate(ProtocolFileVO request, HttpServletResponse response) {
        iProtocolAttrInfoService.importTemplate(request,response);
    }

    @ApiOperation("导出协议属性")
    @PostMapping("/export/{protocolId}")
    public Response exportProtocolAttrs(@PathVariable("protocolId") String protocolId, HttpServletResponse response) {
        iProtocolAttrInfoService.exportProtocolAttrs(protocolId,response);
        return returnSuccess();
    }

    @ApiOperation(value = "新增协议", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @ResponseBody
    public Response addProtocol(@RequestBody ProtocolDTO requestData){
        iProtocolInfoService.addProtocol(requestData);
        return returnSuccess();
    }

    @ApiOperation(value = "查询协议", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list")
    @ResponseBody
    public Response<BasePageVO<ProtocolVO>> page(ProtocolQryDTO request){
        BasePageVO<ProtocolVO> protocolVO = iProtocolInfoService.getListProtocol(request);
        return returnSuccess(protocolVO);
    }

    @ApiOperation(value = "修改协议", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @ResponseBody
    public Response updateProtocol(@RequestBody ProtocolDTO requestData){
        iProtocolInfoService.updateProtocol(requestData);
        return returnSuccess();
    }

    @ApiOperation(value = "删除协议", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @DeleteMapping("delete/{protocolId}")
    @ResponseBody
    public Response deleteProtocolById(@PathVariable("protocolId") String protocolId){
        iProtocolInfoService.deleteProtocolById(protocolId);
        return returnSuccess();
    }


    @ApiOperation(value = "新增字段", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("attr/add")
    @ResponseBody
    public Response addProtocolAttr(@RequestBody ProtocolAttrInfoDTO requestData){
        iProtocolAttrInfoService.addProtocolAttr(requestData);
        return returnSuccess();
    }

    @ApiOperation(value = "修改字段", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("attr/update")
    @ResponseBody
    public Response updateProtocolAttr(@RequestBody ProtocolAttrInfoDTO requestData){
        iProtocolAttrInfoService.updateProtocolAttr(requestData);
        return returnSuccess();
    }

    @ApiOperation(value = "删除字段", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @DeleteMapping("attr/delete/{attrId}")
    @ResponseBody
    public Response deleteProtocolAttr(@PathVariable("attrId") String attrId){
        iProtocolAttrInfoService.deleteProtocolAttrById(attrId);
        return returnSuccess();
    }


    @ApiOperation(value = "查询字段", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("attr/list")
    @ResponseBody
    public Response<BasePageVO<ProtocolAttrInfoVO>> getListProtocolAttr(ProtocolAttrQryInfoDTO requestData){
        BasePageVO<ProtocolAttrInfoVO> result = iProtocolAttrInfoService.getListProtocolAttr(requestData);
        return returnSuccess(result);
    }

    @ApiOperation(value = "字段详情", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("attr/detail/{attrId}")
    @ResponseBody
    public Response<ProtocolAttrInfoDTO> getProtocolAttrDetail(@PathVariable("attrId") String attrId){
        ProtocolAttrInfoDTO result = iProtocolAttrInfoService.getDetail(attrId);
        return returnSuccess(result);
    }


}
