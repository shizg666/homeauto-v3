package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.LocalCollectExtranetUrlConfig;
import com.landleaf.homeauto.center.device.model.mapper.LocalCollectExtranetUrlConfigMapper;
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
        pingLocalCollect(url);
        String realestateCode = iHomeAutoRealestateService.getRealestateCodeById(request.getRealestateId());
        LocalCollectExtranetUrlConfig config = new LocalCollectExtranetUrlConfig();
        config.setRealestateCode(realestateCode);
        config.setUrl(request.getUrl());
        save(config);
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
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "url请求不通:"+url);
        }
        if (Objects.isNull(result) || !HttpStatus.OK.equals(result.getStatusCode())){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "url请求不通:{}"+url);
        }
    }

    @Override
    public String getLocalCollectConfig(Long realestateId) {
        String realestateCode = iHomeAutoRealestateService.getRealestateCodeById(realestateId);
        String url = this.baseMapper.getLocalCollectConfig(realestateCode);
        if (StringUtil.isEmpty(url)){
            return "";
        }
        String result = url;
        pingLocalCollect(url.concat(URL_SUFFIX));
        return result;
    }
}
