package com.damon.oauth.config;

import com.damon.common.config.DefaultPasswordConfig;
import com.damon.common.constant.SecurityConstants;
import com.damon.oauth.handler.OauthLogoutSuccessHandler;
import com.damon.oauth.organ.TenantUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.annotation.Resource;

/**
 * Description:security配置
 *
 * @author damon.liu
 * Date 2023-05-12 4:11
 */
@Configuration
@Import(DefaultPasswordConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private LogoutHandler oauthLogoutHandler;

    // @Autowired
    // private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
    //
    // @Autowired
    // private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Autowired(required = false)
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     *
     * @return 认证管理对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TenantUsernamePasswordAuthenticationFilter tenantAuthenticationFilter(AuthenticationManager authenticationManager) {
        TenantUsernamePasswordAuthenticationFilter filter = new TenantUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setFilterProcessesUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(SecurityConstants.LOGIN_FAILURE_PAGE));
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .logout()
                .logoutUrl(SecurityConstants.LOGOUT_URL)
                .logoutSuccessHandler(new OauthLogoutSuccessHandler())
                .addLogoutHandler(oauthLogoutHandler)
                .clearAuthentication(true)
                // .and()
                // .apply(openIdAuthenticationSecurityConfig)
                // .and()
                // .apply(mobileAuthenticationSecurityConfig)
                .and()
                // .addFilterBefore(new LoginProcessSetTenantFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().cacheControl();

        // 登录设置（登录页、地址、成功后处理器）
        http.formLogin()
                .loginPage(SecurityConstants.LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL)
                .successHandler(authenticationSuccessHandler);

        // 基于密码 等模式可以无session,不支持授权码模式
        if (authenticationEntryPoint != null) {
            http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            // 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }
    }

    /**
     * 全局用户信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
