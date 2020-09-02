package com.landleaf.homeauto.center.device.remote;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zhanghongbin
 */
@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface ProjectRemote {


    @GetMapping("/web/project/get/filter/cascadeSeclects")
    @ApiOperation("楼盘项目下拉列表（根据用户权限过滤)")
    Response<List<CascadeVo>> getListCascadeSeclects();
}
