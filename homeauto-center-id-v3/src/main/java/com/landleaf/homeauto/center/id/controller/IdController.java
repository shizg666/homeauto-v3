package com.landleaf.homeauto.center.id.controller;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.id.common.enums.Status;
import com.landleaf.homeauto.center.id.common.model.Response;
import com.landleaf.homeauto.center.id.common.model.Result;
import com.landleaf.homeauto.center.id.service.impl.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 为各服务提供id
 *
 * @author wenyilu
 */
@RestController
@RequestMapping("/id-api")
public class IdController {

    @Autowired
    private SegmentService segmentService;

    @RequestMapping(value = "/segment/get/{bizTag}")
    public Response getSegmentId(@PathVariable("bizTag") String bizTag) {

        Result result;
        if (bizTag == null || bizTag.isEmpty()) {
            // TODO 空的
        }
        result = segmentService.getId(bizTag);
        if (result.getStatus().equals(Status.EXCEPTION)) {
            // TODO 异常
        }
        return returnSuccess(result.getId());
    }
    @RequestMapping(value = "/segment/get/{bizTag}/{count}")
    public Response getSegmentIds(@PathVariable("bizTag") String bizTag, @PathVariable("count") int count) {
        List<Long> ids = Lists.newArrayListWithCapacity(count);
        Result result;
        for (int i = 0; i < count; i++) {
            result = segmentService.getId(bizTag);
            ids.add(result.getId());
        }
        return returnSuccess(ids);
    }


    /**
     * @param object
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 请求成功带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public <T> Response<T> returnSuccess(T object) {
        return returnSuccess(object, "操作成功");
    }

    /**
     * @param object
     * @param successMsg
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 带成功提示和返回参数的结果
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public <T> Response<T> returnSuccess(T object, String successMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setMessage(successMsg);
        response.setResult(object);
        return response;
    }

}
