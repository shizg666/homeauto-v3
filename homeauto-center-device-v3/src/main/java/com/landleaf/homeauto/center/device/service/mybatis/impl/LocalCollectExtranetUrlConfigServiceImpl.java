package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.LocalCollectExtranetUrlConfig;
import com.landleaf.homeauto.center.device.model.mapper.LocalCollectExtranetUrlConfigMapper;
import com.landleaf.homeauto.center.device.model.vo.ExtranetUrlConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.ExtranetUrlConfigDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.center.device.service.mybatis.ILocalCollectExtranetUrlConfigService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * <p>
 * 本地数采外网URL配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
@Slf4j
@Service
public class LocalCollectExtranetUrlConfigServiceImpl extends ServiceImpl<LocalCollectExtranetUrlConfigMapper, LocalCollectExtranetUrlConfig> implements ILocalCollectExtranetUrlConfigService {
    @Value("${homeauto.local-collect-ping}")
    public String URL_SUFFIX;
    @Autowired
    @Qualifier("outRestTemplate")
    private RestTemplate outRestTemplate;
    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;

    @Override
    public void addConfig(ExtranetUrlConfigDTO request) {
        String url = request.getUrl().concat(URL_SUFFIX);
        String realestateCode = iHomeAutoRealestateService.getRealestateCodeById(request.getRealestateId());
        pingLocalCollect(url);
        LocalCollectExtranetUrlConfig config = getOne(new LambdaQueryWrapper<LocalCollectExtranetUrlConfig>().eq(LocalCollectExtranetUrlConfig::getRealestateCode,realestateCode));
        if (Objects.isNull(config)){
            LocalCollectExtranetUrlConfig configDO = new LocalCollectExtranetUrlConfig();
            configDO.setRealestateCode(realestateCode);
            configDO.setUrl(request.getUrl());
            save(configDO);
        }else {
            config.setUrl(request.getUrl());
            updateById(config);
        }
    }

    /**
     *请求本地数采服务器
     * @param url
     */
    private void pingLocalCollect(String url) {
        log.info("请求本地数采url:{}",url);
        ResponseEntity<String> result;
        try{
            result = outRestTemplate.getForEntity(url, String.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "请确认域名是否正确以及本地服务器是否联网");
        }
        if (Objects.isNull(result) || !HttpStatus.OK.equals(result.getStatusCode())){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "请确认域名是否正确以及本地服务器是否联网");
        }
    }

    @Override
    public ExtranetUrlConfigVO getLocalCollectConfig(Long realestateId) {

        String realestateCode = iHomeAutoRealestateService.getRealestateCodeById(realestateId);
        String url = this.baseMapper.getLocalCollectConfig(realestateCode);
        ExtranetUrlConfigVO result = new ExtranetUrlConfigVO();
        result.setUrl(url);
        if (StringUtil.isEmpty(url)){
            result.setUrl("");
            return result;
        }
        try {
            pingLocalCollect(url.concat(URL_SUFFIX));
            result.setStatus(1);
        }catch (Exception e){
            result.setStatus(0);
        }
        return result;
    }
}
