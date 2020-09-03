package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyUserMapper;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.dto.device.family.familyUerRemoveDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 家庭组表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Service
public class FamilyUserServiceImpl extends ServiceImpl<FamilyUserMapper, FamilyUserDO> implements IFamilyUserService {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Override
    public void checkoutFamily(String userId, String familyId) {
        // 把当前用户的当前家庭设为常用家庭
        UpdateWrapper<FamilyUserDO> updateWrapperForCheck = new UpdateWrapper<>();
        updateWrapperForCheck.set("last_checked", 1);
        updateWrapperForCheck.eq("family_id", familyId);
        updateWrapperForCheck.eq("user_id", userId);
        update(updateWrapperForCheck);

        // 把当前用户的其他家庭设备设为不常用家庭
        UpdateWrapper<FamilyUserDO> updateWrapperForUncheck = new UpdateWrapper<>();
        updateWrapperForUncheck.set("last_checked", 0);
        updateWrapperForUncheck.notIn("family_id", familyId);
        updateWrapperForUncheck.eq("user_id", userId);
        update(updateWrapperForCheck);
    }

    @Override
    public boolean isFamilyExisted(String userId, String familyId) {
        QueryWrapper<FamilyUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("family_id", familyId);
        queryWrapper.last("limit 1");
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public List<String> getFamilyIdsByUserId(String userId) {
        List<String> data = this.baseMapper.getFamilyIdsByUserId(userId);
        return null;
    }

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public void deleteFamilyMember(FamiluserDeleteVO request) {
        this.checkAdmin(request.getFamilyId());
        removeById(request.getMemberId());
    }

    @Override
    public void checkAdmin(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        int count = this.baseMapper.checkAdmin(familyId,token.getUserId());
        if (count <= 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.PROJECT_UNAUTHORIZATION.getCode()), ErrorCodeEnumConst.PROJECT_UNAUTHORIZATION.getMsg());
        }
    }

    @Override
    public void quitFamily(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        this.checkAdmin(familyId);
        remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId,familyId).eq(FamilyUserDO::getUserId,token.getUserId()));
    }

    @Override
    public void addFamilyMember(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        int usercount = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId,familyId).eq(FamilyUserDO::getUserId,token.getUserId()));
        if (usercount > 0){
            return;
        }
        HomeAutoFamilyDO familyDO = iHomeAutoFamilyService.getById(familyId);
        if (familyDO == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"家庭id不存在");
        }
        int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId,familyId));
        FamilyUserDO familyUserDO = new FamilyUserDO();
        familyUserDO.setFamilyId(familyId);
        familyUserDO.setUserId(token.getUserId());
        if (count ==0){
            familyUserDO.setType(FamilyUserTypeEnum.MADIN.getType());
        }else {
            familyUserDO.setType(FamilyUserTypeEnum.MEMBER.getType());
        }
        //未已交付的是运维
        if (FamilyDeliveryStatusEnum.UNDELIVERY.getType().equals(familyDO.getDeliveryStatus())){
            familyUserDO.setType(FamilyUserTypeEnum.PROJECTADMIN.getType());
        }
        save(familyUserDO);
    }

    @Override
    public void deleteOperation(String familyId) {
        String operationId = this.baseMapper.getOperationer(familyId);
        if(StringUtil.isEmpty(operationId)){
            return;
        }
        removeById(operationId);
    }

    @Override
    public void removeUser(familyUerRemoveDTO request) {
        remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getUserId,request.getUserId()));;
    }

    @Override
    public List<SelectedVO> getMenberTypes() {
        return null;
    }

}
