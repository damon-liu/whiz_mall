package com.damon.iot.bridging.entity;

import lombok.Data;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-05 6:39
 */
@Data
public class PublishMsg {

    private String topic;

    private Integer qos;

    private Boolean retained;

    private String msg;
}
