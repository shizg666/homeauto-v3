package com.landleaf.homeauto.center.websocket.feign;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Yujiumin
 * @version 2020/9/4
 */
@FeignClient(ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface FamilyFeignService {

    /**
     * 查询家庭是否存在
     *
     * @param familyId 家庭ID
     * @return 是否存在结果
     */
    @GetMapping("/device/feign/family/exist/{familyId}")
    Response<Boolean> familyExist(@PathVariable String familyId);
}
