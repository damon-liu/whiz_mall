package com.damon.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.damon.db.MybatisPlusAutoFillProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 8:30
 */
@EnableConfigurationProperties({MybatisPlusAutoFillProperties.class})
public class MybatisPlusAutoConfigure {

    @Autowired
    private MybatisPlusAutoFillProperties autoFillProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "zlt.mybatis-plus.auto-fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(autoFillProperties);
    }
}
