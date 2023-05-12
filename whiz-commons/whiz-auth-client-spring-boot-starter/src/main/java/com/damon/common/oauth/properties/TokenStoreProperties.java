package com.damon.common.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 2:39
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "whiz.oauth2.token.store")
@RefreshScope
public class TokenStoreProperties {

    /**
     * token存储类型(redis/db/authJwt/resJwt)
     */
    private String type = "redis";
}
