package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.remote.WebSocketRemote;
import com.landleaf.homeauto.center.device.service.mybatis.CommonService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    @Override
    public  void setResponseHeader(HttpServletResponse response, String fileName) {
        // 使用swagger 可能会导致各种问题，测试请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            String name = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + name + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
