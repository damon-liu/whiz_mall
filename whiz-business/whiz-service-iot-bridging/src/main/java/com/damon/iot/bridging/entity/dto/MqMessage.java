package com.damon.iot.bridging.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangpeng
 * @title: MqMessage
 * @date 2020-05-2615:56
 */
@Data
@Builder
public class MqMessage implements Serializable {

    private String topic;

    private Long serverRecvTime;

    private byte[] payload;

    private String remark;
}