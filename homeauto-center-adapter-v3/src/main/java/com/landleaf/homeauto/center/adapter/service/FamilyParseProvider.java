package com.landleaf.homeauto.center.adapter.service;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 获取家庭信息provider
 *
 * @author pilo
 */
@Component
@Slf4j
public class FamilyParseProvider {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DeviceRemote deviceRemote;

    public AdapterFamilyDTO getFamily(String terminalMac) {
        String key = String.format(RedisCacheConst.MAC_FAMILY, terminalMac);
        if (redisUtils.hasKey(key)) {
            Object o = redisUtils.get(key);
            if (o != null) {
                return JSON.parseObject(JSON.toJSONString(o), AdapterFamilyDTO.class);
            }
        }
        Response<AdapterFamilyDTO> familyDTOResponse = null;
        try {
            familyDTOResponse = deviceRemote.getFamily(terminalMac);
            log.info("返回家庭结果集:{}",JSON.toJSONString(familyDTOResponse));
            if (familyDTOResponse != null && familyDTOResponse.isSuccess()) {
                AdapterFamilyDTO familyDTO = familyDTOResponse.getResult();
                if(familyDTO!=null&& !Objects.isNull(familyDTO.getFamilyId())){
                    redisUtils.set(key, familyDTO, RedisCacheConst.CONFIG_COMMON_EXPIRE);
                    return familyDTO;
                }
            }
        } catch (Exception e) {
            log.error("获取家庭信息异常,[终端地址]:{}", terminalMac);
        }
        return null;
    }
}
