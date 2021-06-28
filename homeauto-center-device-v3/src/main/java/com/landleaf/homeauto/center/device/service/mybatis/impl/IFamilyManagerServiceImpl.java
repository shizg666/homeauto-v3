package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.center.device.model.vo.BasePageVOFactory;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserPageVO;
import com.landleaf.homeauto.center.device.model.vo.familymanager.*;
import com.landleaf.homeauto.center.device.model.vo.statistics.*;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerBindFamilySaveReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class IFamilyManagerServiceImpl implements IFamilyManagerService {

    @Autowired(required = false)
    private UserRemote userRemote;
    @Autowired
    private IFamilyUserService iFamilyUserService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addFamilyUser(FamilyManagerDTO familyManagerDTO) {
        CustomerBindFamilySaveReqDTO customer = BeanUtil.mapperBean(familyManagerDTO,CustomerBindFamilySaveReqDTO.class);
        customer.setSex(familyManagerDTO.getGender());
        Response<CustomerInfoDTO> responseDTO = userRemote.bindFamilySaveOrUpdateCustomer(customer);
        if (Objects.isNull(responseDTO)|| !responseDTO.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getCode()),ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getMsg());
        }
        CustomerInfoDTO result = responseDTO.getResult();
        FamilyUserDTO familyUserDTO = BeanUtil.mapperBean(familyManagerDTO,FamilyUserDTO.class);
        familyUserDTO.setUserId(result.getId());
        if (FamilyUserTypeEnum.MADIN.getType().equals(familyManagerDTO.getType())){
            checkAdmain(familyManagerDTO.getFamilyId());
        }

        iFamilyUserService.addMember(familyUserDTO);
    }

    private void checkAdmain(Long familyId) {
        int count  = iFamilyUserService.count(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId,familyId).eq(FamilyUserDO::getType,FamilyUserTypeEnum.MADIN.getType()).last("limit 1"));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该家庭已有管理员！");
        }
    }

    @Override
    public BasePageVO<FamilyManagerPageVO> page(FamilyManagerQryVO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<String> paths = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getPaths())){
            String realestatedStr = String.valueOf(request.getRealestateId()).concat("/");
            paths = request.getPaths().stream().map(o->realestatedStr.concat(o)).collect(Collectors.toList());
        }else {
            paths.add(String.valueOf(request.getRealestateId()));
        }
        request.setPaths(paths);
        List<FamilyManagerPageVO> data = iFamilyUserService.getListFamilyManager(request);

        if (CollectionUtils.isEmpty(data)){
            return BasePageVOFactory.<ProductPageVO>getBasePage(Lists.newArrayListWithCapacity(0));
        }
        List<String> userIds = data.stream().map(FamilyManagerPageVO::getUserId).collect(Collectors.toList());
        Response<List<HomeAutoCustomerDTO>> customers = userRemote.getCustomerInfoByIds(userIds);
        if (Objects.isNull(customers)|| !customers.isSuccess()){
            return BasePageVOFactory.<FamilyManagerPageVO>getBasePage(data);
        }
        List<HomeAutoCustomerDTO> customerDTOS = customers.getResult();
        Map<String,List<HomeAutoCustomerDTO>> userMap = customerDTOS.stream().collect(Collectors.groupingBy(HomeAutoCustomerDTO::getId));
        data.forEach(familyUser->{
            if (!CollectionUtils.isEmpty(userMap) && !CollectionUtils.isEmpty(userMap.get(familyUser.getUserId()))){
                familyUser.setUserName(userMap.get(familyUser.getUserId()).get(0).getName());
                familyUser.setPhone(userMap.get(familyUser.getUserId()).get(0).getMobile());
                familyUser.setTypeStr(FamilyUserTypeEnum.MADIN.getName());
            }
        });
        return BasePageVOFactory.<FamilyManagerPageVO>getBasePage(data);
    }

    @Override
    public FamilyManageDetailVO getDetailFamilyUser(Long id,Long familyId,String userId,Integer type) {
        Response<List<HomeAutoCustomerDTO>> customerInfoByIds = userRemote.getCustomerInfoByIds(Arrays.asList(userId));
        if (Objects.isNull(customerInfoByIds)|| !customerInfoByIds.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getCode()),ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getMsg());
        }
        HomeAutoCustomerDTO customerDTO = customerInfoByIds.getResult().get(0);
        log.info("&&&&&&&&&&&&&&:{}", JSON.toJSONString(customerDTO));
        FamilyManageDetailVO detailVO = BeanUtil.mapperBean(customerDTO,FamilyManageDetailVO.class);
        detailVO.setGender(customerDTO.getSex());
        FamilyUserDO userDO = iFamilyUserService.getById(id);
        detailVO.setBindTime(userDO.getBindTime());
        detailVO.setValidTime(userDO.getValidTime());
        detailVO.setRemark(userDO.getRemark());
        if (FamilyUserTypeEnum.MADIN.getType().equals(type)){
            //用户关联的家庭信息
            List<FamilyManagerUserVO> familyManagers =iFamilyUserService.getListFamilyManagerByUid(userId);
            detailVO.setFamilys(familyManagers);
            //成员信息
            List<FamilyUserPageVO> listFamilyMember = iFamilyUserService.getListFamilyMember(familyId);
            detailVO.setMembers(listFamilyMember);
        }
        return detailVO;
    }

    @Override
    public void updateFamilyUser(FamilyManagerDTO familyManagerDTO) {
        CustomerBindFamilySaveReqDTO customer = BeanUtil.mapperBean(familyManagerDTO,CustomerBindFamilySaveReqDTO.class);
        customer.setSex(familyManagerDTO.getGender());
        Response<CustomerInfoDTO> responseDTO = userRemote.bindFamilySaveOrUpdateCustomer(customer);
        if (Objects.isNull(responseDTO)|| !responseDTO.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getCode()),ErrorCodeEnumConst.FENGIN_REMOTE_EXCEPTION.getMsg());
        }
        CustomerInfoDTO result = responseDTO.getResult();
        FamilyUserDTO familyUserDTO = BeanUtil.mapperBean(familyManagerDTO,FamilyUserDTO.class);
        //todo
        familyUserDTO.setUserId(result.getId());
        FamilyUserDO userDO = iFamilyUserService.getById(familyManagerDTO.getId());
        if (!familyManagerDTO.getFamilyId().equals(userDO.getFamilyId())){
            checkAdmain(familyManagerDTO.getFamilyId());
        }
        iFamilyUserService.updateMember(familyUserDTO);
    }

    @Override
    public void deleteFamilyUser(Long id) {
        iFamilyUserService.deleteById(id);
    }

    @Override
    public List<SelectedIntegerVO> getFamilyUserTypes() {
        List<SelectedIntegerVO> data = Arrays.stream(FamilyUserTypeEnum.values()).map(o->new SelectedIntegerVO(o.getName(),o.getType())).collect(Collectors.toList());
        return data;
    }
}
