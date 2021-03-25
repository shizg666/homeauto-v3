package com.landleaf.homeauto.common.web.filter;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.RemoteHostDetail;
import com.landleaf.homeauto.common.web.context.RemoteHostDetailContext;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 内部服务过滤器
 */
@Slf4j
public class TokenFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 819293185870247274L;

    private static Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("AddTokenFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        LOGGER.info("请求url==》{}", servletPath);
        // **********************************余下的token校验解析*************************************************//
        String tokenStr = req.getHeader(CommonConst.AUTHORIZATION_INNER);
//        log.info("内部token信息,[Authorization_inner]{}",tokenStr);
        if (StringUtils.isNotEmpty(tokenStr)) {
            HomeAutoToken token = JSON.parseObject(URLDecoder.decode(tokenStr), HomeAutoToken.class);
            TokenContext.setToken(token);
        }
        getRemoteHostDetail(req);
        try {
            chain.doFilter(request, response);
        } finally {
            //请求处理完成后将UserContext中的用户信息移除掉
            TokenContext.remove();
            RemoteHostDetailContext.remove();
        }
    }


    private void getRemoteHostDetail(HttpServletRequest request) {
        String remoteHostDetailStr = request.getHeader(CommonConst.REMOTE_HOST_DETAIL);
        if (StringUtils.isNotBlank(remoteHostDetailStr)) {
            RemoteHostDetail remoteHostDetail = JSON.parseObject(URLDecoder.decode(remoteHostDetailStr), RemoteHostDetail.class);
            RemoteHostDetailContext.setRemoteHostDetail(remoteHostDetail);
        }
    }
}
