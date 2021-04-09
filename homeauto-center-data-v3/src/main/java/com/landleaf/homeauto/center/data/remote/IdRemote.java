package com.landleaf.homeauto.center.data.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_ID)
public interface IdRemote {

    @GetMapping(value = "/id-api/segment/get/{bizTag}")
    public Response<Long> getSegmentId(@PathVariable("bizTag") String bizTag);

    @GetMapping(value = "/id-api/segment/get/{bizTag}/{count}")
    public Response<List<Long>> getSegmentId(@PathVariable("bizTag") String bizTag,@PathVariable("count") String count);

}
