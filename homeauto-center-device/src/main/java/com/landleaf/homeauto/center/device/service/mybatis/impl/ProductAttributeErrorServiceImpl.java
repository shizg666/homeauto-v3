package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeErrorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

//    public static final String ERROR_CODE_SHOWISTR_2 = "枚举值：1-%s；2-%s";
//    public static final String ERROR_CODE_SHOWISTR_1 = "枚举值：1-%s";
    public static final String COMMUNICATE_SHOWISTR = "布尔值：0-正常；1-故障";
    public static final String VAKUE_SHOWISTR = "属性名称：%s；取值范围：%s~%s";

    @Override
    public List<String> getIdListByProductId(String id) {
        List<String> data = this.baseMapper.getIdListByProductId(id);
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
        AttributeErrorDTO errorDTO = this.baseMapper.getErrorAttributeInfo(request);
        if (errorDTO == null){
            return null;
        }
        if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(errorDTO.getType()) || AttributeErrorTypeEnum.VAKUE.getType().equals(errorDTO.getType())){
            return errorDTO;
        }
        List<String> desc = iProductAttributeErrorInfoService.getListDesc(errorDTO.getId());
        errorDTO.setDesc(desc);
        return errorDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductAttributeErrorDTO request) {
        addCheck(request);
        List<ProductAttributeErrorInfo> saveErrorInfoAttrs = Lists.newArrayList();
        ProductAttributeError attributeError = BeanUtil.mapperBean(request, ProductAttributeError.class);
        attributeError.setProductId(request.getProductId());
        attributeError.setId(IdGeneratorUtil.getUUID32());
        save(attributeError);
        if (CollectionUtils.isEmpty(request.getInfos())) {
            return;
        }
        List<ProductAttributeErrorInfoDTO> infos = request.getInfos();
        infos.forEach(errorInfo -> {
            ProductAttributeErrorInfo errorInfoObj = BeanUtil.mapperBean(errorInfo, ProductAttributeErrorInfo.class);
            errorInfoObj.setErrorAttributeId(attributeError.getId());
            saveErrorInfoAttrs.add(errorInfoObj);
        });
        iProductAttributeErrorInfoService.saveBatch(saveErrorInfoAttrs);
        
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
        deleteErrorAttribures(request.getId());
        add(request);
    }


    /**
     * 产品查看详情之故障详情页
     * @param productId
     * @return
     */
    @Override
    public List<ProductAttributeErrorVO> getListAttributesErrorsDeatil(String productId) {
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
        return this.baseMapper.getAttributePrecision(request);
    }

    /**
     * 构建故障展示信息
     * @param data
     */
    private void buildErrorInfoStr(List<ProductAttributeErrorVO> data) {
        data.forEach(errorVO->{
            String str = "";
            errorVO.setTypeStr(AttributeErrorTypeEnum.getInstByType(errorVO.getType())!= null?AttributeErrorTypeEnum.getInstByType(errorVO.getType()).getName():"");
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
//        List<String> ids = this.getIdListByProductId(request.getProductId());
//        if (CollectionUtils.isEmpty(ids)) {
//            return;
//        }
//        this.remove(new LambdaQueryWrapper<ProductAttributeError>().eq(ProductAttributeError::getProductId, request.getProductId()));
        this.removeById(attrId);
        iProductAttributeErrorInfoService.remove(new LambdaQueryWrapper<ProductAttributeErrorInfo>().eq(ProductAttributeErrorInfo::getErrorAttributeId, attrId));
    }
}
