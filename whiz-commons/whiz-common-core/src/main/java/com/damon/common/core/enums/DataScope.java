package com.damon.common.core.enums;

import lombok.Getter;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 2:14
 */
@Getter
public enum DataScope implements EnuService{

    ALL(0, "全部权限"), CREATOR(1, "创建者权限");

    DataScope(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    private Integer id;
    private String content;
}
