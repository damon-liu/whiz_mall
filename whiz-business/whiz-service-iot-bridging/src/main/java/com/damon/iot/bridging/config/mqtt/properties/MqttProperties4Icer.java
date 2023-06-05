package com.damon.iot.bridging.config.mqtt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 9:26
 */
@ConfigurationProperties(prefix = "spring.mqtt")
@Setter
@Getter
@Configuration
public class MqttProperties4Icer extends MqttProperties {
}
