package com.damon.file.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-18 15:30
 */

@Setter
@Getter
public class S3Properties {

    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String accessKeySecret;
    /**
     * 访问端点
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 区域
     */
    private String region;
    /**
     * path-style
     */
    private Boolean pathStyleAccessEnabled = true;

}
