package com.landleaf.homeauto.common.config.auth.login.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.common.config.auth.login.context.TokenContext;
import com.landleaf.homeauto.common.config.auth.login.context.UserContext;
import com.landleaf.homeauto.common.config.auth.login.dataprovider.IUserProvider;
import com.landleaf.homeauto.common.config.auth.login.token.Token;
import com.landleaf.homeauto.common.config.auth.login.token.TokenStore;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.constance.SepatorConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.oauth.IUser;
import com.landleaf.homeauto.common.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.*;

/**
 * 登录认证过滤器
 */
public class OauthFilter extends HttpServlet implements Filter, ApplicationContextAware {
    private static final long serialVersionUID = -886104465143606721L;
    private static Logger LOGGER = LoggerFactory.getLogger(OauthFilter.class);
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private ApplicationContext applicationContext;
    private String logoutPath;
    private String excludedPaths;
    private TokenStore tokenStore;
    private long refreshTokenExpire;

    public OauthFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logoutPath = filterConfig.getInitParameter(CommonConst.AUTH_LOGOUT);
        excludedPaths = filterConfig.getInitParameter(CommonConst.AUTH_EXCLUDED_PATHS);
        refreshTokenExpire = Long.parseLong(filterConfig.getInitParameter(CommonConst.REFRESH_TOKEN_EXPIRE));
        LOGGER.info("登录认证过滤器初始化.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        ErrorCodeEnumConst errorCodeEnumConst;
        String servletPath = req.getServletPath();
        LOGGER.info("请求url==》{}", servletPath);
        /**********************************白名单路径放行*************************************************/
        if (excludedPaths != null && excludedPaths.trim().length() > 0) {
            for (String excludedPath : excludedPaths.split(SepatorConst.COMMA)) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    LOGGER.info("请求url==》{},白名单====>放行", servletPath);
                    return;
                }
            }

        }
        /**********************************余下的token校验解析*************************************************/
        String access_token = request.getParameter(CommonConst.TOKEN);
        if (access_token == null || access_token.trim().isEmpty()) {
            access_token = req.getHeader(CommonConst.AUTHORIZATION);
        }
        //token不能为空
        if (access_token == null || access_token.trim().isEmpty()) {
            errorCodeEnumConst = TOKEN_NOT_BLAND;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            LOGGER.error("access_token不能为空====>返回");
            return;
        }
        String subject = null;
        String userId = null;
        String userType = null;
        try {
            String tokenKey = tokenStore.getTokenKey();
            LOGGER.info("-------------------------------------------");
            LOGGER.info("开始解析token：" + access_token);
            LOGGER.info("使用tokenKey：" + tokenKey);
            subject = TokenUtil.parseTokenForSubject(access_token, tokenKey);
            String[] subjectData = StringUtils.split(subject, SepatorConst.AT);
            userId = subjectData[0];
            userType = subjectData[1];
        } catch (ExpiredJwtException e) {
            LOGGER.error("token已过期");
            errorCodeEnumConst = LOGIN_EXPIRED;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            LOGGER.error("token已过期====>返回");
            return;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            errorCodeEnumConst = TOKEN_RESOLVE_ERROR;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            LOGGER.error("token解析异常====>返回");
            return;
        }
        Token token = tokenStore.findToken(userId, userType, access_token);
        if (token == null) {
            LOGGER.error("token不在系统中");
            errorCodeEnumConst = LOGIN_EXPIRED;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            return;
        }
        //token续约
        tokenStore.refreshTokenExpire(userId, userType, access_token, refreshTokenExpire);

        /**********************************登出*************************************************/
        // logout path check
        if (logoutPath != null
                && logoutPath.trim().length() > 0
                && logoutPath.equals(servletPath)) {
            tokenStore.removeToken(userId, userType, access_token);
            chain.doFilter(request, response);
            LOGGER.error("登出地址====>放行");
            return;
        }
        /**********************************封装用户对象到Context*******************************************/
        //存缓存中获取用户信息
        IUserProvider userProvider = applicationContext.getBean(IUserProvider.class);
        if (userProvider == null) {
            LOGGER.error("系统异常，未实现用户信息提供接口！");
            errorCodeEnumConst = ERROR_CODE_UNHANDLED_EXCEPTION;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            return;
        }
        //从提供者读取数据
        IUser user = null;
        try {
            user = userProvider.getUserByIdAndType(userId, userType);
        } catch (Exception e) {
            LOGGER.error("获取用户信息异常：{}", e.getMessage(), e);
            errorCodeEnumConst = USER_INFO_GET_ERROR;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            return;
        }
        if (user == null) {
            errorCodeEnumConst = USER_NOT_FOUND;
            returnError(errorCodeEnumConst.getCode(), errorCodeEnumConst.getMsg(), res);
            LOGGER.error("未找到用户:{}", userId);
            return;
        }

        //将用户信息设置到UserContext中
        UserContext.setCurrentUser(user);
        String userName = userProvider.getUserNameByIUser(userId, userType, user);
        token.setUserName(userName);
        TokenContext.setToken(token);
        // already login, allow
        try {
            //输出日志
            printLog(req, response);
            chain.doFilter(request, response);
        } finally {
            //请求处理完成后将UserContext中的用户信息移除掉
            UserContext.remove();
            TokenContext.remove();
        }
        return;
    }


    private void returnError(int code, String msg, HttpServletResponse res) {
        Response returnResponse = new Response<>();
        returnResponse.setSuccess(false);
        returnResponse.setErrorCode(String.valueOf(code));
        returnResponse.setErrorMsg(msg);
        res.setContentType("application/json;charset=utf-8");
        try {
            res.getWriter().println(JSON.toJSONString(returnResponse));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 输出日志
     *
     * @param request
     * @param response
     */
    private void printLog(HttpServletRequest request, ServletResponse response) {
        String content = String.format("");
        LOGGER.info("<====接口请求日志====>");

        LOGGER.info("请求路径{}", request.getServletPath());
        LOGGER.info("请求参数{}", getRequestParams(request));
    }


    public String getRequestParams(HttpServletRequest request) {
        Map<String, String> data = Maps.newHashMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        data.put("Query", JSON.toJSONString(parameterMap));
        StringBuilder sb = new StringBuilder();
        BufferedInputStream bis = null;
        InputStream is = null;
        try {
            is = request.getInputStream();
            bis = new BufferedInputStream(is);
            byte[] tmp = new byte[8192];
            int n = 0;
            while ((n = bis.read(tmp)) > 0) {
                String temp = new String(tmp, 0, n);
                sb.append(temp);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        data.put("Body", sb.toString());
        return JSON.toJSONString(data);
    }
}
