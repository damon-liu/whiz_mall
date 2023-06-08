package com.damon.iot.bridging.utils.enums;

import lombok.Getter;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 9:33
 */
@Getter
public enum IotServiceType {
    /**
     * 充电
     */
    RBT("rbt"),
    ICER("icer"),
    ALL("all"),
    ;
    private final String val;

    IotServiceType(String val) {
        this.val = val;
    }
}
