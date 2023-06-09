package iot.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 3:15
 */
@Data
@Builder
public class RbtMqMessage implements Serializable {

    private String topic;

    private Long serverRecvTime;

    private byte[] payload;

    private String remark;
}

