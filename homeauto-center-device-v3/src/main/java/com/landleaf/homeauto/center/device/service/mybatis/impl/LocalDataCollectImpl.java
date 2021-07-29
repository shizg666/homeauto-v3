package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.enums.CloudSyncTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.CommonService;
import com.landleaf.homeauto.center.device.service.mybatis.ILocalCollectExtranetUrlConfigService;
import com.landleaf.homeauto.center.device.service.mybatis.ILocalDataCollectService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.DeflaterUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 本地数采 服务类
 * </p>
 *
 * @author shizg
 */
@Service
public class LocalDataCollectImpl implements ILocalDataCollectService {


    @Override
    public void syncVollectData(SyncCloudDTO syncCloudDTO) {
        if (StringUtil.isEmpty(syncCloudDTO.getEncodeData())){
            return;
        }
        String data = DeflaterUtil.unzipString(syncCloudDTO.getEncodeData());
      if (CloudSyncTypeEnum.FAMILY_DEVICE_STATUS_HISTORY.getType().equals(syncCloudDTO.getSyncType())){
          JSON.parseArray(data, FamilyDeviceStatusHistory.class)
      }
    }
}
