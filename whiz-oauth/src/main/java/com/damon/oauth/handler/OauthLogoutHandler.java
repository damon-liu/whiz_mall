package com.damon.oauth.handler;

import cn.hutool.core.util.StrUtil;
import com.damon.common.oauth.properties.SecurityProperties;
import com.damon.common.oauth.utils.AuthUtil;
import com.damon.oauth.utils.UsernameHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 14:57
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(tokenStore, "tokenStore不为空！");
        String token = request.getParameter("token");
        if (StrUtil.isEmpty(token)) {
            token = AuthUtil.extractToken(request);
        }
        if(StrUtil.isNotEmpty(token)){
            if (securityProperties.getAuth().getUnifiedLogout()) {
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
                UsernameHolder.setContext(oAuth2Authentication.getName());
            }

            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
            OAuth2RefreshToken refreshToken;
            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    log.info("刪除refreshToken {} ", existingAccessToken.getRefreshToken());
                    refreshToken = existingAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
                log.info("刪除accessToken {} ", existingAccessToken);
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }
    }
}
