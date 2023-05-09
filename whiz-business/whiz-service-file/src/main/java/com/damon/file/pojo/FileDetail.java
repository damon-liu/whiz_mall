package com.damon.file.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-05 8:40
 */
@Data
public class FileDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 对象查看路径
     */
    private String objectUrl;
    /**
     * 对象保存路径
     */
    private String objectPath;
}