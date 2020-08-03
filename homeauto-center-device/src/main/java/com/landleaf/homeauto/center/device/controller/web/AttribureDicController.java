package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttribureDicService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 属性字典表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/attribute-dic/")
public class AttribureDicController extends BaseController {

    @Autowired
    private IHomeAutoAttribureDicService iHomeAutoAttribureDicService;

    @PostMapping("add")
    public Response add(@RequestBody AttribureDicDTO request){
        iHomeAutoAttribureDicService.add(request);
        return returnSuccess();
    }

    @PostMapping("update/{id}")
    public Response add(@PathVariable("id") String attributeId){
        return returnSuccess();
    }

}
