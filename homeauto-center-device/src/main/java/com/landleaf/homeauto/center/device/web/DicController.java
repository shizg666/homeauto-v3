package com.landleaf.homeauto.center.device.web;


import com.landleaf.homeauto.center.device.service.dic.IDicService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.landleaf.homeauto.common.controller.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/device/dic")
public class DicController extends BaseController {

    @Autowired
    private IDicService dicService;

    @PostMapping("add")
    public Response<?> addDic(@RequestBody DicDTO dicDTO) {
        try {
            Integer integer = dicService.addDic(dicDTO);
            return returnSuccess(integer);
        } catch (Exception ex) {
            return handlerException(ex);
        }
    }

}
