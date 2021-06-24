package com.landleaf.homeauto.center.device.remote;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.device.model.vo.device.HistoryQryDTO2;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DATA)
public interface DataRemote {


    @PostMapping("/status/history")
    @ApiOperation("获取家庭设备历史数据")
    Response<BasePageVO<FamilyDeviceStatusHistory>> getStatusHistory(@RequestBody HistoryQryDTO2 historyQryDTO);

}
