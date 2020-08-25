package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.enums.PlatformTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAppVersionDO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionQry;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAppVersionMapper;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAppVersionService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        return this.baseMapper.getCurrentVersion(appType,belongApp);
    }


    @Override
    public PageInfo<AppVersionDTO> queryAppVersionDTOList(AppVersionQry appVersionQry) {
        PageHelper.startPage(appVersionQry.getPageNum(), appVersionQry.getPageSize());
        List<AppVersionDTO> appVersionDTOList = this.baseMapper.queryAppVersionDTOList(appVersionQry);
        appVersionDTOList.forEach(i->fillDesc(i));
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
    public void saveAppVersion(AppVersionDTO appVersionDTO) {
        this.prevCheck(appVersionDTO);
        HomeAutoAppVersionDO entity = new HomeAutoAppVersionDO();
        BeanUtils.copyProperties(appVersionDTO, entity);
        this.save(entity);

    }

    @Override
    public void updateAppVersion(AppVersionDTO appVersionDTO) {
        this.prevCheck(appVersionDTO);
        HomeAutoAppVersionDO entity = new HomeAutoAppVersionDO();
        BeanUtils.copyProperties(appVersionDTO, entity);
        entity.setVersionTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        this.updateById(entity);
    }

    @Override
    public void enableState(String id, Integer enableFlag) {
        new HomeAutoAppVersionDO(){{
            setId(id);
            setEnableFlag(enableFlag);
        }}.updateById();
    }

    @Override
    public void deleteAppVersion(String id) {
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
    public void updatePushStatus(String id, Integer pushStatus) {
        new HomeAutoAppVersionDO(){{
            setId(id);
            setPushStatus(pushStatus);
        }}.updateById();
    }


    private void prevCheck(AppVersionDTO appVersionDTO){
        if(PlatformTypeEnum.ANDROID.getType().equals(appVersionDTO.getAppType())
                && StringUtils.isBlank(appVersionDTO.getUrl())){
            //安卓平台没有附带url的
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "安卓平台更新版本,必须上传APK");
        }
    }


    public static void fillDesc(AppVersionDTO appVersionDTO){
        appVersionDTO.setAppTypeDesc(PlatformTypeEnum.getPlatformTypeEnum(appVersionDTO.getAppType()).getDesc());
        appVersionDTO.setForceFlagDesc(CommonConst.NumberConst.INT_TRUE==appVersionDTO.getForceFlag()?"是":"否");
        appVersionDTO.setEnableFlagDesc(CommonConst.NumberConst.INT_TRUE==appVersionDTO.getEnableFlag()?"启用中":"未启用");
        appVersionDTO.setPushStatusDesc(CommonConst.NumberConst.INT_TRUE==appVersionDTO.getPushStatus()?"已推送":"未推送");
    }

}
