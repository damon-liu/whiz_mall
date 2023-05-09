package com.damon.common.lb.chooser;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-07 15:31
 */
public interface IRuleChooser {
    ServiceInstance choose(List<ServiceInstance> instances);
}