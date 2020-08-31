package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.asyn.IFutureService;
import com.landleaf.homeauto.center.device.enums.ScreenApkUpdateStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ScreenApkUpdateSaveDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoScreenApkUpdateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.SCREEN_APK_UPDATE_DETAIL_NOT_EXIST_ERROR;

/**
 * <p>
 * 大屏apk更新记录 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Service
public class HomeAutoScreenApkUpdateServiceImpl extends ServiceImpl<HomeAutoScreenApkUpdateMapper, HomeAutoScreenApkUpdateDO> implements IHomeAutoScreenApkUpdateService {

    @Autowired
    private IHomeAutoScreenApkService homeAutoScreenApkService;
    @Autowired
    private IHomeAutoScreenApkUpdateDetailService homeAutoScreenApkUpdateDetailService;
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private IFutureService futureService;
    @Autowired
    private IHomeAutoProjectService homeAutoProjectService;


    /**
     * 保持推送记录
     * 1、工程信息
     * 2、网关信息
     * 3、大屏信息
     * 4、大屏绑定工程信息
     *
     * @param requestBody
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public HomeAutoScreenApkUpdateDO saveApkUpdate(ScreenApkUpdateSaveDTO requestBody) {
        String apkId = requestBody.getApkId();
        String path = requestBody.getPath();
        if (StringUtils.isEmpty(apkId) || StringUtils.isEmpty(path)) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
        }
        HomeAutoScreenApkDO apk = homeAutoScreenApkService.getById(apkId);
        if (apk == null) {
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_APK_NOT_EXIST_ERROR);
        }
        //截取path
        String[] split = path.split("/");
        List<String> familyIds = Lists.newArrayList();
        /*******************************获取基础数据**************************************************/
        if (split.length > 6) {
            familyIds.add(split[6]);
        } else {
            List<String> paths = Lists.newArrayList();
            paths.add(path);
            //根据path获取工程ID
            if (!CollectionUtils.isEmpty(paths)) {
                // TODO 根据path获取家庭集合
                // 暂时写死
                familyIds.add("1");
            }
        }
        if (CollectionUtils.isEmpty(familyIds)) {
            throw new BusinessException(ErrorCodeEnumConst.SCREEN_APK_UPDATE_PROJECT_NOT_FOUND_ERROR);
        }
        /**********************************************组装保存对象******************************/
        HomeAutoScreenApkUpdateDO saveApkUpdate = new HomeAutoScreenApkUpdateDO();
        saveApkUpdate.setApkId(requestBody.getApkId());
        saveApkUpdate.setPath(path);
        save(saveApkUpdate);
        List<HomeAutoScreenApkUpdateDetailDO> details = Lists.newArrayList();
        familyIds.stream().distinct();
        // 获取家庭所属项目楼盘
        Collection<HomeAutoFamilyDO> families = homeAutoFamilyService.listByIds(familyIds);
        Map<String, String> projectMap = families.stream().collect(Collectors.toMap(HomeAutoFamilyDO::getId, HomeAutoFamilyDO::getProjectId, (o, n) -> n));
        Map<String, String> realestateMap = families.stream().collect(Collectors.toMap(HomeAutoFamilyDO::getId, HomeAutoFamilyDO::getRealestateId, (o, n) -> n));

        for (String familyId : familyIds) {
            HomeAutoScreenApkUpdateDetailDO detail = new HomeAutoScreenApkUpdateDetailDO();
            detail.setFamilyId(familyId);
            detail.setApkId(apkId);
            detail.setApkName(apk.getName());
            detail.setApkVersion(apk.getVersionCode());
            detail.setApkUpdateId(saveApkUpdate.getId());
            detail.setStatus(ScreenApkUpdateStatusEnum.UPDATING.getType());
            detail.setProjectId(projectMap.get(familyId));
            detail.setRealestateId(realestateMap.get(familyId));
            detail.setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
            details.add(detail);
        }
        if (!CollectionUtils.isEmpty(details)) {
            /******************************将该家庭下历史推送记录中未成功的置为失败******************************/
            homeAutoScreenApkUpdateDetailService.updateHistoryUnSuccessRecordsToFail(familyIds);
            /******************************保存更新APK详情记录**********************************************/
            homeAutoScreenApkUpdateDetailService.saveBatch(details);
            /******************************异步发送通知推送到网关**********************************************/
            futureService.notifyApkUpdate(apk.getUrl(), details);
        }
        return saveApkUpdate;
    }

    @Override
    public void retrySave(String detailId) {
        HomeAutoScreenApkUpdateDetailDO detail = homeAutoScreenApkUpdateDetailService.getById(detailId);
        if (detail == null) {
            throw new BusinessException(SCREEN_APK_UPDATE_DETAIL_NOT_EXIST_ERROR);
        }
        String apkId = detail.getApkId();
        HomeAutoScreenApkDO apk = homeAutoScreenApkService.getById(apkId);
        List<HomeAutoScreenApkUpdateDetailDO> details = Lists.newArrayList();
        details.add(detail);
        List<String> familyIds = Lists.newArrayList();
        familyIds.add(detail.getFamilyId());
        /******************************将该家庭下历史推送记录中未成功的置为失败******************************/
        homeAutoScreenApkUpdateDetailService.updateHistoryUnSuccessRecordsToFail(familyIds);

        futureService.notifyApkUpdate(apk.getUrl(), details);
        HomeAutoScreenApkUpdateDetailDO updateData = new HomeAutoScreenApkUpdateDetailDO();
        BeanUtils.copyProperties(detail, updateData);
        updateData.setStatus(ScreenApkUpdateStatusEnum.UPDATING.getType());
        updateData.setResMsg(null);
        updateData.setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        homeAutoScreenApkUpdateDetailService.updateById(updateData);
    }

}
