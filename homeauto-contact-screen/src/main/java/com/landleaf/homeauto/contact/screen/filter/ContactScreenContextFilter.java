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

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName ContactScreenContextFilter
 * @Description: 大屏通讯交互上下文过滤器（http协议）
 * @Author wyl
 * @Date 2020/8/6
 * @Version V1.0
 **/
public class ContactScreenContextFilter extends HttpServlet implements Filter {

    private static final long serialVersionUID = 819293185870247274L;

    private static Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("ContactScreenContextFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        LOGGER.info("大屏端主动请求url==》{}", servletPath);
        PrintWriter writer = response.getWriter();
        // **********************************余下的token校验解析*************************************************//
        String mac = req.getHeader(CommonConst.HEADER_MAC);
        if (StringUtils.isNotEmpty(mac)) {
            ContactScreenHeader header = new ContactScreenHeader();
            header.setScreenMac(mac);
            ContactScreenContext.setContext(header);
        } else {
            Response returnResponse = new Response<>();
            returnResponse.setErrorCode("400");
            returnResponse.setErrorMsg("header中缺少mac");
            writer.write(JSON.toJSONString(returnResponse, SerializerFeature.WriteMapNullValue));
            return;
        }
        try {
            chain.doFilter(request, response);
        } finally {
            ContactScreenContext.remove();
            if (writer != null) {
                writer.close();
            }
        }
    }

}