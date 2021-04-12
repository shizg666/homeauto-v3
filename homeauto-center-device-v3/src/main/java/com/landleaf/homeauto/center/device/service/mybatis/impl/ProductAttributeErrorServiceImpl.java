package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeErrorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品故障属性表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Service
public class ProductAttributeErrorServiceImpl extends ServiceImpl<ProductAttributeErrorMapper, ProductAttributeError> implements IProductAttributeErrorService {

    @Autowired
    private IProductAttributeErrorInfoService iProductAttributeErrorInfoService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IdService idService;

//    public static final String ERROR_CODE_SHOWISTR_2 = "枚举值：1-%s；2-%s";
//    public static final String ERROR_CODE_SHOWISTR_1 = "枚举值：1-%s";
    public static final String COMMUNICATE_SHOWISTR = "布尔值：0-正常；1-故障";
    public static final String VAKUE_SHOWISTR = "属性名称：%s；取值范围：%s~%s";

    @Override
    public List<Long> getIdListByProductId(Long id) {
        List<Long> data = this.baseMapper.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<String> getErrorInfo(String id) {
        return null;
    }

    @Override
    public AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductAttributeErrorDTO request) {
        addCheck(request);
        List<ProductAttributeErrorInfo> saveErrorInfoAttrs = Lists.newArrayList();
        ProductAttributeError attributeError = BeanUtil.mapperBean(request, ProductAttributeError.class);
        attributeError.setProductId(request.getProductId());
        attributeError.setId(idService.getSegmentId());
        save(attributeError);
        if (CollectionUtils.isEmpty(request.getInfos())) {
            //todo 缓存数据
//            cacheData(request);
            return;
        }
        List<ProductAttributeErrorInfoDTO> infos = request.getInfos();
        infos.forEach(errorInfo -> {
            ProductAttributeErrorInfo errorInfoObj = BeanUtil.mapperBean(errorInfo, ProductAttributeErrorInfo.class);
            errorInfoObj.setErrorAttributeId(attributeError.getId());
            saveErrorInfoAttrs.add(errorInfoObj);
        });
        iProductAttributeErrorInfoService.saveBatch(saveErrorInfoAttrs);
        //todo 缓存数据
//        cacheData(request);
    }

    /**
     * 数据缓存
     * @param request
     */
    private void cacheData(ProductAttributeErrorDTO request) {
    }


