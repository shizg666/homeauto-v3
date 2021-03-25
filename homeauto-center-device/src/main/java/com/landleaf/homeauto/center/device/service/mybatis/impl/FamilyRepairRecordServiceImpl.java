package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyRepairRecord;
import com.landleaf.homeauto.center.device.model.domain.FamilyRepairRecordEnclosure;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.*;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRepairRecordMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilyRepairRecordVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭维修记录 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Service
public class FamilyRepairRecordServiceImpl extends ServiceImpl<FamilyRepairRecordMapper, FamilyRepairRecord> implements IFamilyRepairRecordService {

    @Autowired
    private IHomeAutoProjectService homeAutoProjectService;
    @Autowired
    private IHomeAutoRealestateService homeAutoRealestateService;
    @Autowired
    private IFamilyRepairRecordEnclosureService repairRecordEnclosureService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IProjectHouseTemplateService houseTemplateService;
    @Autowired
    private IHouseTemplateDeviceService houseTemplateDeviceService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRecord(FamilyRepairRecordAddDTO requestDTO) {
       saveOrUpdate(requestDTO,false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRecord(FamilyRepairRecordUpdateDTO requestDTO) {
       saveOrUpdate(requestDTO,true);
    }

    @Override
    public FamilyRepairRecordVO detail(String id) {
        FamilyRepairRecord repairRecord = getById(id);
        return convertToVO(repairRecord);
    }

    @Override
    public BasePageVO<FamilyRepairRecordVO> pageList(FamilyRepairRecordPageRequestDTO requestDTO) {
        BasePageVO<FamilyRepairRecordVO> result = new BasePageVO<FamilyRepairRecordVO>();
        List<FamilyRepairRecordVO> data = Lists.newArrayList();
        PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize(), true);
        LambdaQueryWrapper<FamilyRepairRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(requestDTO.getDeviceName())) {
            queryWrapper.like(FamilyRepairRecord::getDeviceName, requestDTO.getDeviceName());
        }
        if (!StringUtils.isEmpty(requestDTO.getName())) {
            queryWrapper.like(FamilyRepairRecord::getName, requestDTO.getName());
        }
        if (!StringUtils.isEmpty(requestDTO.getFamilyName())) {
            queryWrapper.like(FamilyRepairRecord::getFamilyName, requestDTO.getFamilyName());
        }
        if (!StringUtils.isEmpty(requestDTO.getRealestateName())) {
            queryWrapper.like(FamilyRepairRecord::getRealestateName, requestDTO.getRealestateName());
        }
        if (!StringUtils.isEmpty(requestDTO.getProjectName())) {
            queryWrapper.like(FamilyRepairRecord::getProjectName, requestDTO.getProjectName());
        }
        if (!StringUtils.isEmpty(requestDTO.getProblem())) {
            queryWrapper.like(FamilyRepairRecord::getProblem, requestDTO.getProblem());
        }
        List<String> timeRang = requestDTO.getTimes();
        String startTime = null;
        String endTime = null;
        if (!org.springframework.util.CollectionUtils.isEmpty(timeRang) && timeRang.size() == 2) {
            startTime = timeRang.get(0);
            endTime = timeRang.get(1);
            queryWrapper.apply("(start_time>= TO_DATE('"+startTime +"','yyyy-mm-dd') and start_time<= TO_DATE('"+endTime +"','yyyy-mm-dd')) or " +
                    "(end_time>= TO_DATE('"+startTime +"','yyyy-mm-dd') and end_time<= TO_DATE('"+endTime +"','yyyy-mm-dd'))");
        }
        queryWrapper.orderByDesc(FamilyRepairRecord::getCreateTime);
        Page<SysRole> page = new Page<>();
        BeanUtils.copyProperties(requestDTO, page);
        List<FamilyRepairRecord> records = list(queryWrapper);
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
    public void delete(String id) {
        removeById(id);
    }

    private FamilyRepairRecordVO convertToVO(FamilyRepairRecord repairRecord) {
        FamilyRepairRecordVO vo = new FamilyRepairRecordVO();
        String id = repairRecord.getId();
        List<FamilyRepairRecordEnclosure> enclosures=repairRecordEnclosureService.getByRecordId(id);
        BeanUtils.copyProperties(repairRecord,vo);
        try {
            if(!StringUtils.isEmpty(repairRecord.getRealestateId())){
                vo.setRealestateAddress(homeAutoRealestateService.getById(repairRecord.getRealestateId()).getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!CollectionUtils.isEmpty(enclosures)){
            vo.setEnclosures(enclosures.stream().map(i->{
                FamilyRepairRecordEnclosureDTO enclosureDTO = new FamilyRepairRecordEnclosureDTO();
                BeanUtils.copyProperties(i,enclosureDTO);
                return enclosureDTO;
            }).collect(Collectors.toList()));
        }
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(FamilyRepairRecordAddOrUpdateBaseDTO requestDTO,boolean isUpdate) {
        FamilyRepairRecord record = new FamilyRepairRecord();
        if(isUpdate){
            FamilyRepairRecordUpdateDTO updateRequest = (FamilyRepairRecordUpdateDTO) requestDTO;
            record= getById(updateRequest.getId());
        }
        BeanUtils.copyProperties(requestDTO,record);
        buildOtherMessage(requestDTO,record);
        if(isUpdate){
            updateById(record);
            FamilyRepairRecord finalRecord = record;
            Supplier<QueryWrapper> supplier=()->{
                QueryWrapper<FamilyRepairRecordEnclosure> queryWrapper = new QueryWrapper<FamilyRepairRecordEnclosure>();
                queryWrapper.eq("family_repair_id", finalRecord.getId());
                return queryWrapper;
            } ;
            repairRecordEnclosureService.remove(supplier.get());
        }else {
            save(record);
        }
        List<FamilyRepairRecordEnclosureDTO> enclosures = requestDTO.getEnclosures();
        if(!CollectionUtil.isEmpty(enclosures)){
            FamilyRepairRecord finalRecord1 = record;
            repairRecordEnclosureService.saveBatch(enclosures.stream().map(i->{
                FamilyRepairRecordEnclosure enclosure = new FamilyRepairRecordEnclosure();
                enclosure.setFamilyRepairId(finalRecord1.getId());
                enclosure.setUrl(i.getUrl());
                enclosure.setFileName(i.getFileName());
                enclosure.setFileType(i.getFileType());
                return enclosure;
            }).collect(Collectors.toList()));
        }
    }


    private void buildOtherMessage(FamilyRepairRecordAddOrUpdateBaseDTO baseDTO,FamilyRepairRecord record) {
        String familyId = baseDTO.getFamilyId();
        String projectId = baseDTO.getProjectId();
        String realestateId = baseDTO.getRealestateId();
        record.setStartTime(LocalDateTimeUtil.parseStr2LocalDate(baseDTO.getStartTime(),"yyyy-MM-dd"));
        record.setEndTime(LocalDateTimeUtil.parseStr2LocalDate(baseDTO.getEndTime(),"yyyy-MM-dd"));
        try {
            if(!StringUtils.isEmpty(realestateId)){
                record.setRealestateName(homeAutoRealestateService.getById(realestateId).getName());
            }
            if(!StringUtils.isEmpty(projectId)) {
                record.setProjectName(homeAutoProjectService.getById(projectId).getName());
            }
            if(!StringUtils.isEmpty(familyId)) {
                HomeAutoFamilyDO familyDO = familyService.getById(familyId);
                record.setFamilyName(familyDO.getName());
                try {
                    record.setTemplateName(houseTemplateService.getById(familyDO.getTemplateId()).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
