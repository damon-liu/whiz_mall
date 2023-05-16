package com.damon.oauth.config;

import com.damon.common.entity.SysUser;
import com.damon.common.oauth.properties.TokenStoreProperties;
import com.damon.oauth.service.impl.RedisClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description:OAuth2 授权服务器配置
 *
 * @author damon.liu
 * Date 2023-05-10 13:59
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 注入authenticationManager 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Autowired
    private RedisClientDetailsService clientDetailsService;

    @Autowired
    private RandomValueAuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private TokenGranter tokenGranter;

    /**
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices)
                .exceptionTranslator(webResponseExceptionTranslator)
                .tokenGranter(tokenGranter);
    }

    /**
     * 配置应用名称 应用id
     * 配置OAuth2的客户端相关信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
        clientDetailsService.loadAllClientToCache();
    }

    /**
     * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                //让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();
    }

    /**
     * 添加自定义信息的话，比如说登录用户的ID
     *
     * @param tokenStoreProperties
     * @return
     */
    @Bean
    @Order(1)
    public TokenEnhancer tokenEnhancer(TokenStoreProperties tokenStoreProperties) {
        return (accessToken, authentication) -> {
            Set<String> responseTypes = authentication.getOAuth2Request().getResponseTypes();
            Map<String, Object> additionalInfo = new HashMap<>(3);
            // String accountType = AuthUtil.getAccountType(authentication.getUserAuthentication());
            // if (StrUtil.isNotEmpty(accountType)) {
            //     additionalInfo.put(SecurityConstants.ACCOUNT_TYPE_PARAM_NAME, accountType);
            // }

            if ("authJwt".equals(tokenStoreProperties.getType())) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof SysUser) {
                    SysUser user = (SysUser)principal;
                    if ("authJwt".equals(tokenStoreProperties.getType())) {
                        additionalInfo.put("userId", user.getId());
                    }
                }
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

}
