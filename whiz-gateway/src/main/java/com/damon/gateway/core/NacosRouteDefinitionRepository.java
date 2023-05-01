package com.damon.gateway.core;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.damon.comon.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Description: nacos路由数据源
 *
 * @author damon.liu
 * Date 2023-05-01 9:15
 */

@Slf4j
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final String WHIZ_DATA_ID = "whiz-gateway";
    private static final String WHIZ_GROUP_ID = "WHIZ_GROUP";

    private ApplicationEventPublisher publisher;

    private NacosConfigProperties nacosConfigProperties;

    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigProperties nacosConfigProperties) {
        this.publisher = publisher;
        this.nacosConfigProperties = nacosConfigProperties;
        addListener();
    }

    // 重写 getRouteDefinitions 方法实现路由信息的读取
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosConfigProperties.getServerAddr());
            String content = configService.getConfig(WHIZ_DATA_ID, WHIZ_GROUP_ID, 5000);
            List<RouteDefinition> routeDefinitions = getListByStr(content);
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            log.error("getRouteDefinitions by nacos error", e);
        }
        return Flux.fromIterable(new ArrayList<>());
    }

    /**
     * 添加Nacos监听
     */
    private void addListener() {
        try {
            nacosConfigProperties.configServiceInstance().addListener(WHIZ_DATA_ID, WHIZ_GROUP_ID, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }
                // 路由变化只需要往 ApplicationEventPublisher 推送一个 RefreshRoutesEvent 事件即可，gateway会自动监听该事件并调用 getRouteDefinitions 方法更新路由信息
                @Override
                public void receiveConfigInfo(String configInfo) {
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
        }
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    private List<RouteDefinition> getListByStr(String content) {
        if (StrUtil.isNotEmpty(content)) {
            return JsonUtil.toList(content, RouteDefinition.class);
        }
        return new ArrayList<>(0);
    }
}
