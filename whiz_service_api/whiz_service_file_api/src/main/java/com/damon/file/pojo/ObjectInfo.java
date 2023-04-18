package com.damon.file.pojo;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ObjectInfo {
    /**
     * 对象查看路径
     */
    private String objectUrl;
    /**
     * 对象保存路径
     */
    private String objectPath;
}
