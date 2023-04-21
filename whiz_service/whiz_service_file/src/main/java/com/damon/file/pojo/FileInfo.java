package com.damon.file.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * file实体类
 */
@EqualsAndHashCode(callSuper = false)
@Table(name = "file_info")
@Data
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    /**
     * 原始文件名
     */
    private String name;
    /**
     * 是否图片
     */
    private Boolean isImg;
    /**
     * 上传文件类型
     */
    private String contentType;
    /**
     * 文件大小
     */
    private Integer size;
    /**
     * 冗余字段
     */
    private String path;
    /**
     * oss访问路径 oss需要设置公共读
     */
    private String url;
    /**
     * FileType字段
     */
    private String source;

    private Date createTime;

    private Date updateTime;
}
