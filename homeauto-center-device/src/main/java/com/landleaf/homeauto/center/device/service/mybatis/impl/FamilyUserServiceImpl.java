package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyUserMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluseAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.IJSMSService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserCheckoutService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.family.familyUerRemoveDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭组表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Slf4j
@Service
public class FamilyUserServiceImpl extends ServiceImpl<FamilyUserMapper, FamilyUserDO> implements IFamilyUserService {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private UserRemote userRemote;

    @Autowired
    private IJSMSService ijsmsService;

    @Autowired
    private IFamilyUserCheckoutService iFamilyUserCheckoutService;

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFamilyMember(FamiluserDeleteVO request) {

        FamilyUserDO familyUserDO = getById(request.getMemberId());
        if (familyUserDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        this.checkAdmin(request.getFamilyId());
        this.deleteById(request.getMemberId());
        List<String> ids = Lists.newArrayList();
        ids.add(familyUserDO.getUserId());
        iFamilyUserCheckoutService.deleteFamilyUserNote(request.getFamilyId(), familyUserDO.getUserId());
        userRemote.unbindFamilyNotice(ids);
//        sendMessage(familyDO,familyUserDO.getUserId());
    }

    @Override
    public void checkAdmin(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        if (String.valueOf(UserTypeEnum.WEB.getType()).equals(token.getUserType())){
            return;
        }
        int count = this.baseMapper.checkAdmin(familyId, token.getUserId());
        if (count <= 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.PROJECT_UNAUTHORIZATION.getCode()), ErrorCodeEnumConst.PROJECT_UNAUTHORIZATION.getMsg());
        }
    }

    @Override
    public boolean checkAdminReturn(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        int count = this.baseMapper.checkAdmin(familyId, token.getUserId());
        if (count <= 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void quitFamily(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        if (this.checkAdminReturn(familyId)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.PROJECT_UNAUTHORIZATION.getCode()), "管理员不可操作");
        }

        HomeAutoFamilyDO familyDO = iHomeAutoFamilyService.getById(familyId);
        if (familyDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyId).eq(FamilyUserDO::getUserId, token.getUserId()));
        iFamilyUserCheckoutService.deleteFamilyUserNote(familyId, token.getUserId());
        List<String> ids = Lists.newArrayList();
        ids.add(token.getUserId());
        userRemote.unbindFamilyNotice(ids);
    }

    @Override
    public void addFamilyMember(FamiluseAddDTO request) {
        String familyId = "";
        if ("1".equals(request.getType())){
            familyId = request.getFamily();
        }else {
            familyId = iHomeAutoFamilyService.getFamilyIdByMac(request.getFamily());
        }
       this.addFamilyMemberById(familyId);
    }

    @Override
    public void addFamilyMember(String familyId) {
        String [] path = familyId.split(":");
        if (path.length != 2){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_DATA_EXIST.getCode()), "格式不对");
        }
        String family = path[1];
        String type = path[0];
        if ("1".equals(type)){
            familyId = family;
        }else {
            familyId = iHomeAutoFamilyService.getFamilyIdByMac(family);
        }
        addFamilyMemberById(familyId);

    }

    private void addFamilyMemberById(String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        int usercount = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyId).eq(FamilyUserDO::getUserId, token.getUserId()).last("limit 1"));
        if (usercount > 0) {
            return;
        }
        HomeAutoFamilyDO familyDO = iHomeAutoFamilyService.getById(familyId);
        if (familyDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "家庭id不存在");
        }
