package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProduct;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.mapper.SysProductMapper;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.enums.category.CategoryAttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.alibaba.druid.wall.spi.WallVisitorUtils.checkUpdate;

/**
 * <p>
 * 系统产品表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct> implements ISysProductService {

    @Autowired
    private IdService idService;
    @Autowired
    private ISysProductAttributeInfoService iSysProductAttributeInfoService;
    @Autowired
    private ISysProductAttributeService iSysProductAttributeService;
    @Autowired
    private ISysProductCategoryService iSysProductCategoryService;
    @Autowired
    private IBizNumProducerService iBizNumProducerService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;

    //系统产品类别code CategoryTypeEnum 不可重复
    public static final String SYS_PRODCUT_CODE = "1";

    public static final Integer UPDATE_FLAG = 1;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysProduct(SysProductDTO requestDTO) {
        checkAdd(requestDTO);
        SysProduct product = BeanUtil.mapperBean(requestDTO, SysProduct.class);
        String productCode = iBizNumProducerService.getProductCode(SYS_PRODCUT_CODE);
        product.setCode(productCode);
        save(product);
        //保存产品属性
        requestDTO.setId(product.getId());
        requestDTO.setCode(productCode);
        saveAttribute(requestDTO);
        //保存系统产品品类信息
        iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
    }

    /**
     * 新增系统产品属性
     * @param requestDTO
     */
    private void saveAttribute(SysProductDTO requestDTO) {
        //产品属性
        List<SysProductAttribute> attributeList = Lists.newArrayList();
        //产品属性值
        List<SysProductAttributeInfo> infoList = Lists.newArrayList();
        //功能属性
        buildAttrData(attributeList, infoList, requestDTO, CategoryAttributeTypeEnum.FEATURES.getType(), requestDTO.getAttributesFunc());
        buildAttrData(attributeList, infoList,  requestDTO, CategoryAttributeTypeEnum.BASE.getType(), requestDTO.getAttributesBase());
        iSysProductAttributeService.saveBatch(attributeList);
        iSysProductAttributeInfoService.saveBatch(infoList);
    }

    private void buildAttrData(List<SysProductAttribute> attributeList, List<SysProductAttributeInfo> infoList, SysProductDTO requestDTO, Integer type, List<SysProductAttributeDTO> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }

        for (SysProductAttributeDTO attribute : attributes) {
            SysProductAttribute productAttribute = BeanUtil.mapperBean(attribute, SysProductAttribute.class);
            productAttribute.setSysProductId(requestDTO.getId());
            productAttribute.setSysProductCode(requestDTO.getCode());
            productAttribute.setId(idService.getSegmentId());
            productAttribute.setFunctionType(type);
            attributeList.add(productAttribute);
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                SysProductAttributeInfo attributeInfo = BeanUtil.mapperBean(info, SysProductAttributeInfo.class);
                attributeInfo.setSysAttrId(productAttribute.getId());
                attributeInfo.setSysProductId(requestDTO.getId());
                attributeInfo.setSysProductCode(requestDTO.getCode());
                infoList.add(attributeInfo);
            });
        }
    }


    private void checkAdd(SysProductDTO requestDTO) {
        int count = count(new LambdaQueryWrapper<SysProduct>().eq(SysProduct::getType,requestDTO.getType()).last("limit 1"));
        if (count > 0){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"该系统已存在！");
        }
    }
    private void checkUpdate(SysProductDTO requestDTO) {
        SysProduct product = getById(requestDTO.getId());
        if (product.getType().equals(requestDTO.getType())){
            return;
        }
        checkAdd(requestDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSysProdut(SysProductDTO requestDTO) {
        checkUpdate(requestDTO);
        SysProduct product = BeanUtil.mapperBean(requestDTO, SysProduct.class);
        updateById(product);
        if (UPDATE_FLAG.equals(requestDTO.getUpdateFalg())) {
            //1 可以修改
            //删除数据
            iSysProductAttributeService.deleteProductAttribures(requestDTO.getId());
            iSysProductCategoryService.deleteBySysProductId(requestDTO.getId());

            //新增数据
            saveAttribute(requestDTO);
            //保存系统产品品类信息
            iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
        } else {
            //不能修改只能新增 传过来的都是新数据
            //新增数据
            saveAttribute(requestDTO);
            //保存系统产品品类信息
            iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSysProdutById(Long sysProductId) {
        if (iHomeAutoProjectService.exsistSysPruduct(sysProductId)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"系统已关联项目不可删除");
        }
        removeById(sysProductId);
        iSysProductAttributeService.deleteProductAttribures(sysProductId);
        iSysProductCategoryService.deleteBySysProductId(sysProductId);
    }

    @Override
    public SysProductDetailVO getDetailSysProdut(Long sysProductId) {
        SysProduct sysProduct = getById(sysProductId);
        SysProductDetailVO sysProductDetailVO = BeanUtil.mapperBean(sysProduct,SysProductDetailVO.class);
        List<SysProductAttributeVO> sysProductAttributeVOS = iSysProductCategoryService.getListAttrVOBySysProductId(sysProductId);
        return null;
    }
}
