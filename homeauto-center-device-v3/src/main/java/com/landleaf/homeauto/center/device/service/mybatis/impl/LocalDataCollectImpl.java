package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.landleaf.homeauto.center.device.enums.CloudSyncTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceLinkDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceValueDO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.remote.DataRemote;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.DeflaterUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class LocalDataCollectImpl implements ILocalDataCollectService {

    @Autowired
    private IHomeAutoFaultDeviceLinkService iHomeAutoFaultDeviceLinkService;
    @Autowired
    private IHomeAutoFaultDeviceHavcService iHomeAutoFaultDeviceHavcService;
    @Autowired
    private IHomeAutoFaultDeviceValueService iHomeAutoFaultDeviceValueService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DataRemote dataRemote;
    @Autowired
    private IFamilyDeviceInfoStatusService iFamilyDeviceInfoStatusService;
    @Autowired
    private IHomeAutoFaultDeviceCurrentService iHomeAutoFaultDeviceCurrentService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Override
    public void syncVollectData(SyncCloudDTO syncCloudDTO) {
        if (StringUtil.isEmpty(syncCloudDTO.getEncodeData())){
            return;
        }
        String data = DeflaterUtil.unzipString(syncCloudDTO.getEncodeData());
        log.info("解压后的数据***************************:{}",data);
      if (CloudSyncTypeEnum.FAMILY_DEVICE_STATUS_HISTORY.getType().equals(syncCloudDTO.getSyncType())){
          //设备状态历史数据
          dataRemote.syncDeviceStatusHistory(syncCloudDTO);

      }else if (CloudSyncTypeEnum.HOME_AUTO_FAULT_DEVICE_HAVC.getType().equals(syncCloudDTO.getSyncType())){
          //暖通故障历史数据
          List<HomeAutoFaultDeviceHavcDO> dataDos = JSON.parseArray(data, HomeAutoFaultDeviceHavcDO.class);
          iHomeAutoFaultDeviceHavcService.saveBatch(dataDos);
      }else if (CloudSyncTypeEnum.HOME_AUTO_FAULT_DEVICE_LINK.getType().equals(syncCloudDTO.getSyncType())){
          //设备通信故障历史数据
          List<HomeAutoFaultDeviceLinkDO> dataDos = JSON.parseArray(data, HomeAutoFaultDeviceLinkDO.class);
          iHomeAutoFaultDeviceLinkService.saveBatch(dataDos);
      }else if (CloudSyncTypeEnum.HOME_AUTO_FAULT_DEVICE_VALUE.getType().equals(syncCloudDTO.getSyncType())){
          //设备数值故障历史数据
          List<HomeAutoFaultDeviceValueDO> dataDos = JSON.parseArray(data, HomeAutoFaultDeviceValueDO.class);
          iHomeAutoFaultDeviceValueService.saveBatch(dataDos);
      }else if (CloudSyncTypeEnum.FAMILY_DEVICE_STATUS_CURRENT.getType().equals(syncCloudDTO.getSyncType())){
          //当前设备属性状态数据
          dataRemote.syncDeviceStatusCurrent(syncCloudDTO);
      } else if (CloudSyncTypeEnum.FAMILY_DEVICE_INFO_STATUS.getType().equals(syncCloudDTO.getSyncType())){
          //当前设备状态数据
          syncDeviceInfoStatus(syncCloudDTO.getRealestateId(),data);
      } else if (CloudSyncTypeEnum.HOME_AUTO_FAULT_DEVICE_CURRENT.getType().equals(syncCloudDTO.getSyncType())){
          //当前设备故障状态数据
          syncFaultDeviceCurrent(syncCloudDTO.getRealestateId(),data);
      }
    }

    private void syncDeviceInfoStatus(Long realestateId, String data) {
        String key = String.format(RedisCacheConst.LOCAL_DATA_SYNC,CloudSyncTypeEnum.FAMILY_DEVICE_INFO_STATUS.getType(),realestateId);
        if(redisUtils.getLock(key,
                50*60L)){
            //获取到锁 第一次 删除之前的数据
            List<Long> familyIds = iHomeAutoFamilyService.getListIdByRealestateId(realestateId);
            iFamilyDeviceInfoStatusService.remove(new LambdaQueryWrapper<FamilyDeviceInfoStatus>().in(FamilyDeviceInfoStatus::getFamilyId,familyIds));
        }
        List<FamilyDeviceInfoStatus> dataDos = JSON.parseArray(data, FamilyDeviceInfoStatus.class);
        iFamilyDeviceInfoStatusService.saveBatch(dataDos);
    }

    private void syncFaultDeviceCurrent(Long realestateId, String data) {
        String key = String.format(RedisCacheConst.LOCAL_DATA_SYNC,CloudSyncTypeEnum.HOME_AUTO_FAULT_DEVICE_CURRENT.getType(),realestateId);
        if(redisUtils.getLock(key,
                50*60L)){
            //获取到锁 第一批次 删除之前的数据
            iHomeAutoFaultDeviceCurrentService.remove(new LambdaQueryWrapper<HomeAutoFaultDeviceCurrent>().in(HomeAutoFaultDeviceCurrent::getRealestateId,realestateId));
        }
        List<HomeAutoFaultDeviceCurrent> dataDos = JSON.parseArray(data, HomeAutoFaultDeviceCurrent.class);
        iHomeAutoFaultDeviceCurrentService.saveBatch(dataDos);
    }

}
