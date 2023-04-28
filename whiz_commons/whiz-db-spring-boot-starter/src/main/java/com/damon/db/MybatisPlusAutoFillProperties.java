package com.damon.db;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 6:51
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "whiz.mybatis-plus.auto-fill")
@RefreshScope
public class MybatisPlusAutoFillProperties {

    /**
     * 是否开启自动填充字段
     */
    private Boolean enabled = true;
    /**
     * 是否开启了插入填充
     */
    private Boolean enableInsertFill = true;
    /**
     * 是否开启了更新填充
     */
    private Boolean enableUpdateFill = true;
    /**
     * 创建时间字段名
     */
    private String createTimeField = "createTime";
    /**
     * 更新时间字段名
     */
    private String updateTimeField = "updateTime";

}
