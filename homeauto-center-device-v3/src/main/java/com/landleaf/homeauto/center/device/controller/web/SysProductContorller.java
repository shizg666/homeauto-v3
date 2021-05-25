package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysProductContorller
 * @Description: TODO
 * @Author shizg
 * @Date 2021/5/24
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/sys-produt/")
@Api(value = "/web/sys-produt/", tags = {"系统产品配置"})
public class SysProductContorller extends BaseController {
    @Autowired
    private ISysProductService iSysProductService;

    @ApiOperation(value = "新增系统产品")
    @PostMapping("add")
    public Response addSysProdut(@RequestBody SysProductDTO requestDTO){
        iSysProductService.addSysProduct(requestDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "修改系统产品")
    @PostMapping("update")
    public Response updateSysProdut(@RequestBody SysProductDTO requestDTO){
        iSysProductService.updateSysProdut(requestDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "查看系统产品")
    @GetMapping("detail/{sysProductId}")
    public Response<SysProductDetailVO> detailSysProdut(@PathVariable("sysProductId") Long sysProductId){
        SysProductDetailVO productDetailVO = iSysProductService.getDetailSysProdut(sysProductId);
        return returnSuccess(productDetailVO);
    }

    @ApiOperation(value = "根据系统产品id删除系统产品")
    @DeleteMapping("delete/{sysProductId}")
    public Response deleteSysProdut(@PathVariable("sysProductId") Long sysProductId){
        iSysProductService.deleteSysProdutById(sysProductId);
        return returnSuccess();
    }
}
