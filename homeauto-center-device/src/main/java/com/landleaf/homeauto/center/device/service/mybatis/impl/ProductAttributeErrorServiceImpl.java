package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Slf4j
public class ProductAttributeErrorServiceImpl  implements IProductAttributeErrorService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IDeviceAttrInfoService deviceAttrInfoService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

//    public static final String ERROR_CODE_SHOWISTR_2 = "枚举值：1-%s；2-%s";
//    public static final String ERROR_CODE_SHOWISTR_1 = "枚举值：1-%s";
//    public static final String COMMUNICATE_SHOWISTR = "布尔值：0-正常；1-故障";
//    public static final String VAKUE_SHOWISTR = "属性名称：%s；取值范围：%s~%s";



    @Override
    public AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request) {
//      ProductErrorSchedule
//        if(StringUtil.isEmpty(request.getFamilyCode())){
//            log.error("********************************家庭code不能为空！");
//            return null;
//        }
//        String templateId = templateId = iHomeAutoFamilyService.getTemplateIdByFamilyCode(request.getFamilyCode());
//        request.setTempalteId(templateId);
        String caheKey = String.format(RedisCacheConst.DEVICE_ERROR_ATTR_INFO,
                request.getDeviceId(), request.getCode());
        String jsonObject = (String) redisUtils.get(caheKey);
        if (!StringUtil.isEmpty(jsonObject)){
            AttributeErrorDTO infoDTO = JSON.parseObject(jsonObject, AttributeErrorDTO.class);
            return infoDTO;
        }
        AttributeErrorDTO data= deviceAttrInfoService.getAttrError(request);
        return data;


    }



//    /**
//     * 数据缓存
//     * @param request
//     */
//    private void cacheData(ProductAttributeErrorDTO request) {
//        AttributeErrorDTO errorDTO = BeanUtil.mapperBean(request,AttributeErrorDTO.class);
//        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request.getType())){
//            List<ProductAttributeErrorInfoDTO> infoDTOS = request.getInfos();
//            if (!CollectionUtils.isEmpty(infoDTOS)){
//                List<String> vals = infoDTOS.stream().sorted(Comparator.comparing(ProductAttributeErrorInfoDTO::getSortNo)).map((obj)-> obj.getVal()).collect(Collectors.toList());
//                errorDTO.setDesc(vals);
//            }
//        }
//        String key  = String.format(RedisCacheConst.PRODUCT_ERROR_INFO,request.getProductCode(),request.getCode());
//        redisUtils.set(key, JSON.toJSONString(errorDTO));
//    }







//    /**
//     * 删除缓存
//     * @param request
//     */
//    private void deleteCache(ProductAttributeErrorDTO request,ProductAttributeError error) {
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
//    }

}
