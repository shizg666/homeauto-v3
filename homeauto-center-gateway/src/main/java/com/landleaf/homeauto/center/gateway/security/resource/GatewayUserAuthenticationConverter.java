package com.landleaf.homeauto.center.gateway.security.resource;

import com.landleaf.homeauto.center.gateway.domain.HomeAutoUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName GatewayUserAuthenticationConverter
 * @Description: 自定义资源服务器解析check-token返回信息  本次未使用
 * @Author wyl
 * @Date 2020/6/10
 * @Version V1.0
 **/
public class GatewayUserAuthenticationConverter implements UserAuthenticationConverter {
    private static final String N_A = "N/A";
    private Collection<? extends GrantedAuthority> defaultAuthorities;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        return null;
    }

    // map 是check-token 返回的全部信息
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

            String username = (String) map.get(USERNAME);
            String source = (String) map.get("source");
            HomeAutoUserDetails demoUserDetails = new HomeAutoUserDetails(null, null, username, source);
            return new UsernamePasswordAuthenticationToken(demoUserDetails, N_A, authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
