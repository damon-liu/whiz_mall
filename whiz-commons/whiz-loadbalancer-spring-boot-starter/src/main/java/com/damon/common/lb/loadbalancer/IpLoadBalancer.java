package com.damon.common.lb.loadbalancer;

import com.damon.common.constant.CommonConstant;
import com.damon.common.lb.chooser.IRuleChooser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:自定义IP路由选择
 *
 * @author damon.liu
 * Date 2023-05-07 15:33
 */
@Slf4j
public class IpLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers;

    private String serviceId;

    private IRuleChooser ruleChooser;

    public IpLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers, String serviceId, IRuleChooser ruleChooser) {
        this.serviceInstanceListSuppliers = serviceInstanceListSuppliers;
        this.serviceId = serviceId;
        this.ruleChooser = ruleChooser;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        // 从request中获取版本，兼容webflux方式
        RequestData requestData = ((RequestDataContext) (request.getContext())).getClientRequest();
        String ip = getVersionFromRequestData(requestData);
        log.info("客户端指定的ip为：{}", ip);
        return serviceInstanceListSuppliers.getIfAvailable().get(request).next().map(instanceList -> getInstanceResponse(instanceList, ip));
    }

    private String getVersionFromRequestData(RequestData requestData) {
        if (requestData.getHeaders().containsKey(CommonConstant.LOCK_REMOTE_IP)) {
            return requestData.getHeaders().get(CommonConstant.LOCK_REMOTE_IP).get(0);
        }
        return null;
    }

    /**
     * 1. 先获取到拦截的版本，如果不为空的话就将service列表过滤，寻找metadata中哪个服务是配置的版本，
     * 如果版本为空则不需要进行过滤直接提交给service选择器进行选择
     * 2. 如果没有找到版本对应的实例，则找所有版本为空或者版本号为default的实例
     * 3.将instance列表提交到选择器根据对应的策略返回一个instance
     *
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, String lockIp) {
        // 获取客户端指定的访问ip
        List<ServiceInstance> filteredServiceInstanceList = Lists.newArrayList();
        if (StringUtils.isNotBlank(lockIp)) {
            if (CollectionUtils.isNotEmpty(instances)) {
                filteredServiceInstanceList = instances.stream().filter(item -> item.getHost().equals(lockIp)).collect(Collectors.toList());
            }
        }

        // 如果没有找到指定ip实例时，采用默认服务实例
        if (CollectionUtils.isEmpty(filteredServiceInstanceList)) {
            filteredServiceInstanceList = instances;
        }

        // 经过两轮过滤后如果能找到的话就通过选择器选择
        if (CollectionUtils.isNotEmpty(filteredServiceInstanceList)) {
            ServiceInstance serviceInstance = this.ruleChooser.choose(filteredServiceInstanceList);
            if (!Objects.isNull(serviceInstance)) {
                return new DefaultResponse(serviceInstance);
            }
        }

        // 返回空的返回体
        return new EmptyResponse();
    }
}
