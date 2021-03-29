package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.enums.ScreenApkUpdateStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoScreenApkUpdateDetailMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkUpdateDetailService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 大屏apk更新记录详情 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Service
public class HomeAutoScreenApkUpdateDetailServiceImpl extends ServiceImpl<HomeAutoScreenApkUpdateDetailMapper, HomeAutoScreenApkUpdateDetailDO> implements IHomeAutoScreenApkUpdateDetailService {

    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private IHomeAutoScreenApkService homeAutoScreenApkService;

    @Override
    public void updateHistoryUnSuccessRecordsToFail(List<String> familyIds) {
        this.baseMapper.updateHistoryUnSuccessRecordsToFail(familyIds);
    }

    @Override
    public BasePageVO<ApkUpdateDetailResDTO> pageListApkUpdateDetail(ApkUpdateDetailPageDTO requestBody) {
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        List<ApkUpdateDetailResDTO> data = Lists.newArrayList();
        List<String> createTime = requestBody.getCreateTime();
        String startTime = null;
        String endTime = null;
        if (!CollectionUtils.isEmpty(createTime) && createTime.size() == 2) {
            startTime = createTime.get(0);
            endTime = createTime.get(1);
        }
        List<ApkUpdateDetailResDTO> queryResult = this.baseMapper.getHistoryDetails(requestBody.getApkName(),
                requestBody.getProjectId(),requestBody.getRealestateId(),requestBody.getVersionCode(),startTime,endTime);
        if (!CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
                ApkUpdateDetailResDTO tmp = new ApkUpdateDetailResDTO();
                BeanUtils.copyProperties(i, tmp);
                tmp.setStatusName(ScreenApkUpdateStatusEnum.getInstByType(tmp.getStatus()).getName());
                StringBuilder s = new StringBuilder();
                s.append(tmp.getPathName()).append(tmp.getRoomNo()).append("室");
                String address = s.toString();
                tmp.setPathName(address);
                return tmp;
            }).collect(Collectors.toList()));
        }
        PageInfo pageInfo = new PageInfo(data);
        BasePageVO<ApkUpdateDetailResDTO> result = new BasePageVO<>();
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public void updateResponseSuccess(String id) {
        HomeAutoScreenApkUpdateDetailDO updateData = new HomeAutoScreenApkUpdateDetailDO();
        updateData.setId(id);
        updateData.setResTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        updateData.setStatus(ScreenApkUpdateStatusEnum.SUCCESS.getType());
        updateData.setResMsg("成功");
        updateById(updateData);
    }

    @Override
    public void updateResponseFail(String id, String errorMsg) {
        HomeAutoScreenApkUpdateDetailDO updateData = new HomeAutoScreenApkUpdateDetailDO();
        updateData.setId(id);
        updateData.setResTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        updateData.setStatus(ScreenApkUpdateStatusEnum.FAIL.getType());
        if (!StringUtils.isEmpty(errorMsg) && errorMsg.length() > 200) {
            errorMsg = errorMsg.substring(0, 200);
        }
        updateData.setResMsg(errorMsg);
        updateById(updateData);
    }

    @Override
    public ApkPushingResDTO pushingDetails(String apkId) {
        ApkPushingResDTO result = new ApkPushingResDTO();
        List<ApkPushingDetailResDTO> data = Lists.newArrayList();
        if (StringUtils.isEmpty(apkId)) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
        }
        //未成功推送记录
        List<HomeAutoScreenApkUpdateDetailDO> queryResult = getNotSuccessDetail(apkId);
        if (!CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
                ApkPushingDetailResDTO tmp = new ApkPushingDetailResDTO();
                BeanUtils.copyProperties(i, tmp);
                tmp.setFamilyId(i.getFamilyId());
                return tmp;
            }).collect(Collectors.toList()));
            //获取工程信息
            List<String> familyIds = queryResult.stream().map(i -> i.getFamilyId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(familyIds)) {
                Collection<HomeAutoFamilyDO> families = homeAutoFamilyService.listByIds(familyIds);
                Map<String, HomeAutoFamilyDO> familyMap = Maps.newHashMap();
                if (!CollectionUtils.isEmpty(families)) {
                    familyMap = families.stream().collect(Collectors.toMap(HomeAutoFamilyDO::getId, i -> {
                        return i;
                    }));
                }
                for (ApkPushingDetailResDTO dto : data) {
                    dto.setStatusName(ScreenApkUpdateStatusEnum.getInstByType(dto.getStatus()).getName());
                    String familyId = dto.getFamilyId();
                    if (!StringUtils.isEmpty(familyId)) {
                        HomeAutoFamilyDO tmp = familyMap.get(familyId);
                        if(tmp==null){
                            continue;
                        }
                        StringBuilder s = new StringBuilder();
                        s.append(tmp.getPathName()).append(tmp.getRoomNo()).append("室");
                        String address = s.toString();
                        tmp.setPathName(address);
                        dto.setPathName(tmp.getPathName());
                        dto.setFamilyName(tmp.getName());
                        dto.setFamilyCode(tmp.getCode());
                    }
                }
            }
        }
        result.setNotSuccessCount(queryResult.size());
        result.setTotalCount(count(new QueryWrapper<HomeAutoScreenApkUpdateDetailDO>().eq("apk_id", apkId)));
        result.setPushingDetails(data);
        return result;
    }

    @Override
    public ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {

        String version = adapterHttpApkVersionCheckDTO.getVersion();
        String familyId = adapterHttpApkVersionCheckDTO.getFamilyId();
        if (org.apache.commons.lang3.StringUtils.isEmpty(version) || org.apache.commons.lang3.StringUtils.isEmpty(familyId)) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
        }

        ScreenHttpApkVersionCheckResponseDTO result = new ScreenHttpApkVersionCheckResponseDTO();
        result.setVersion(version);
        result.setUpdateFlag(false);

        HomeAutoScreenApkUpdateDetailDO current = getFamilyCurrentVersion(familyId);
        if (current == null) {
            return result;
        }
        ScreenApkResDTO info = homeAutoScreenApkService.getInfoById(current.getApkId());
        if(info==null){
            return result;
        }

        if (!org.apache.commons.lang3.StringUtils.equals(version, info.getVersionCode())) {
            result.setUpdateFlag(true);
            result.setVersion(info.getVersionCode());
            result.setUrl(info.getUrl());
            return result;
        }
        //更新为成功
        updateResponseSuccess(current.getId());
        return result;
    }

    /**
     * 获取家庭当前推送的apk版本记录
     *
     * @param familyId
     * @return
     */
    public HomeAutoScreenApkUpdateDetailDO getFamilyCurrentVersion(String familyId) {
        return this.baseMapper.getFamilyCurrentVersion(familyId);
    }
    /**
     * 根据应用Id获取未成功推送记录
     *
     * @param apkId
     * @return
     */
    private List<HomeAutoScreenApkUpdateDetailDO> getNotSuccessDetail(String apkId) {

        QueryWrapper<HomeAutoScreenApkUpdateDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apk_id", apkId);
        List<Integer> statusList = Lists.newArrayList();
        statusList.add(ScreenApkUpdateStatusEnum.FAIL.getType());
        statusList.add(ScreenApkUpdateStatusEnum.UPDATING.getType());
        queryWrapper.in("status", statusList);
        return list(queryWrapper);
    }
}
