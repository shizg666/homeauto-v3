package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoAddress;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 公共 服务类
 * </p>
 *
 * @author shizg
 */
public interface CommonService<T> {


    /**
     * 获取当前用户的paths
     *
     * @return
     */
    List<String> getUserPathScope();





}
