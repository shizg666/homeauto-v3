package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoScreenApkMapper;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkUpdateService;
import com.landleaf.homeauto.common.constant.DateFormatConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;

/**
 * <p>
 * 大屏apk 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Service
public class HomeAutoScreenApkServiceImpl extends ServiceImpl<HomeAutoScreenApkMapper, HomeAutoScreenApkDO> implements IHomeAutoScreenApkService {

    @Autowired
    private IHomeAutoScreenApkUpdateService homeAutoScreenApkUpdateService;

    @Override
    public void saveApk(ScreenApkSaveDTO requestBody) {
        HomeAutoScreenApkDO params = new HomeAutoScreenApkDO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoScreenApkDO saveData = new HomeAutoScreenApkDO();
        BeanUtils.copyProperties(requestBody, saveData);
        try {
            saveData.setUploadUser(TokenContext.getToken().getUserName());
        } catch (Exception e) {
            log.error("新增应用版本，获取上传者名称异常", e);
        }
        saveData.setUploadTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        save(saveData);
    }

    @Override
    public void updateApk(ScreenApkDTO requestBody) {
        //已有推送记录，不能修改
        String id = requestBody.getId();

        int updateCount = homeAutoScreenApkUpdateService.count(new QueryWrapper<HomeAutoScreenApkUpdateDO>().eq("apk_id", id));
        if (updateCount > 0) {
            throw new BusinessException(SCREEN_APK_EXIST_UPDATE_ERROR);
        }
        HomeAutoScreenApkDO params = new HomeAutoScreenApkDO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, true)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoScreenApkDO updateData = new HomeAutoScreenApkDO();
        BeanUtils.copyProperties(requestBody, updateData);
        updateData.setUploadTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        try {
            updateData.setUploadUser(TokenContext.getToken().getUserName());
        } catch (Exception e) {
            log.error("修改应用版本，获取上传者名称异常", e);
        }
        updateById(updateData);
    }

    @Override
    public BasePageVO<ScreenApkResDTO> pageListApks(ScreenApkPageDTO requestBody) {
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        List<ScreenApkResDTO> data = Lists.newArrayList();
        QueryWrapper<HomeAutoScreenApkDO> queryWrapper = new QueryWrapper<>();
        String versionCode = requestBody.getVersionCode();
        String name = requestBody.getName();
        String uploadUser = requestBody.getUploadUser();
        List<String> uploadTimeRange = requestBody.getVersionTime();
        String startTime = null;
        String endTime = null;
        if (!CollectionUtils.isEmpty(uploadTimeRange) && uploadTimeRange.size() == 2) {
            startTime = uploadTimeRange.get(0);
            endTime = uploadTimeRange.get(1);
            queryWrapper.apply("upload_time>= TO_TIMESTAMP('"+startTime +"','yyyy-mm-dd hh24:mi:ss')");
            queryWrapper.apply("upload_time<= TO_TIMESTAMP('"+endTime +"','yyyy-mm-dd hh24:mi:ss')");
        }
        if (!StringUtils.isEmpty(versionCode)) {
            queryWrapper.eq("version_code", versionCode);
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!StringUtils.isEmpty(uploadUser)) {
            queryWrapper.eq("upload_user", uploadUser);
        }
        queryWrapper.orderByDesc("upload_time");
        List<HomeAutoScreenApkDO> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
                ScreenApkResDTO tmp = new ScreenApkResDTO();
                BeanUtils.copyProperties(i, tmp);
                return tmp;
            }).collect(Collectors.toList()));
        }
        PageInfo pageInfo = new PageInfo(data);
        BasePageVO<ScreenApkResDTO> result = new BasePageVO<>();
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public void deleteApkByIds(List<String> ids) {
        int updateCount = homeAutoScreenApkUpdateService.count(new QueryWrapper<HomeAutoScreenApkUpdateDO>().in("apk_id", ids));
        if (updateCount > 0) {
            throw new BusinessException(SCREEN_APK_EXIST_UPDATE_ERROR);
        }
        removeByIds(ids);
    }

    /**
     * 应用查询条件类型
     *
     * @return
     */
    @Override
    public ScreenApkConditionDTO getCondition() {
        ScreenApkConditionDTO data = new ScreenApkConditionDTO();
        String[] keys = new String[]{"versionCode", "uploadUser", "name"};
        Map<String, Set<String>> valueMap = Maps.newHashMap();
        for (String key : keys) {
            valueMap.put(key, Sets.newHashSet());
        }
        QueryWrapper<HomeAutoScreenApkDO> queryWrapper = new QueryWrapper<>();
        List<HomeAutoScreenApkDO> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            for (String valueKey : valueMap.keySet()) {
                for (HomeAutoScreenApkDO apk : queryResult) {
                    try {
                        Field declaredField = apk.getClass().getDeclaredField(valueKey);
                        declaredField.setAccessible(true);
                        String value = (String) declaredField.get(apk);
                        if (!StringUtils.isEmpty(value)) {
                            valueMap.get(valueKey).add(value);
                        }
                    } catch (Exception e) {
                        log.error("获取应用查询条件属性异常", e);
                    }
                }
            }
            for (String valueKey : valueMap.keySet()) {
                Set<String> values = valueMap.get(valueKey);
                Set<SelectedVO> maps = Sets.newHashSet();
                for (String value : values) {
                    SelectedVO map = new SelectedVO();
                    map.setLabel( value);
                    map.setValue( value);
                    maps.add(map);
                }
                if(org.apache.commons.lang3.StringUtils.equals("versionCode",valueKey)){
                    data.setVersionCode(maps);
                }else  if(org.apache.commons.lang3.StringUtils.equals("uploadUser",valueKey)){
                    data.setUploadUser(maps);
                }else  if(org.apache.commons.lang3.StringUtils.equals("name",valueKey)){
                    data.setName(maps);
                }
            }
        }
        return data;
    }

    @Override
    public ScreenApkResDTO getInfoById(String id) {
        ScreenApkResDTO result = new ScreenApkResDTO();
        HomeAutoScreenApkDO exist = getById(id);
        if(exist!=null){
            BeanUtils.copyProperties(exist,result);
        }
        return result;
    }


    private boolean saveOrUpdateValidParams(HomeAutoScreenApkDO params, boolean update) {
        if (params == null) {
            return false;
        }
        String id = params.getId();

        if (StringUtils.isEmpty(id) && update) {
            return false;
        }
        if (params.getUrl() == null ||
                StringUtils.isEmpty(params.getVersionCode())) {
            return false;
        }
        //校验同一应用名称下同一版本是否存在
        QueryWrapper<HomeAutoScreenApkDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version_code", params.getVersionCode());
        queryWrapper.eq("name", params.getName());
        if (update) {
            List<String> ids = Lists.newArrayList();
            ids.add(params.getId());
            queryWrapper.notIn("id", ids);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(SCREEN_APK_EXIST_ERROR);
        }
        return true;
    }

}
