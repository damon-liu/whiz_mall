package com.damon.oauth.handler;

import com.damon.common.entity.Result;
import com.damon.common.oauth.properties.SecurityProperties;
import com.damon.common.utils.JsonUtil;
import com.damon.oauth.service.UnifiedLogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 15:09
 */
public class OauthLogoutSuccessHandler implements LogoutSuccessHandler {

    // private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private UnifiedLogoutService unifiedLogoutService;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (securityProperties.getAuth().getUnifiedLogout()) {
            unifiedLogoutService.allLogout();
        }

        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        String jsonStr = JsonUtil.toJSONString(Result.succeed("登出成功"));
        writer.write(jsonStr);
        writer.flush();
    }

}
