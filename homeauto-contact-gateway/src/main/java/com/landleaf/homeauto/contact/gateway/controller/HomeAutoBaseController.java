package com.landleaf.homeauto.contact.gateway.controller;


import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.contact.gateway.HomeAutoBase;
import com.landleaf.homeauto.common.domain.vo.HomeAutoBaseVO;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.contact.gateway.service.IHomeAutoBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/contact-gateway/home-auto-base")
public class HomeAutoBaseController extends BaseController {

    @Autowired
    private IHomeAutoBaseService homeAutoBaseService;

    @PostMapping("/save")
    public Response save() {
        HomeAutoBase homeAutoBase = new HomeAutoBase();
        homeAutoBase.setName(String.valueOf(new Random().nextInt(1000)));
        homeAutoBaseService.save(homeAutoBase);
        return returnSuccess();
    }

    @PostMapping("/list")
    public Response list() {
        List<HomeAutoBaseVO> result = Lists.newArrayList();
        List<HomeAutoBase> list = homeAutoBaseService.list();
        if (!CollectionUtils.isEmpty(list)) {
            result.addAll(list.stream().map(i -> {
                HomeAutoBaseVO homeAutoBaseVO = new HomeAutoBaseVO();
                BeanUtils.copyProperties(i, homeAutoBaseVO);
                return homeAutoBaseVO;
            }).collect(Collectors.toList()));
        }
        return returnSuccess(result);
    }
}
