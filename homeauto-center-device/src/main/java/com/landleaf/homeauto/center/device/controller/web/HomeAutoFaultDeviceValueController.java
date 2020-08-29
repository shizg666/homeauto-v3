package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 数值故障控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/fault/value")
@Api(value = "/fault/value", tags = {"数值故障请求接口"})
public class HomeAutoFaultDeviceValueController extends BaseController {

}