    private void addCheck(ProductAttributeErrorDTO request) {
        //todo
        int count = this.baseMapper.existErrorAttrCode(request.getCode(),request.getProductId());
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "错误码已存在");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductAttributeErrorDTO request) {
        ProductAttributeError error = getById(request.getId());
        if (error == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        deleteErrorAttribures(request.getId());
        //todo
        deleteCache(request,error);
        add(request);
    }

    /**
     * 删除缓存
     * @param request
     */
    private void deleteCache(ProductAttributeErrorDTO request, ProductAttributeError error) {
//        if (request == null){
//            String oldKey = String.format(RedisCacheConst.PRODUCT_ERROR_INFO,error.getProductCode(),error.getCode());
//            redisUtils.del(oldKey);
//            return;
//        }
//        if (!request.getCode().equals(error.getCode())){
//            String oldKey = String.format(RedisCacheConst.PRODUCT_ERROR_INFO,error.getProductCode(),error.getCode());
//            redisUtils.del(oldKey);
//        }else {
//            String oldKey = String.format(RedisCacheConst.PRODUCT_ERROR_INFO,request.getProductCode(),request.getCode());
//            redisUtils.del(oldKey);
//        }
    }


    /**
     * 产品查看详情之故障详情页
     * @param productId
     * @return
     */
    @Override
    public List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(Long productId) {
        List<ProductAttributeErrorVO> data = this.baseMapper.getListAttributesErrorsDeatil(productId);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithCapacity(0);
        }
        buildErrorInfoStr(data);
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteErrorAttrById(String attrId) {
        deleteErrorAttribures(attrId);
    }

    @Override
    public List<AttributePrecisionDTO> getAttributePrecision(AttributePrecisionQryDTO request) {

        if (StringUtil.isEmpty(request.getProductCode())){
            return Lists.newArrayListWithExpectedSize(0);
        }
        Map<String, JSONArray> data = (Map<String, JSONArray>) redisUtils.get(String.format(RedisCacheConst.PRODUCT_PRECISION_INFO,request.getProductCode()));

        if (!CollectionUtils.isEmpty(data)){
            if (StringUtil.isEmpty(request.getCode())){
                List<AttributePrecisionDTO> result = Lists.newArrayList();
                data.forEach((key,value)->{
                    String jsonStr = JSONObject.toJSONString(value);
                    result.addAll(JSONArray.parseArray(jsonStr, AttributePrecisionDTO.class));
                });
                return result;
            }else {
                String jsonStr = JSONObject.toJSONString(data.get(request.getCode()));
                return JSONArray.parseArray(jsonStr, AttributePrecisionDTO.class);
            }
        }
        List<AttributePrecisionDTO> result = this.baseMapper.getAttributePrecision(request);
        if (StringUtil.isEmpty(request.getCode())){
            saveCachePrecision(result,request.getProductCode());
        }else {
            saveCachePrecision(null,request.getProductCode());
        }
        return result;
    }

    @Override
    public void saveCachePrecision(List<AttributePrecisionDTO> dtos , String productCode) {
        List<AttributePrecisionDTO> data = dtos;
        if (CollectionUtils.isEmpty(dtos)){
            AttributePrecisionQryDTO request = new AttributePrecisionQryDTO();
            request.setProductCode(productCode);
            data = this.baseMapper.getAttributePrecision(request);
            if (CollectionUtils.isEmpty(data)){
                return;
            }
        }
        Map<String,List<AttributePrecisionDTO>> mapData = data.stream().collect(Collectors.groupingBy(AttributePrecisionDTO::getCode));
        String key = String.format(RedisCacheConst.PRODUCT_PRECISION_INFO,productCode);
        redisUtils.set(key, mapData);
    }

    @Override
    public List<ProductAttributeError> getByProductCode(String productCode) {
        QueryWrapper<ProductAttributeError> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code",productCode);
        return list(queryWrapper);
    }

    @Override
    public List<AttributeErrorDTO> getListCacheInfo() {
        return this.baseMapper.getListCacheInfo();
    }

    /**
     * 构建故障展示信息
     * @param data
     */
    private void buildErrorInfoStr(List<ProductAttributeErrorVO> data) {
        data.forEach(errorVO->{
            String str = "";
            errorVO.setTypeStr(AttributeErrorTypeEnum.getInstByType(errorVO.getType())!= null? AttributeErrorTypeEnum.getInstByType(errorVO.getType()).getName():"");
            if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(errorVO.getType())){
                if(!CollectionUtils.isEmpty(errorVO.getInfos())){
                    StringBuilder sb = new StringBuilder();
                    sb.append("枚举值:");
                    errorVO.getInfos().forEach(info->{
                        sb.append(info.getSortNo()).append("-").append(info.getVal()).append(";");
                    });
                    str = sb.toString();
                }
            }else if (AttributeErrorTypeEnum.VAKUE.getType().equals(errorVO.getType())){
                str = String.format(VAKUE_SHOWISTR,errorVO.getCodeName(),errorVO.getMin(),errorVO.getMax());
            }else {
                str = COMMUNICATE_SHOWISTR;
            }
            errorVO.setInfoStr(str);
        });
    }

    /**
     * 删除产品故障属性
     * @param attrId
     */
    private void deleteErrorAttribures(String attrId) {
        ProductAttributeError error = getById(attrId);
        if (error == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        this.removeById(attrId);

        deleteCache(null,error);
        //非暖通故障码的不需要删除子表
        if (!AttributeErrorTypeEnum.ERROR_CODE.getType().equals(error.getType())){
            return;
        }
        iProductAttributeErrorInfoService.remove(new LambdaQueryWrapper<ProductAttributeErrorInfo>().eq(ProductAttributeErrorInfo::getErrorAttributeId, attrId));
    }
}
