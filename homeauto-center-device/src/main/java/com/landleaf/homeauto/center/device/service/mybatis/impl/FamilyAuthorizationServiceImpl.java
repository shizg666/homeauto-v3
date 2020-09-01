package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyAuthorization;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyAuthorizationMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyAuthorizationService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.core.date.DateTime.now;

/**
 * <p>
 * 家庭授权记录表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
@Service
public class FamilyAuthorizationServiceImpl extends ServiceImpl<FamilyAuthorizationMapper, FamilyAuthorization> implements IFamilyAuthorizationService {

    @Autowired
    private IFamilyUserService iFamilyUserService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Override
    public void authorization(String familyId) {
        iFamilyUserService.checkAdmin(familyId);
        addcheck(familyId);
        FamilyAuthorization data = new FamilyAuthorization();
        LocalDateTime endTime = LocalDateTimeUtil.plus(LocalDateTime.now(),2, ChronoUnit.DAYS);
        data.setEndTime(endTime);
        data.setExecuteFlag(0);
        data.setFamilyId(familyId);
        save(data);
        HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
        familyDO.setId(familyId);
        familyDO.setReviewStatus(FamilyReviewStatusEnum.AUTHORIZATION.getType());
        iHomeAutoFamilyService.updateById(familyDO);
        //todo 发授权消息
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("bussnessExecutor")
    public void timingScan(String now) {
        int count = this.baseMapper.getExpiredData(now);
        if (count <= 0){
            return;
        }
        List<FamilyAuthorization> familyAuthorizationBOS = this.baseMapper.getListExpiredData(now());
        List<HomeAutoFamilyDO> familyDOS = familyAuthorizationBOS.stream().map(o->{
            HomeAutoFamilyDO project = new HomeAutoFamilyDO();
            project.setId(o.getFamilyId());
            project.setReviewStatus(FamilyReviewStatusEnum.REVIEW.getType());
            project.setReviewTime(LocalDateTime.now());
            o.setExecuteFlag(1);
            return project;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(familyDOS)){
         return;
        }
        iHomeAutoFamilyService.updateBatchById(familyDOS);
        updateBatchById(familyAuthorizationBOS);
    }

    private void addcheck(String familyId) {
        int count = count(new LambdaQueryWrapper<FamilyAuthorization>().eq(FamilyAuthorization::getFamilyId,familyId).eq(FamilyAuthorization::getExecuteFlag,0));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.PROJECT_AUTHORIZATION.getCode()), ErrorCodeEnumConst.PROJECT_AUTHORIZATION.getMsg());
        }
    }
}
