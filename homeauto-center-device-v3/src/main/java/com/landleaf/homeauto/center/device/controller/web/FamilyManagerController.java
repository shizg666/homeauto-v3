package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManageDetailVO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerDTO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerPageVO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerQryVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyManagerService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/web/family/manager")
@Api(description = "住户管理")
public class FamilyManagerController extends BaseController {

    @Autowired
    private IFamilyManagerService iFamilyManagerService;



    @ApiOperation(value = "住户管理分页查询", consumes = "application/json")
    @PostMapping(value = "/page")
    public Response<BasePageVO<FamilyManagerPageVO>> page(FamilyManagerQryVO familyManagerQryVO) {
        BasePageVO<FamilyManagerPageVO> vos = iFamilyManagerService.page(familyManagerQryVO);
        return returnSuccess(vos);
    }

    @ApiOperation(value = "添加住户")
    @PostMapping("/add")
    public Response addFamilyUser(@RequestBody FamilyManagerDTO familyManagerDTO){
        iFamilyManagerService.addFamilyUser(familyManagerDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "修改住户")
    @PostMapping("/update")
    public Response updateFamilyUser(@RequestBody FamilyManagerDTO familyManagerDTO){
        iFamilyManagerService.updateFamilyUser(familyManagerDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "删除住户")
    @DeleteMapping("/delete/{id}")
    public Response deleteFamilyUser(@PathVariable("id") Long id){
        iFamilyManagerService.deleteFamilyUser(id);
        return returnSuccess();
    }

    @ApiOperation(value = "住户详情")
    @PostMapping("/detail")
    public Response<FamilyManageDetailVO> getDetailFamilyUser(@RequestParam("id") Long id,@RequestParam("familyId") Long familyId,@RequestParam("userId") String userId,@RequestParam("type") Integer type){
        FamilyManageDetailVO result = iFamilyManagerService.getDetailFamilyUser(id,familyId,userId,type);
        return returnSuccess(result);
    }



}
