package com.damon.common.lb.config;

import com.damon.common.constant.ConfigConstants;
import com.damon.common.lb.chooser.IRuleChooser;
import com.damon.common.lb.chooser.impl.RoundRuleChooser;
import com.damon.common.lb.loadbalancer.IpLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-08 3:44
 */
@Slf4j
public class IpLoadBalancerConfig {

    private IRuleChooser defaultRuleChooser = null;

    @Bean
    @ConditionalOnMissingBean(IRuleChooser.class)
    @ConditionalOnProperty(prefix = ConfigConstants.CONFIG_LOADBALANCE_ISOLATION, value = "chooser")
    public IRuleChooser customRuleChooser(Environment environment, ApplicationContext context){

        IRuleChooser chooser = new RoundRuleChooser();
        if (environment.containsProperty(ConfigConstants.CONFIG_LOADBALANCE_ISOLATION_CHOOSER)) {
            String chooserRuleClassString = environment.getProperty(ConfigConstants.CONFIG_LOADBALANCE_ISOLATION_CHOOSER);
            if(StringUtils.isNotBlank(chooserRuleClassString)){
                try {
                    Class<?> ruleClass = ClassUtils.forName(chooserRuleClassString, context.getClassLoader());
                    chooser = (IRuleChooser) ruleClass.newInstance();
                } catch (ClassNotFoundException e) {
                    log.error("没有找到定义的选择器，将使用内置的选择器", e);
                } catch (InstantiationException e) {
                    log.error("没法创建定义的选择器，将使用内置的选择器", e);
                } catch (IllegalAccessException e) {
                    log.error("没法创建定义的选择器，将使用内置的选择器", e);
                }
            }
        }
        return chooser;
    }

    @Bean
    @ConditionalOnMissingBean(value = IRuleChooser.class)
    public IRuleChooser defaultRuleChooser(){
        return new RoundRuleChooser();
    }


    @Bean
    @ConditionalOnProperty(prefix = ConfigConstants.CONFIG_LOADBALANCE_ISOLATION, name = "enabled", havingValue = "true", matchIfMissing = false)
    public ReactorServiceInstanceLoadBalancer versionServiceLoadBalancer(Environment environment
            , LoadBalancerClientFactory loadBalancerClientFactory, IRuleChooser ruleChooser){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new IpLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class)
                , name, ruleChooser);
    }
}
