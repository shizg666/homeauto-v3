package com.landleaf.homeauto.common.web.filter;

import com.landleaf.homeauto.common.web.wrapper.BodyReaderHttpServletRequestWrapper;
import com.landleaf.homeauto.common.web.context.RemoteHostDetailContext;
import com.landleaf.homeauto.common.domain.RemoteHostDetail;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 走自定义httpReques 过滤器
 */
public class HttpServletRequestFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        setRealIp((HttpServletRequest) request);

        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }


    private void setRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        RemoteHostDetailContext.setRemoteHostDetail(
                new RemoteHostDetail(ip));
    }
}