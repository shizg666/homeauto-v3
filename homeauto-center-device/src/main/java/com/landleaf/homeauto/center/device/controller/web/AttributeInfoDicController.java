package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 属性值字典表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/attribute-info/")
public class AttributeInfoDicController extends BaseController {


    @PostMapping("add")
    public Response add(@RequestBody AttribureDicDTO request){

        return returnSuccess();
    }

}
