package com.damon.file.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-18 15:28
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = FileServerProperties.PREFIX)
public class FileServerProperties {

    public static final String PREFIX = "whiz.file";

    public static final String PREFIX_S3= "whiz.file.s3";

    public static final String TYPE_FDFS = "fastdfs";

    public static final String TYPE_S3 = "s3";
    public static final String TYPE_S3_AWS = "aws";
    public static final String TYPE_S3_ALI_YUN = "aliYun";
    public static final String TYPE_S3_7NIU_YUN = "qiNiuYun";

    /**
     * 为以下2个值，指定不同的自动化配置
     * s3：aws s3协议的存储（七牛oss、阿里云oss、minio等）
     * fastdfs：本地部署的fastDFS
     */
    private String type;

    /**
     * aws s3配置
     */
    S3Properties s3 = new S3Properties();

    /**
     * fastDFS配置
     */
    FdfsProperties fdfs = new FdfsProperties();
}
