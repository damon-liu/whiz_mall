package com.damon.comon.component.banner;

import com.damon.comon.core.Constants;
import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.taobao.text.Color;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description:
 * banner在线生成地址：http://patorjk.com/software/taag/#p=display&f=Graffiti&t=WHIZ-MALL
 *
 * @author damon.liu
 * Date 2023-05-01 8:30
 */
public class BannerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            LogoBanner logoBanner = new LogoBanner(BannerInitializer.class, "/banner/banner.txt", "Welcome to whiz", 5, 6, new Color[5], true);
            CustomBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":", Constants.PROJECT_VERSION, 0, 1)
            );
        }
    }
}
