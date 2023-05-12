package com.damon.common.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 2:40
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "whiz.security")
@RefreshScope
public class SecurityProperties {

    // 认证相关配置（url级别鉴权+token续签）
    private AuthProperties auth = new AuthProperties();

    // 需要忽略认证的url地址
    private PermitProperties ignore = new PermitProperties();

}
