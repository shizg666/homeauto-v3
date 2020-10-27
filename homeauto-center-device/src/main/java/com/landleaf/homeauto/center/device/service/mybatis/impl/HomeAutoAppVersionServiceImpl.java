package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.enums.PlatformTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAppVersionDO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionQry;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionSaveOrUpdateDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAppVersionMapper;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAppVersionService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
@Service
public class HomeAutoAppVersionServiceImpl extends ServiceImpl<HomeAutoAppVersionMapper, HomeAutoAppVersionDO> implements IHomeAutoAppVersionService {

    @Override
    public AppVersionDTO getCurrentVersion(Integer appType, String belongApp) {
        AppVersionDTO currentVersion = this.baseMapper.getCurrentVersion(appType, belongApp);
        if(currentVersion!=null){
            fillDesc(currentVersion);
        }
        return currentVersion;
    }


    @Override
    public PageInfo<AppVersionDTO> queryAppVersionDTOList(AppVersionQry appVersionQry) {
        PageHelper.startPage(appVersionQry.getPageNum(), appVersionQry.getPageSize());
        String belongApp = appVersionQry.getBelongApp();
        if(StringUtils.isEmpty(belongApp)){
            appVersionQry.setBelongApp(AppTypeEnum.SMART.getCode());
        }
        List<AppVersionDTO> appVersionDTOList = this.baseMapper.queryAppVersionDTOList(appVersionQry);
        appVersionDTOList.forEach(i -> fillDesc(i));
        return new PageInfo<>(appVersionDTOList);
    }

    @Override
    public AppVersionDTO queryAppVersionDTO(String id) {
        HomeAutoAppVersionDO appVersion = this.baseMapper.selectById(id);
        AppVersionDTO result = new AppVersionDTO();
        BeanUtils.copyProperties(appVersion, result);
        fillDesc(result);
        return result;
    }

    @Override
    public void saveAppVersion(AppVersionSaveOrUpdateDTO appVersionDTO) {
        this.prevCheck(appVersionDTO,false);
        HomeAutoAppVersionDO entity = new HomeAutoAppVersionDO();
        BeanUtils.copyProperties(appVersionDTO, entity);
        entity.setVersionTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        entity.setPushStatus(CommonConst.NumberConst.INT_FALSE);
        entity.setUploadUser(TokenContext.getToken().getUserName());
        this.save(entity);

    }

    @Override
    public void updateAppVersion(AppVersionSaveOrUpdateDTO appVersionDTO) {
        this.prevCheck(appVersionDTO, true);
        nonAllowUpdate(appVersionDTO.getId());
        HomeAutoAppVersionDO entity = new HomeAutoAppVersionDO();
        BeanUtils.copyProperties(appVersionDTO, entity);
        entity.setVersionTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        entity.setPushStatus(CommonConst.NumberConst.INT_FALSE);
        entity.setUploadUser(TokenContext.getToken().getUserName());
        this.updateById(entity);
    }


    @Override
    public void deleteAppVersion(String id) {
        nonAllowUpdate(id);
        this.removeById(id);
    }

    @Override
    public List<SelectedVO> getAppVersionsSelect(String belongApp) {
        List<AppVersionDTO> appVersionDTOList = this.baseMapper.getAppVersionsSelect(belongApp);
        return appVersionDTOList.stream()
                .map(a -> new SelectedVO(a.getVersion(), a.getVersion()))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePushStatus(String id) {
        nonAllowUpdate(id);
        new HomeAutoAppVersionDO() {{
            setId(id);
            setPushStatus(CommonConst.NumberConst.INT_TRUE);
            setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
            setCurrentFlag(CommonConst.NumberConst.INT_TRUE);
        }}.updateById();
        // 清除掉当前版本其它标记
        HomeAutoAppVersionDO curren = getById(id);
        if(curren!=null){
            String belongApp = curren.getBelongApp();
            Integer appType = curren.getAppType();
            UpdateWrapper<HomeAutoAppVersionDO> updateWrapper = new UpdateWrapper<HomeAutoAppVersionDO>();
            updateWrapper.eq("belong_app",belongApp);
            updateWrapper.eq("app_type",appType);
            updateWrapper.notIn("id",id);
            updateWrapper.set("current_flag",CommonConst.NumberConst.INT_FALSE);
            update(updateWrapper);
        }
    }



    private void prevCheck(AppVersionSaveOrUpdateDTO appVersionDTO, boolean isUpdate) {
        if (PlatformTypeEnum.ANDROID.getType().equals(appVersionDTO.getAppType())
                && StringUtils.isBlank(appVersionDTO.getUrl())) {
            //安卓平台没有附带url的
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "安卓平台更新版本,必须上传APK");
        }

        // 校验版本号是否唯一
        Predicate<AppVersionSaveOrUpdateDTO> versionUnique = t ->{
            String id = t.getId();
            String version = t.getVersion();
            String belongApp = t.getBelongApp();
            Integer appType = t.getAppType();
            QueryWrapper<HomeAutoAppVersionDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("version",version);
            queryWrapper.eq("belong_app",belongApp);
            queryWrapper.eq("app_type",appType);
            if(!StringUtil.isEmpty(id)){
                queryWrapper.notIn("id",id);
            }
          return   count(queryWrapper)>0;
        };
        if(versionUnique.test(appVersionDTO)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.APP_UPDATE_VERSION_EXIST.getCode()), "版本已存在！");
        }

    }


    public static void fillDesc(AppVersionDTO appVersionDTO) {
        appVersionDTO.setAppTypeDesc(PlatformTypeEnum.getPlatformTypeEnum(appVersionDTO.getAppType()).getDesc());
        appVersionDTO.setForceFlagDesc(CommonConst.NumberConst.INT_TRUE == appVersionDTO.getForceFlag() ? "是" : "否");
        appVersionDTO.setPushStatusDesc(CommonConst.NumberConst.INT_TRUE == appVersionDTO.getPushStatus() ? "已推送" : "未推送");
        appVersionDTO.setCurrentFlagDesc(appVersionDTO.getCurrentFlag()==null?"否":CommonConst.NumberConst.INT_TRUE == appVersionDTO.getCurrentFlag() ? "是" : "否");
    }

    private void nonAllowUpdate(String id) {
        HomeAutoAppVersionDO existVersion = getById(id);
        if (existVersion == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        Integer pushStatus = existVersion.getPushStatus();
        if (CommonConst.NumberConst.INT_TRUE == pushStatus.intValue()) {
            throw new BusinessException(ErrorCodeEnumConst.APP_VERSION_ALREADY_PUSH_ERROR);
        }

    }


}
