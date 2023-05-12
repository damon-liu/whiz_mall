package com.damon.gateway.feign;

import com.damon.common.entity.SysMenu;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-11 7:35
 */
@Component
public class AsynMenuService {

    @Lazy
    @Resource
    private MenuService menuService;

    @Async
    public Future<List<SysMenu>> findByRoleCodes(String roleCodes) {
        List<SysMenu> result = menuService.findByRoleCodes(roleCodes);
        return new AsyncResult<>(result);
    }
}
