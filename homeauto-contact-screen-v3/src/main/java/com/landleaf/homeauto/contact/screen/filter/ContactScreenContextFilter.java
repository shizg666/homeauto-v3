package com.landleaf.homeauto.contact.screen.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.filter.TokenFilter;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ContactScreenContextFilter
 * @Description: 大屏通讯交互上下文过滤器（http协议）
 * @Author wyl
 * @Date 2020/8/6
 * @Version V1.0
 **/
public class ContactScreenContextFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 819293185870247274L;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static Logger LOGGER = LoggerFactory.getLogger(ContactScreenContextFilter.class);
    private static String[] excludePaths = new String[]{"/contact-screen/screen/city/weather"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("ContactScreenContextFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String servletPath = req.getServletPath();
        LOGGER.info("大屏端主动请求url==》{}", servletPath);
        // **********************************余下的token校验解析*************************************************//
        boolean whitePath = false;
        for (String excludePath : excludePaths) {
            if(antPathMatcher.match(excludePath,servletPath)){
                whitePath=true;
                ContactScreenHeader header = new ContactScreenHeader();
                header.setScreenMac(null);
                ContactScreenContext.setContext(header);
                break;
            }
        }
        if(!whitePath){
            if (antPathMatcher.match("/contact-screen/screen/**", servletPath)) {
                String mac = req.getHeader(CommonConst.HEADER_MAC);
                if (StringUtils.isNotEmpty(mac)) {
                    ContactScreenHeader header = new ContactScreenHeader();
                    header.setScreenMac(mac);
                    ContactScreenContext.setContext(header);
                } else {
                    Response returnResponse = new Response<>();
                    returnResponse.setErrorCode("400");
                    returnResponse.setErrorMsg("header not found mac");
                    res.getWriter().println(JSON.toJSONString(returnResponse, SerializerFeature.WriteMapNullValue));
                    return;
                }
            }
        }
        try {
            chain.doFilter(request, response);
        } finally {
            ContactScreenContext.remove();
        }
        return;
    }

}