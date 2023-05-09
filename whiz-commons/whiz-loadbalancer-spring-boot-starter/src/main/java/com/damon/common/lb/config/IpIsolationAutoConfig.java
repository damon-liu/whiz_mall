package com.damon.common.lb.config;

import com.damon.common.constant.ConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-08 3:44
 */
@Configuration
@LoadBalancerClients(defaultConfiguration = IpLoadBalancerConfig.class)
@ConditionalOnProperty(prefix = ConfigConstants.CONFIG_LOADBALANCE_ISOLATION, name = "enabled", havingValue = "true", matchIfMissing = false)
@Import({VersionRegisterBeanPostProcessor.class})
public class IpIsolationAutoConfig {
}