//        int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyId).eq(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType()));
        String adminUserId = this.baseMapper.getAdminMobileByFamilyId(familyId);
        FamilyUserDO familyUserDO = new FamilyUserDO();
        familyUserDO.setFamilyId(familyId);
        familyUserDO.setUserId(token.getUserId());

        if (StringUtil.isEmpty(adminUserId) && FamilyDeliveryStatusEnum.DELIVERY.getType().equals(familyDO.getDeliveryStatus())) {
            familyUserDO.setType(FamilyUserTypeEnum.MADIN.getType());
        } else {
            familyUserDO.setType(FamilyUserTypeEnum.MEMBER.getType());
        }
        //第二次判判断
        int usercount2 = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyId).eq(FamilyUserDO::getUserId, token.getUserId()).last("limit 1"));
        if (usercount2 > 0) {
            return;
        }
        save(familyUserDO);
        sendMessage(familyDO, token.getUserId(),adminUserId);
        userRemote.bindFamilyNotice(token.getUserId(), familyId);

    }

    /**
     * 发送短信通知
     *
     * @param familyDO
     * @param userId
     * @param touserId
     */
    private void sendMessage(HomeAutoFamilyDO familyDO, String userId,String touserId) {
        List<String> userIds = Lists.newArrayListWithCapacity(2);
        userIds.add(userId);
        userIds.add(touserId);
        Response<List<HomeAutoCustomerDTO>> customerInfoRes = userRemote.getCustomerInfoByIds(userIds);
        if (!customerInfoRes.isSuccess() && customerInfoRes.getResult() == null) {
            log.error("sendMessage-----绑定家庭获取用户信息失败:{}", userIds);
        }
        List<HomeAutoCustomerDTO> data = customerInfoRes.getResult();
        if (CollectionUtils.isEmpty(data)){
            log.error("sendMessage-----绑定家庭获取用户信息失败:{}", userIds);
        }
        Map<String,List<HomeAutoCustomerDTO>> map = data.stream().collect(Collectors.groupingBy(HomeAutoCustomerDTO::getId));
        ijsmsService.groupAddUser(familyDO.getName(), map.get(userId).get(0).getName(), map.get(userId).get(0).getMobile(),map.get(touserId).get(0).getMobile());
    }


    @Override
    public void deleteOperation(String familyId) {
        String operationId = this.baseMapper.getOperationer(familyId);
        if (StringUtil.isEmpty(operationId)) {
            return;
        }
        removeById(operationId);
    }

    @Override
    public void removeUser(familyUerRemoveDTO request) {
        if (checkAdminByUser(request.getUserId())) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_DATA_EXIST.getCode()), "该用户是管理员不可注销");
        }
        remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getUserId, request.getUserId()));
    }

    @Override
    public List<SelectedIntegerVO> getMenberTypes() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (FamilyUserTypeEnum value : FamilyUserTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(FamilyUserDTO request) {
        isExsit(request);
        int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getFamilyId()));
        if (count == 0) {
            HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
            familyDO.setId(request.getFamilyId());
            familyDO.setActiveTime(LocalDateTime.now());
            iHomeAutoFamilyService.updateById(familyDO);
        }
        FamilyUserDO familyUserDO = BeanUtil.mapperBean(request, FamilyUserDO.class);
        save(familyUserDO);
        userRemote.bindFamilyNotice(request.getUserId(), request.getFamilyId());
    }

    @Override
    public void deleteById(String id) {
        FamilyUserDO familyUserDO = getById(id);
        if (familyUserDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_DATA_EXIST.getCode()), "根据id查询不到信息");
        }
        if (FamilyUserTypeEnum.MADIN.getType().intValue() == familyUserDO.getType()) {
            int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyUserDO.getFamilyId()));
            if (count > 1) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.PROJECT_DELTET_FALSE.getCode()), ErrorCodeEnumConst.PROJECT_DELTET_FALSE.getMsg());
            }
        }
        removeById(id);
        List<String> ids = Lists.newArrayList();
        ids.add(familyUserDO.getUserId());
        userRemote.unbindFamilyNotice(ids);
    }

    @Override
    public void settingAdmin(FamilyUserOperateDTO request) {
        checkAdmin(request.getFamilyId());
        List<FamilyUserDO> familyUserDOS = Lists.newArrayList();
        FamilyUserDO familyUserDO1 = new FamilyUserDO();
        familyUserDO1.setId(request.getId());
        familyUserDO1.setType(FamilyUserTypeEnum.MADIN.getType());
        familyUserDOS.add(familyUserDO1);
        FamilyUserDO familyUserDO = getOne(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getFamilyId()).eq(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType()));
        if (familyUserDO != null) {
            familyUserDO.setType(FamilyUserTypeEnum.MEMBER.getType());
            familyUserDOS.add(familyUserDO);
        }
        updateBatchById(familyUserDOS);
    }

    @Override
    public List<FamilyUserPageVO> getListFamilyMember(String familyId) {

        List<FamilyUserDO> familyUserDOS = list(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, familyId).orderByDesc(FamilyUserDO::getCreateTime));
        List<FamilyUserPageVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(familyUserDOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> userIds = familyUserDOS.stream().map(FamilyUserDO::getUserId).collect(Collectors.toList());

        Response<List<HomeAutoCustomerDTO>> response = userRemote.getListByIds(userIds);
        if (!response.isSuccess()) {
            log.error("getListFamilyMember --->getListByIds 获取用户信息失败");
            return Lists.newArrayListWithCapacity(0);
        }
        List<HomeAutoCustomerDTO> customerDTOS = response.getResult();
        if (CollectionUtils.isEmpty(customerDTOS)) {
            log.error("getListFamilyMember --->getListByIds 获取用户信息为空");
            return Lists.newArrayListWithCapacity(0);
        }
        Map<String, List<HomeAutoCustomerDTO>> collect = customerDTOS.stream().collect(Collectors.groupingBy(HomeAutoCustomerDTO::getId));
        familyUserDOS.forEach(o -> {
            FamilyUserPageVO responseVO = new FamilyUserPageVO();
            responseVO.setId(o.getId());
            responseVO.setType(o.getType());
            FamilyUserTypeEnum userTypeEnum = FamilyUserTypeEnum.getInstByType(o.getType());
            responseVO.setTypeStr(userTypeEnum == null ? "" : userTypeEnum.getName());
            List<HomeAutoCustomerDTO> list = collect.get(o.getUserId());
            if (!CollectionUtils.isEmpty(list)) {
                HomeAutoCustomerDTO customerDTO = list.get(0);
                responseVO.setName(customerDTO == null ? "" : customerDTO.getName());
                responseVO.setMobile(customerDTO == null ? "" : customerDTO.getMobile());
            }
            result.add(responseVO);
        });
        return result;
    }

    @Override
    public Boolean checkAdminByUser(String userId) {
        int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getUserId, userId).eq(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType()).last("limit 1"));
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<FamilyUserDO> listByUserId(String userId) {
        QueryWrapper<FamilyUserDO> familyUserDOQueryWrapper = new QueryWrapper<>();
        familyUserDOQueryWrapper.eq("user_id", userId);
        return list(familyUserDOQueryWrapper);
    }

    private void isExsit(FamilyUserDTO request) {
        HomeAutoFamilyDO familyDO = iHomeAutoFamilyService.getById(request.getFamilyId());
        if (familyDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "家庭id不存在");
        }
        int count = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getFamilyId()).eq(FamilyUserDO::getUserId, request.getUserId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_ALREADY_EXISTS.getCode()), "该用户已绑定");
        }
        if (FamilyUserTypeEnum.MADIN.getType().equals(request.getType())) {
            int count2 = count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getFamilyId()).eq(FamilyUserDO::getType, FamilyUserTypeEnum.MADIN.getType()));
            if (count2 > 0) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_ALREADY_EXISTS.getCode()), "该家庭已有管理员");
            }
        }
        if (FamilyDeliveryStatusEnum.DELIVERY.getType().equals(familyDO.getDeliveryStatus()) && FamilyUserTypeEnum.PROJECTADMIN.getType().equals(request.getType())) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_ALREADY_EXISTS.getCode()), "交付状态下不可添加运维人员");
        }
    }

}
