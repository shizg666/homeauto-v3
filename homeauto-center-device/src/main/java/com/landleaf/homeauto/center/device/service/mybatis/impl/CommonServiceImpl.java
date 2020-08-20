package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.CommonService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公共 服务类
 * </p>
 *
 * @author shizg
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired(required = false)
    private UserRemote userRemote;

    @Override
    public List<String> getUserPathScope() {
        String userId = TokenContext.getToken().getUserId();
        Response<List<String>> response = userRemote.getUserPaths(userId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getErrorCode(), response.getErrorMsg());
        }
        return response.getResult();
    }
}
