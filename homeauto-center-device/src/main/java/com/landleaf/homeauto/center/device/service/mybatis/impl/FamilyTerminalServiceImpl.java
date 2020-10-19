package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyTerminalMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalOperateVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.device.family.TerminalInfoDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 家庭终端表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyTerminalServiceImpl extends ServiceImpl<FamilyTerminalMapper, FamilyTerminalDO> implements IFamilyTerminalService {

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IFamilySceneService iFamilySceneService;


    @Autowired
    private RedisUtils redisUtils;

    public static final Integer MASTER_FLAG = 1;


    @Override
    public FamilyTerminalDO getMasterTerminal(String familyId) {
        QueryWrapper<FamilyTerminalDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        queryWrapper.eq("master_flag", 1);
        List<FamilyTerminalDO> result = list(queryWrapper);
        if(!CollectionUtils.isEmpty(result)){
            return result.get(0);
        }
        return null;
    }

    @Override
    public void add(FamilyTerminalVO request) {
        request.setMac(request.getMac().trim());
        addCheck(request);
        int count = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getFamilyId,request.getFamilyId()));
        if (count == 0){
            request.setMasterFlag(1);
        }else {
            request.setMasterFlag(0);
        }
        FamilyTerminalDO familyTerminalDO = BeanUtil.mapperBean(request,FamilyTerminalDO.class);
        save(familyTerminalDO);
        if (MASTER_FLAG.equals(request.getMasterFlag())){
            iFamilySceneService.getSyncInfo(request.getFamilyId());
        }
    }

    private void addCheck(FamilyTerminalVO request) {
        int count = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getName,request.getName()).eq(FamilyTerminalDO::getFamilyId,request.getFamilyId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关名称已存在");
        }
        int count2 = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getMac,request.getMac()));
        if (count2 >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关mac已存在");
        }
    }

    @Override
    public void update(FamilyTerminalVO request) {
        request.setMac(request.getMac().trim());
        updateCheck(request);
        FamilyTerminalDO familyTerminalDO = BeanUtil.mapperBean(request,FamilyTerminalDO.class);
        updateById(familyTerminalDO);
        FamilyTerminalDO terminalDO = getById(request.getId());
        if (MASTER_FLAG.equals(terminalDO.getMasterFlag())){
            iFamilySceneService.getSyncInfo(request.getFamilyId());
        }
    }

    private void updateCheck(FamilyTerminalVO request) {
        FamilyTerminalDO terminalDO = getById(request.getId());
        if (request.getName().equals(terminalDO.getName()) && request.getMac().equals(terminalDO.getMac()) ){
            return;
        }
        if (!request.getName().equals(terminalDO.getName())){
            int count = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getName,request.getName()).eq(FamilyTerminalDO::getFamilyId,request.getFamilyId()));
            if (count >0){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关名称已存在");
            }
        }
        if (!request.getMac().equals(terminalDO.getMac())){
            int count = count(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getMac,request.getMac()));
            if (count >0){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关mac已存在");
            }
        }
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iFamilyDeviceService.count(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getTerminalId,request.getId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "网关有设备存在");
        }
        removeById(request.getId());
    }

    @Override
    public List<SelectedVO> getTerminalSelects(String familyId) {
        return this.baseMapper.getTerminalSelects(familyId);
    }

    @Override
    public void switchMaster(FamilyTerminalOperateVO request) {
        String id = this.baseMapper.getMasterID(request.getFamilyId());
        FamilyTerminalDO terminalDO = new FamilyTerminalDO();
        terminalDO.setId(id);
        terminalDO.setMasterFlag(0);
        FamilyTerminalDO terminalDO2 = new FamilyTerminalDO();
        terminalDO2.setId(request.getId());
        terminalDO2.setMasterFlag(1);
        List<FamilyTerminalDO> list = Lists.newArrayListWithCapacity(2);
        list.add(terminalDO);
        list.add(terminalDO2);
        updateBatchById(list);
    }

    @Override
    public List<FamilyTerminalPageVO> getListByFamilyId(String familyId) {
        return this.baseMapper.getListByFamilyId(familyId);
    }

    @Override
    public TerminalInfoDTO getMasterMacByFamilyid(String familyId) {
        if (StringUtil.isEmpty(familyId)){
            return null;
        }
        String key = String.format(RedisCacheConst.FAMILY_ID_MAC,familyId);
        String str= (String) redisUtils.get(key);
        if (!StringUtil.isEmpty(str)){
            TerminalInfoDTO infoDTO = JSON.parseObject(str, TerminalInfoDTO.class);
            return infoDTO;
        }
        TerminalInfoDTO infoDTO = this.baseMapper.getMasterMacByFamilyid(familyId);
        if (infoDTO == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "家庭没配置主终端:{}",familyId);
        }
        redisUtils.set(key,JSON.toJSONString(infoDTO));
        return infoDTO;
    }
}
