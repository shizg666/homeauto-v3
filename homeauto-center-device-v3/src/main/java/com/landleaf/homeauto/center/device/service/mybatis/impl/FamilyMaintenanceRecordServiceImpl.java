package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.MaintenanceTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.maintenance.FamilyMaintenanceRecord;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenancePageRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceUpdateRequestDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyMaintenanceRecordMapper;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyMaintenanceRecordService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭维修记录 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-05-24
 */
@Service
public class FamilyMaintenanceRecordServiceImpl extends ServiceImpl<FamilyMaintenanceRecordMapper, FamilyMaintenanceRecord> implements IFamilyMaintenanceRecordService {

    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private IdService idService;

    @Override
    public BasePageVO<FamilyMaintenanceRecordVO> pageListMaintenanceRecord(FamilyMaintenancePageRequestDTO requestBody) {
        BasePageVO<FamilyMaintenanceRecordVO> result = new BasePageVO<FamilyMaintenanceRecordVO>();
        List<FamilyMaintenanceRecordVO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        // 根据locatePath查询家庭Ids TODO
        List<Long> familyIds = Lists.newArrayList();

        LambdaQueryWrapper<FamilyMaintenanceRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(requestBody.getOwner())) {
            queryWrapper.likeRight(FamilyMaintenanceRecord::getOwner, requestBody.getOwner());
        }
        List<String> timeRang = requestBody.getTimes();
        String startTime = null;
        String endTime = null;
        if (!org.springframework.util.CollectionUtils.isEmpty(timeRang) && timeRang.size() == 2) {
            startTime = timeRang.get(0);
            endTime = timeRang.get(1);
            queryWrapper.apply("(maintenance_time>= TO_DATE('" + startTime + "','yyyy-mm-dd') and maintenance_time<= TO_DATE('" + endTime + "','yyyy-mm-dd')) ");
        }
        if (!CollectionUtils.isEmpty(familyIds)) {
            queryWrapper.in(FamilyMaintenanceRecord::getFamilyId, familyIds);
        }
        queryWrapper.orderByDesc(FamilyMaintenanceRecord::getCreateTime);
        Page<FamilyMaintenanceRecord> page = new Page<>();
        BeanUtils.copyProperties(requestBody, page);
        List<FamilyMaintenanceRecord> records = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
        if (!org.springframework.util.CollectionUtils.isEmpty(records)) {
            data.addAll(records.stream().map(i -> {
                return convertToVO(i);
            }).collect(Collectors.toList()));
        }
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public FamilyMaintenanceRecordVO detail(Long id) {
        FamilyMaintenanceRecord maintenanceRecord = getById(id);
        if (maintenanceRecord != null) {
            return convertToVO(maintenanceRecord);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRecord(FamilyMaintenanceAddRequestDTO requestDTO) {
        // 获取id
        long segmentId = idService.getSegmentId(CommonConst.BIZ_CODE_HOMEAUTO_MAINTENANCE);
        FamilyMaintenanceRecord saveData = new FamilyMaintenanceRecord();
        BeanUtils.copyProperties(requestDTO,saveData);
        saveData.setNum(segmentId);
        save(saveData);
    }

    @Override
    public void updateRecord(FamilyMaintenanceUpdateRequestDTO requestDTO) {
        FamilyMaintenanceRecord exitRecord = getById(requestDTO.getId());
        if(exitRecord==null){
            throw new BusinessException(ErrorCodeEnumConst.MAINTENANCE_NOT_FOUND);
        }
        FamilyMaintenanceRecord updateData = new FamilyMaintenanceRecord();
        BeanUtils.copyProperties(requestDTO,updateData);
        updateById(updateData);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public List<FamilyMaintenanceRecordVO> listByFamily(Long familyId) {
        QueryWrapper<FamilyMaintenanceRecord> queryWrapper = new QueryWrapper<FamilyMaintenanceRecord>();
        queryWrapper.eq("family_id",familyId);
        List<FamilyMaintenanceRecord> list = list(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
           return list.stream().map(i->{
               return convertToVO(i);
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private FamilyMaintenanceRecordVO convertToVO(FamilyMaintenanceRecord record) {
        FamilyMaintenanceRecordVO vo = new FamilyMaintenanceRecordVO();
        BeanUtils.copyProperties(record, vo);
        /**
         * 转换类型
         * 获取家庭信息
         */
        HomeAutoFamilyDO familyDO = homeAutoFamilyService.getById(record.getFamilyId());
        if (familyDO != null) {
            vo.setBuildingCode(familyDO.getBuildingCode());
            vo.setUnitCode(familyDO.getUnitCode());
            vo.setFamilyNumber(familyDO.getRoomNo());
            vo.setLocatePath(familyDO.getPath2());
            vo.setMaintenanceTypeDsc(MaintenanceTypeEnum.getInstByType(record.getMaintenanceType()).getDesc());
        }
        return vo;
    }
}
