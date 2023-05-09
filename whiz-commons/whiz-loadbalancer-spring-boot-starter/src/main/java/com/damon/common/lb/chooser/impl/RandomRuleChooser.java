package com.damon.common.lb.chooser.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.damon.common.lb.chooser.IRuleChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-07 15:32
 */
@Slf4j
public class RandomRuleChooser implements IRuleChooser {
    @Override
    public ServiceInstance choose(List<ServiceInstance> instances) {
        if(CollectionUtils.isNotEmpty(instances)){
            int randomValue = ThreadLocalRandom.current().nextInt(instances.size());
            ServiceInstance serviceInstance = instances.get(randomValue);
            log.info("随机选择器指定的ip为：{}, 端口为：{}", serviceInstance.getHost(), serviceInstance.getPort());
            return serviceInstance;
        }
        return null;
    }
}