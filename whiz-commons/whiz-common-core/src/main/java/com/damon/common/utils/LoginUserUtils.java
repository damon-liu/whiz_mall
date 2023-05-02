package com.damon.common.utils;

import cn.hutool.core.util.StrUtil;

import com.damon.common.constant.SecurityConstants;
import com.damon.common.entity.SysRole;
import com.damon.common.entity.SysUser;
import com.damon.common.feign.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取当前登录人工具类
 *
 */
public class LoginUserUtils {
    /**
     * 获取当前登录人
     */
    public static SysUser getCurrentUser(HttpServletRequest request, boolean isFull) {
        SysUser user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            //客户端模式只返回一个clientId
            if (principal instanceof SysUser) {
                user = (SysUser)principal;
            }
        }
        if (user == null) {
            String userId = request.getHeader(SecurityConstants.USER_ID_HEADER);
            String username = request.getHeader(SecurityConstants.USER_HEADER);
            String roles = request.getHeader(SecurityConstants.ROLE_HEADER);

            if (StrUtil.isAllNotBlank(username, userId)) {
                if (isFull) {
                    UserService userService = SpringUtil.getBean(UserService.class);
                    user = userService.selectByUsername(username);
                } else {
                    user = new SysUser();
                    user.setId(Long.valueOf(userId));
                    user.setUsername(username);
                }
                if (StrUtil.isNotBlank(roles)) {
                    List<SysRole> sysRoleList = new ArrayList<>();
                    Arrays.stream(roles.split(",")).forEach(role -> {
                        SysRole sysRole = new SysRole();
                        sysRole.setCode(role);
                        sysRoleList.add(sysRole);
                    });
                    user.setRoles(sysRoleList);
                }
            }
        }
        return user;
    }

    public static SysUser getCurrentUser(boolean isFull) {
        // 从请求上下文里获取 Request 对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest contextRequest = requestAttributes.getRequest();
        if (contextRequest != null) {
            return LoginUserUtils.getCurrentUser(contextRequest, isFull);
        }
        return null;
    }
}
