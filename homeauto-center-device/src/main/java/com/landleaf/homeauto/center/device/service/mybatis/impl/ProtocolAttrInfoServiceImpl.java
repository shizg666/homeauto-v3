package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.excel.importfamily.ImporFamilyResultVO;
import com.landleaf.homeauto.center.device.excel.importfamily.ImportProtocolModel;
import com.landleaf.homeauto.center.device.excel.importfamily.ProtocolImportDataListener;
import com.landleaf.homeauto.center.device.handle.excel.ProtocolSheetWriteHandler;
import com.landleaf.homeauto.center.device.model.domain.protocol.*;
import com.landleaf.homeauto.center.device.model.dto.protocol.*;
import com.landleaf.homeauto.center.device.model.excel.ProtocolAttrHeadData;
import com.landleaf.homeauto.center.device.model.excel.ProtocoltHeadData;
import com.landleaf.homeauto.center.device.model.mapper.ProtocolAttrInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.DeviceProtocolAttrQry;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagForAppVO;
import com.landleaf.homeauto.common.domain.vo.file.ProtocolFileVO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrValTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 协议属性信息 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProtocolAttrInfoServiceImpl extends ServiceImpl<ProtocolAttrInfoMapper, ProtocolAttrInfo> implements IProtocolAttrInfoService {

    public static final String FILE_NAME = "协议属性导入模板";
    public static final String[] OPERATE_ACL = {"r","w","r/w"};
    public static final String[] APP_SHOW = {"0-不需要","1-需要"};

    @Autowired
    private IProtocolAttrPrecisionService iProtocolAttrPrecisionService;
    @Autowired
    private IProtocolAttrBitService iProtocolAttrBitService;
    @Autowired
    private IProtocolAttrSelectService iProtocolAttrDetailService;
    @Autowired
    private IDicTagService iDicTagService;

    @Autowired
    private IProtocolInfoService iProtocolInfoService;

    @Autowired
    private CommonService commonService;



    public static final String ZERO_STR = "0";


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addProtocolAttr(ProtocolAttrInfoDTO requestData) {
        ProtocolAttrInfo protocolAttrInfo = BeanUtil.mapperBean(requestData,ProtocolAttrInfo.class);
        save(protocolAttrInfo);
        addAttrChildren(requestData,protocolAttrInfo.getId());
    }

    /**
     * 添加协议属性附属信息
     * @param requestData
     * @param attrId
     */
    private void addAttrChildren(ProtocolAttrInfoDTO requestData, String attrId) {
        if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(requestData.getValueType())){
            List<ProtocolAttrSelectDTO> detailList = requestData.getProtocolAttrDetail();
            detailList.forEach(obj->{
                obj.setAttrId(attrId);
            });
            iProtocolAttrDetailService.add(detailList);
        }else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(requestData.getValueType())){
            ProtocolAttrPrecisionDTO precisionDTO = requestData.getProtocolAttrPrecision();
            precisionDTO.setAttrId(attrId);
            iProtocolAttrPrecisionService.add(requestData.getProtocolAttrPrecision());
        }else {
            List<ProtocolAttrBitDTO> bitDTOS = requestData.getProtocolAttrBitDTO();
            bitDTOS.forEach(obj->{
                obj.setAttrId(attrId);
            });
            iProtocolAttrBitService.add(bitDTOS);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProtocolAttr(ProtocolAttrInfoDTO requestData) {
        ProtocolAttrInfo protocolAttrInfo = BeanUtil.mapperBean(requestData,ProtocolAttrInfo.class);
        updateById(protocolAttrInfo);
        removeAttrChildren(requestData.getValueType(),protocolAttrInfo.getId());
        addAttrChildren(requestData,requestData.getId());
    }

    private void removeAttrChildren(int valueType,String attrId) {
        if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(valueType)){
            iProtocolAttrDetailService.remove(new LambdaQueryWrapper<ProtocolAttrSelect>().eq(ProtocolAttrSelect::getAttrId,attrId));
        }else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(valueType)){
            iProtocolAttrPrecisionService.remove(new LambdaQueryWrapper<ProtocolAttrPrecision>().eq(ProtocolAttrPrecision::getAttrId,attrId));
        }else {
            iProtocolAttrBitService.remove(new LambdaQueryWrapper<ProtocolAttrBit>().eq(ProtocolAttrBit::getAttrId,attrId));
        }
    }

    @Override
    public void deleteProtocolAttrById(String attrId) {
        ProtocolAttrInfo protocolInfo = getById(attrId);
        if (protocolInfo == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性id不存在");
        }
        removeById(attrId);
        removeAttrChildren(protocolInfo.getValueType(),attrId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProtocolAttrByIds(List<String> attrIds) {
        List<ProtocolAttrInfo> protocolInfos = list(new LambdaQueryWrapper<ProtocolAttrInfo>().in(ProtocolAttrInfo::getId,attrIds).select(ProtocolAttrInfo::getValueType,ProtocolAttrInfo::getId));
        if (CollectionUtils.isEmpty(protocolInfos)){
            return;
        }
        removeByIds(attrIds);
        removeAttrListChildren(protocolInfos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProtocolAttrByProtocolId(String protocolId) {
        List<String> attrIds = this.baseMapper.getListAttrIdsByProtocolId(protocolId);
        if (CollectionUtils.isEmpty(attrIds)){
            return;
        }
        deleteProtocolAttrByIds(attrIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeAttrListChildren(List<ProtocolAttrInfo> protocolInfos) {
        List<String> selectIds = Lists.newArrayListWithExpectedSize(protocolInfos.size());
        List<String> bitIds = Lists.newArrayListWithExpectedSize(protocolInfos.size());
        List<String> precisionIds = Lists.newArrayListWithExpectedSize(protocolInfos.size());
        protocolInfos.forEach(obj->{
            if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(obj.getValueType())){
                selectIds.add(obj.getId());
            }else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(obj.getValueType())){
                precisionIds.add(obj.getId());
            }else {
                bitIds.add(obj.getId());
            }
        });
        if (!CollectionUtils.isEmpty(selectIds)){
            iProtocolAttrDetailService.remove(new LambdaQueryWrapper<ProtocolAttrSelect>().in(ProtocolAttrSelect::getAttrId,selectIds));
        }
        if (!CollectionUtils.isEmpty(precisionIds)){
            iProtocolAttrPrecisionService.remove(new LambdaQueryWrapper<ProtocolAttrPrecision>().in(ProtocolAttrPrecision::getAttrId,precisionIds));
        }
        if (!CollectionUtils.isEmpty(bitIds)){
            iProtocolAttrBitService.remove(new LambdaQueryWrapper<ProtocolAttrBit>().in(ProtocolAttrBit::getAttrId,bitIds));
        }

    }

    @Override
    public BasePageVO<ProtocolAttrInfoVO> getListProtocolAttr(ProtocolAttrQryInfoDTO requestData) {
        PageHelper.startPage(requestData.getPageNum(), requestData.getPageSize(), true);
        LambdaQueryWrapper<ProtocolAttrInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(requestData.getProtocolId())){
            wrapper.eq(ProtocolAttrInfo::getProtocolId,requestData.getProtocolId());
        }
        if (!StringUtil.isEmpty(requestData.getCode())){
            wrapper.like(ProtocolAttrInfo::getCode,requestData.getCode());
        }
        if (!StringUtil.isEmpty(requestData.getName())){
            wrapper.like(ProtocolAttrInfo::getName,requestData.getName());
        }
        if (!Objects.isNull(requestData.getCategory())){
            wrapper.eq(ProtocolAttrInfo::getCategory,requestData.getCategory());
        }
        if (!Objects.isNull(requestData.getType())){
            wrapper.eq(ProtocolAttrInfo::getType,requestData.getType());
        }
        wrapper.orderByAsc(ProtocolAttrInfo::getCode);
        List<ProtocolAttrInfo> list = list(wrapper);
        if (CollectionUtils.isEmpty(list)){
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        PageInfo pageInfo = new PageInfo(list);
        List<ProtocolAttrInfoVO> result = BeanUtil.mapperList(list,ProtocolAttrInfoVO.class);
        BasePageVO<ProtocolAttrInfoVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        resultData.setList(result);
        return resultData;
    }

    @Override
    public ProtocolAttrInfoDTO getDetail(String attrId) {
        ProtocolAttrInfo attrInfo = getById(attrId);
        if (Objects.isNull(attrInfo)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性id不存在");
        }
        ProtocolAttrInfoDTO result = BeanUtil.mapperBean(attrInfo, ProtocolAttrInfoDTO.class);
        if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(attrInfo.getValueType())){
            List<ProtocolAttrSelect> details = iProtocolAttrDetailService.list(new LambdaQueryWrapper<ProtocolAttrSelect>().eq(ProtocolAttrSelect::getAttrId,attrId));
            List<ProtocolAttrSelectDTO> detailDTOS = BeanUtil.mapperList(details, ProtocolAttrSelectDTO.class);
            result.setProtocolAttrDetail(detailDTOS);
        }else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(attrInfo.getValueType())){
            ProtocolAttrPrecision precisions = iProtocolAttrPrecisionService.getOne(new LambdaQueryWrapper<ProtocolAttrPrecision>().eq(ProtocolAttrPrecision::getAttrId,attrId));
            ProtocolAttrPrecisionDTO precisionDTO = BeanUtil.mapperBean(precisions,ProtocolAttrPrecisionDTO.class);
            result.setProtocolAttrPrecision(precisionDTO);
        }else {
            List<ProtocolAttrBit> bits = iProtocolAttrBitService.list(new LambdaQueryWrapper<ProtocolAttrBit>().eq(ProtocolAttrBit::getAttrId,attrId));
            List<ProtocolAttrBitDTO> bitDTOS = BeanUtil.mapperList(bits,ProtocolAttrBitDTO.class);
            result.setProtocolAttrBitDTO(bitDTOS);
        }
        return result;
    }

    @Override
    public void getdownLoadTemplate(HttpServletResponse response) {
        commonService.setResponseHeader(response,FILE_NAME);
        try {
            OutputStream os = response.getOutputStream();
            EasyExcel.write(os, ProtocoltHeadData.class).registerWriteHandler(new ProtocolSheetWriteHandler(getExportData())).sheet(1).doWrite(Lists.newArrayListWithCapacity(0));
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()),ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getMsg());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importTemplate(ProtocolFileVO request, HttpServletResponse response) {
        ProtocolDTO protocolDTO = BeanUtil.mapperBean(request,ProtocolDTO.class);
        ProtocolInfo protocolInfo = iProtocolInfoService.addProtocol(protocolDTO);
        ProtocolImportDataListener listener = new ProtocolImportDataListener(iProtocolInfoService, this);
        listener.setProtocolId(protocolInfo.getId());
        try {
            EasyExcel.read(request.getFile().getInputStream(), ImportProtocolModel.class, listener).sheet().doRead();
            if (CollectionUtils.isEmpty(listener.getErrorlist())) {
                Response result = new Response();
                result.setSuccess(true);
                result.setMessage("操作成功!");
                result.setResult(null);
                String resBody = JSON.toJSONString(result);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding("utf-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter printWriter = response.getWriter();
                printWriter.print(resBody);
                printWriter.flush();
                printWriter.close();
                return;
            }
            String fileName = "失败列表";
            commonService.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            List<ImporFamilyResultVO> familyResultVOS = BeanUtil.mapperList(listener.getErrorlist(), ImporFamilyResultVO.class);
            ExcelWriterBuilder writerBuilder = EasyExcel.write(os, ImporFamilyResultVO.class);
            writerBuilder.excelType(ExcelTypeEnum.XLSX);
            writerBuilder.autoCloseStream(true);
            writerBuilder.sheet("失败列表").doWrite(familyResultVOS);
        } catch (IOException e) {
            e.printStackTrace();
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), e.getMessage());
            Response result = new Response();
            result.setSuccess(false);
            result.setErrorCode(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()));
            result.setErrorMsg(e.getMessage());
            String resBody = JSON.toJSONString(result);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
            return;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ImportProtocolModel> importBatchAttr(List<ImportProtocolModel> dataList) {
        List<ImportProtocolModel> result = Lists.newArrayListWithExpectedSize(dataList.size());
        for (ImportProtocolModel data : dataList) {
            try {
                ProtocolAttrInfo protocolAttrInfo = ProtocolAttrInfo.builder().name(data.getName()).code(data.getCode()).operateAcl(data.getOperateAcl()).protocolId(data.getProtocolId()).type(Integer.valueOf(data.getType().split("-")[0])).valueType(Integer.valueOf(data.getValType().split("-")[0])).appFlag(Integer.valueOf(data.getAppFlag().split("-")[0])).category(data.getCategoryCode().split("-")[0]).build();
                protocolAttrInfo.setId(IdGeneratorUtil.getUUID32());
                data.setAttrId(protocolAttrInfo.getId());
                saveImportAttrChildren(data);
                save(protocolAttrInfo);
            } catch (BusinessException e) {
                e.printStackTrace();
                data.setError(e.getMessage());
                result.add(data);
            } catch (Exception e) {
                log.error("协议导入报错：行数:{} 属性名称：{}，原因：{}", data.getRow(), data.getName(), e.getMessage());
                e.printStackTrace();
                data.setError(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getMsg());
                result.add(data);
            }

        }
        return result;


    }



    @Override
    public List<ProtocolAttrInfoBO> getListProtocolAttrByDevice(DeviceProtocolAttrQry attrQry) {
        String attrCodePrex = attrQry.getProtocolCode().concat("_").concat(attrQry.getControlArea()).concat("_").concat(attrQry.getSn());
        List<ProtocolAttrInfoBO> protocolAttrInfoBOS = this.baseMapper.getListProtocolAttrByDevice(attrCodePrex,attrQry.getCategoryCode(),attrQry.getProtocolId());
        if (CollectionUtils.isEmpty(protocolAttrInfoBOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        Map<Integer,List<String>> data = Maps.newHashMapWithExpectedSize(8);
        protocolAttrInfoBOS.forEach(protocol->{
            if (data.containsKey(protocol.getValueType())){
                data.get(protocol.getValueType()).add(protocol.getId());
            }else {
                List<String> attrIds = Lists.newArrayListWithExpectedSize(protocolAttrInfoBOS.size());
                attrIds.add(protocol.getId());
                data.put(protocol.getValueType(),attrIds);
            }
        });
        Map<String,List<ProtocolAttrSelect>> selectMap = Maps.newHashMap();
        Map<String,List<ProtocolAttrPrecision>> precisionMap = Maps.newHashMap();
        Map<String,List<ProtocolAttrBit>> bitMap = Maps.newHashMap();

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            Integer valType = entry.getKey();
            List<String> attrIds = entry.getValue();
            if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(valType)) {
                List<ProtocolAttrSelect> selects = iProtocolAttrDetailService.list(new LambdaQueryWrapper<ProtocolAttrSelect>().in(ProtocolAttrSelect::getAttrId, attrIds));
                if (!CollectionUtils.isEmpty(selects)) {
                    selectMap = selects.stream().collect(Collectors.groupingBy(ProtocolAttrSelect::getAttrId));
                }
            } else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(valType)) {
                List<ProtocolAttrPrecision> precisions = iProtocolAttrPrecisionService.list(new LambdaQueryWrapper<ProtocolAttrPrecision>().in(ProtocolAttrPrecision::getAttrId, attrIds));
                if (!CollectionUtils.isEmpty(precisions)) {
                    precisionMap = precisions.stream().collect(Collectors.groupingBy(ProtocolAttrPrecision::getAttrId));
                }
            } else {
                List<ProtocolAttrBit> bits = iProtocolAttrBitService.list(new LambdaQueryWrapper<ProtocolAttrBit>().in(ProtocolAttrBit::getAttrId, attrIds));
                if (!CollectionUtils.isEmpty(bits)) {
                    bitMap = bits.stream().collect(Collectors.groupingBy(ProtocolAttrBit::getAttrId));
                }
            }
        }
        for (ProtocolAttrInfoBO protocol : protocolAttrInfoBOS) {
            if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(protocol.getValueType()) && precisionMap.containsKey(protocol.getId())) {
                protocol.setProtocolAttrPrecision(precisionMap.get(protocol.getId()).get(0));
            } else if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(protocol.getValueType()) && selectMap.containsKey(protocol.getId())) {
                protocol.setProtocolAttrDetail(selectMap.get(protocol.getId()));
            }else if (ProtocolAttrValTypeEnum.BIT.getCode().equals(protocol.getValueType()) && bitMap.containsKey(protocol.getId())){
                protocol.setProtocolAttrBitDTO(bitMap.get(protocol.getId()));
            }
        }
        return protocolAttrInfoBOS;
    }

    @Override
    public void exportProtocolAttrs(String protocolId, HttpServletResponse response) {
        ProtocolInfo protocolInfo = iProtocolInfoService.getById(protocolId);
        commonService.setResponseHeader(response,protocolInfo.getName());
        try {
            List<ProtocolAttrHeadData> data = Lists.newArrayListWithCapacity(0);
            List<ProtocolAttrInfo> attrInfos = list(new LambdaQueryWrapper<ProtocolAttrInfo>().eq(ProtocolAttrInfo::getProtocolId,protocolId));
            if (!CollectionUtils.isEmpty(attrInfos)){
                List<ProtocolAttrInfoVO> attrInfoVOS = BeanUtil.mapperList(attrInfos,ProtocolAttrInfoVO.class);
                data = BeanUtil.mapperList(attrInfoVOS,ProtocolAttrHeadData.class);
            }
            OutputStream os = response.getOutputStream();
            EasyExcel.write(os, ProtocolAttrHeadData.class).sheet(1).doWrite(data);
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()),ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getMsg());
        }
    }


    /**
     * 批量导入保存属性子信息
     * @param data
     */
    private void saveImportAttrChildren(ImportProtocolModel data) {
        Integer calType = Integer.valueOf(data.getValType().split("-")[0]);
       if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(calType)){
            ProtocolAttrPrecision precision = BeanUtil.mapperBean(data,ProtocolAttrPrecision.class);
            if (!StringUtil.isEmpty(precision.getUnit())){
                precision.setUnit(precision.getUnit().split("-")[0]);
            }
           iProtocolAttrPrecisionService.save(precision);
       }else if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(calType)){
           if (StringUtil.isEmpty(data.getVal())){
               return;
           }
           String[] vals = data.getVal().split(";");
           if (ArrayUtils.isEmpty(vals)){
               log.error("枚举属性值格式错误:{} ",data.getVal());
               return;
           }
           List<ProtocolAttrSelect> selects = Lists.newArrayListWithCapacity(vals.length);
           for (String val : vals) {
               String[] attrStr = val.split(":");
               if (ArrayUtils.isEmpty(attrStr) || attrStr.length != 2){
                   log.error("枚举属性值格式错误:{} ",val);
               }
               ProtocolAttrSelect select = new ProtocolAttrSelect();
               select.setAttrId(data.getAttrId());
               select.setName(attrStr[1]);
               select.setValue(attrStr[0]);
               selects.add(select);
           }
           iProtocolAttrDetailService.saveBatch(selects);
       }else if (ProtocolAttrValTypeEnum.BIT.getCode().equals(calType)){
           if (StringUtil.isEmpty(data.getVal())){
               return;
           }
           String[] vals = data.getVal().split(";");
           if (ArrayUtils.isEmpty(vals)){
               log.error("二进制属性值格式错误:{} ",data.getVal());
               return;
           }
           List<ProtocolAttrBit> bitDTOs = Lists.newArrayListWithCapacity(vals.length);
           for (String val : vals) {
               String[] bitStr = val.split("\\|");
               if (ArrayUtils.isEmpty(bitStr)){
                   log.error("二进制属性值格式错误:{} ",val);
               }
               ProtocolAttrBit attrBit = new ProtocolAttrBit();
               attrBit.setName(bitStr[0]);
               attrBit.setAttrId(data.getAttrId());
               for (int i = 0; i < bitStr.length; i++) {
                   if (i == 0){
                       continue;
                   }
                   String[] bits = bitStr[i].split(":");
                   if (ArrayUtils.isEmpty(bits) || bits.length != 2){
                       log.error("二进制属性值格式错误:{} ",val);
                   }

                   attrBit.setBitPos(i);
                   if (ZERO_STR.equals(bits[0])){
                       attrBit.setBit0(bits[1]);
                   }else {
                       attrBit.setBit1(bits[1]);
                   }
               }
               bitDTOs.add(attrBit);
           }
           iProtocolAttrBitService.saveBatch(bitDTOs);
       }
    }

    /**
     * 获取导入模板数据
     * @return
     */
    private Map<Integer, String[]> getExportData() {
        Map<Integer,String[]> data = Maps.newHashMapWithExpectedSize(12);

        String[] catrgorys = new String[CategoryTypeEnum.values().length];
        for (int i = 0; i < CategoryTypeEnum.values().length; i++) {
            catrgorys[i] = CategoryTypeEnum.values()[i].getType().concat("-").concat(CategoryTypeEnum.values()[i].getName());
        }
        data.put(2,catrgorys);

        String[] attrValTypes = new String[ProtocolAttrValTypeEnum.values().length];
        for (int i = 0; i < ProtocolAttrValTypeEnum.values().length; i++) {
            attrValTypes[i] = String.valueOf(ProtocolAttrValTypeEnum.values()[i].getCode()).concat("-").concat(ProtocolAttrValTypeEnum.values()[i].getName());
        }
        //属性值类型
        data.put(3,attrValTypes);

        data.put(4,OPERATE_ACL);


        String[] attrTypes = new String[ProtocolAttrTypeEnum.values().length];
        for (int i = 0; i < ProtocolAttrTypeEnum.values().length; i++) {
            attrTypes[i] = String.valueOf(ProtocolAttrTypeEnum.values()[i].getCode()).concat("-").concat(ProtocolAttrTypeEnum.values()[i].getName());
        }
        //属性类型
        data.put(5,attrTypes);

        //
        data.put(6,APP_SHOW);


        List<DicTagForAppVO> unitData = iDicTagService.getDicTagList("unit_type");
        if (!CollectionUtils.isEmpty(unitData)){
            String[] units = new String[unitData.size()];
            for (int i = 0; i < unitData.size(); i++) {
                units[i] = unitData.get(i).getValue().concat("-").concat(unitData.get(i).getLabel());
            }
            data.put(8,units);
        }
        //计算系数
        List<DicTagForAppVO> calData = iDicTagService.getDicTagList("multiply_factor");
        if (!CollectionUtils.isEmpty(calData)){
            String[] cals = new String[calData.size()];
            for (int i = 0; i < calData.size(); i++) {
                cals[i] = calData.get(i).getValue();
            }
            data.put(9,cals);
        }

        //精度
        List<DicTagForAppVO> precisionData = iDicTagService.getDicTagList("accuracy");
        if (!CollectionUtils.isEmpty(precisionData)){
            String[] precision = new String[precisionData.size()];
            for (int i = 0; i < precisionData.size(); i++) {
                precision[i] = precisionData.get(i).getValue();
            }
            data.put(10,precision);
        }
        //步长、
        List<DicTagForAppVO> stepData = iDicTagService.getDicTagList("step");
        if (!CollectionUtils.isEmpty(stepData)){
            String[] step = new String[stepData.size()];
            for (int i = 0; i < stepData.size(); i++) {
                step[i] = stepData.get(i).getValue();
            }
            data.put(11,step);
        }
        return data;
    }


}
