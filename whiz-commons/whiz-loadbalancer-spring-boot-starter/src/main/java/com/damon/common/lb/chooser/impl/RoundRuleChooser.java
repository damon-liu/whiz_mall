package com.damon.common.lb.chooser.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.damon.common.lb.chooser.IRuleChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 轮询选择器
 *
 * @author damon.liu
 * Date 2023-05-07 15:31
 */
@Slf4j
public class RoundRuleChooser implements IRuleChooser {

    private AtomicInteger position;

    public RoundRuleChooser() {
        this.position = new AtomicInteger(1000);
    }

    @Override
    public ServiceInstance choose(List<ServiceInstance> instances) {
        if(CollectionUtils.isNotEmpty(instances)){
            ServiceInstance serviceInstance = instances.get(Math.abs(position.incrementAndGet() % instances.size()));
            log.info("轮训选择器指定ip为：{}, 端口：{}", serviceInstance.getHost(), serviceInstance.getPort());
            return serviceInstance;
        }
        return null;
    }
}
