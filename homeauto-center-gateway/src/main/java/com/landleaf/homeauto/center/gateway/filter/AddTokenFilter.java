package com.landleaf.homeauto.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @ClassName AddTokenFilter
 * @Description: TODO
 * @Author wyl
 * @Date 2020/7/8
 * @Version V1.0
 **/
public class AddTokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();

        HomeAutoToken token = TokenContext.getToken();
        if(token!=null){
            context.addZuulRequestHeader(CommonConst.AUTHORIZATION_INNER, JSON.toJSONString(token));
        }
        return null;
    }
}
