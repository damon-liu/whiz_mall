package com.damon.common.oauth;

import com.damon.common.oauth.properties.SecurityProperties;
import com.damon.common.oauth.properties.TokenStoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 2:42
 */
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
@ComponentScan
public class AuthClientAutoConfiguration {
}
