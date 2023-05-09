package com.damon.business.controller;


import com.damon.business.service.BusinessService;
import com.damon.common.constant.CommonConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author zlt
 * @date 2019/9/14
 */
@RestController
public class BusinessController {
    @Resource
    private BusinessService businessService;

    /**
     * 下单场景测试-正常
     */
    @RequestMapping(path = "/placeOrder")
    public Boolean placeOrder() {
        HttpServletRequest httpServletRequest = getHeader();
        String lockIp = httpServletRequest.getHeader(CommonConstant.LOCK_REMOTE_IP);
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        businessService.placeOrder("U001");
        return true;
    }

    /**
     * 下单场景测试-回滚
     */
    @RequestMapping(path = "/placeOrderFallBack")
    public Boolean placeOrderFallBack() {
        businessService.placeOrder("U002");
        return true;
    }

    /**
     * 全局中获取请求头中信息
     * @return
     */
    public static HttpServletRequest getHeader() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request;
        }
        return null;
    }
}
