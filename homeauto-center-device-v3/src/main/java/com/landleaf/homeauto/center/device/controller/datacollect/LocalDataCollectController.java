package com.landleaf.homeauto.center.device.controller.datacollect;

import com.landleaf.homeauto.center.device.service.mybatis.ILocalDataCollectService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LocalDataCollectController
 * @Description: TODO
 * @Author shizg
 * @Date 2021/7/29
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/data-collect/")
@Api(value = "/web/category/", tags = {"品类接口"})
public class LocalDataCollectController extends BaseController {

    @Autowired
    private ILocalDataCollectService iLocalDataCollectService;

    @ApiOperation(value = "同步本地数采的数据", notes = "同步本地数采的数据")
    @PostMapping("sync-data")
    public Response syncLocalCollectData(@RequestBody SyncCloudDTO syncCloudDTO){
        iLocalDataCollectService.syncVollectData(syncCloudDTO);
        return returnSuccess();
    }
}
