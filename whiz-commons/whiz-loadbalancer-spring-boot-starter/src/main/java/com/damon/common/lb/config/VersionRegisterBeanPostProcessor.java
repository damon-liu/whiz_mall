package com.damon.common.lb.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.common.utils.StringUtils;
import com.damon.common.constant.CommonConstant;
import com.damon.common.constant.ConfigConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-09 1:15
 */
public class VersionRegisterBeanPostProcessor implements BeanPostProcessor {

    @Value("${"+ ConfigConstants.CONFIG_LOADBALANCE_VERSION+":}")
    private String version;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof NacosDiscoveryProperties && StringUtils.isNotBlank(version)){
            NacosDiscoveryProperties nacosDiscoveryProperties = (NacosDiscoveryProperties) bean;
            nacosDiscoveryProperties.getMetadata().putIfAbsent(CommonConstant.METADATA_VERSION, version);
        }
        return bean;
    }

}
