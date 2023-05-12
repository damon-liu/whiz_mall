package com.damon.gateway.auth.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.damon.common.constant.CommonConstant;
import com.damon.common.context.TenantContextHolder;
import com.damon.common.entity.SysMenu;
import com.damon.common.oauth.properties.SecurityProperties;
import com.damon.common.oauth.utils.AuthUtil;
import com.damon.gateway.feign.AsynMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Description:url权限认证
 *
 * @author damon.liu
 * Date 2023-05-11 8:08
 */
@Slf4j
@Component
public class PermissionAuthManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Resource
    private AsynMenuService asynMenuService;

    @Autowired
    private SecurityProperties securityProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            ServerWebExchange exchange = authorizationContext.getExchange();
            ServerHttpRequest request = exchange.getRequest();
            boolean isPermission = hasPermission(auth, request.getMethodValue(), request.getURI().getPath());
            return new AuthorizationDecision(isPermission);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 查询当前用户拥有的资源权限
     *
     * @param roleCodes 角色code列表，多个以','隔开
     * @return
     */
    public List<SysMenu> findMenuByRoleCodes(String roleCodes) {
        Future<List<SysMenu>> futureResult = asynMenuService.findByRoleCodes(roleCodes);
        try {
            return futureResult.get();
        } catch (Exception e) {
            log.error("异步获取角色：{} 菜单失败！{}", roleCodes, e);
        }
        return Collections.emptyList();
    }

    public boolean hasPermission(Authentication authentication, String requestMethod, String requestURI) {
        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //判断是否开启url权限验证
            if (!securityProperties.getAuth().getUrlPermission().getEnable()) {
                return true;
            }
            //超级管理员admin不需认证
            String username = AuthUtil.getUsername(authentication);
            if (CommonConstant.ADMIN_USER_NAME.equals(username)) {
                return true;
            }

            OAuth2Authentication auth2Authentication = (OAuth2Authentication)authentication;
            //判断应用黑白名单
            if (!isNeedAuth(auth2Authentication.getOAuth2Request().getClientId())) {
                return true;
            }

            //判断不进行url权限认证的api，所有已登录用户都能访问的url
            for (String path : securityProperties.getAuth().getUrlPermission().getIgnoreUrls()) {
                if (antPathMatcher.match(path, requestURI)) {
                    return true;
                }
            }

            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }

            //保存租户信息
            String clientId = auth2Authentication.getOAuth2Request().getClientId();
            TenantContextHolder.setTenant(clientId);

            String roleCodes = grantedAuthorityList.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.joining(", "));
            List<SysMenu> menuList = findMenuByRoleCodes(roleCodes);
            for (SysMenu menu : menuList) {
                if (StringUtils.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), requestURI)) {
                    if (StrUtil.isNotEmpty(menu.getPathMethod())) {
                        return requestMethod.equalsIgnoreCase(menu.getPathMethod());
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断应用是否满足白名单和黑名单的过滤逻辑
     * @param clientId 应用id
     * @return true(需要认证)，false(不需要认证)
     */
    private boolean isNeedAuth(String clientId) {
        boolean result = true;
        //白名单
        List<String> includeClientIds = securityProperties.getAuth().getUrlPermission().getIncludeClientIds();
        //黑名单
        List<String> exclusiveClientIds = securityProperties.getAuth().getUrlPermission().getExclusiveClientIds();
        if (includeClientIds.size() > 0) {
            result = includeClientIds.contains(clientId);
        } else if(exclusiveClientIds.size() > 0) {
            result = !exclusiveClientIds.contains(clientId);
        }
        return result;
    }
}
