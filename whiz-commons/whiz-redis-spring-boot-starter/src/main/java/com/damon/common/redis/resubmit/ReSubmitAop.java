package com.damon.common.redis.resubmit;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.damon.comon.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Description:解决重复提交问题
 *
 * @author damon.liu
 * Date 2023-04-30 8:40
 */
@Aspect
@Component
@Order(0)
public class ReSubmitAop {

    @Autowired
    public HttpServletRequest request;
    @Autowired
    public RedisTemplate redisTemplate;
    public static final String HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse";
    public static final String HTTP_SERVLET_RESPONSE_IMPL = "org.apache.catalina.connector.ResponseFacade";
    public static final String HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";
    public static final String HTTP_SERVLET_REQUEST_IMPL = "org.apache.catalina.connector.RequestFacade";
    public static final String HTTP_SERVLET_MULTIPART = "org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile";
    /**
     * 忽略的类型
     */
    private Set<String> ignoreClasses;

    public ReSubmitAop() {
        ignoreClasses = new HashSet<>(6);
        ignoreClasses.add(HTTP_SERVLET_REQUEST);
        ignoreClasses.add(HTTP_SERVLET_REQUEST_IMPL);
        ignoreClasses.add(HTTP_SERVLET_RESPONSE);
        ignoreClasses.add(HTTP_SERVLET_RESPONSE_IMPL);
        ignoreClasses.add(HTTP_SERVLET_MULTIPART);
    }

    private boolean isIgnoreClass(Class<?> clazz) {
        return ignoreClasses.contains(clazz.getName());
    }

    @Pointcut("@annotation(reSubmitAnno)")
    public void initLogAnno(ReSubmitAnno reSubmitAnno) {
    }

    @Before("initLogAnno(reSubmitAnno)")
    public void doBefore(JoinPoint point, ReSubmitAnno reSubmitAnno) {
        String resubmitKey;
        // 通过参数 防重复提交
        if (reSubmitAnno.keyByParam()) {
            // 参数值
            Object[] args = point.getArgs();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                //处理入参敏感字段过滤
                if (arg != null) {
                    String name = arg.getClass().getName();
                }
                if (arg == null || isIgnoreClass(arg.getClass())) {
                    continue;
                }
                sb.append(JSON.toJSONString(arg));
            }
            resubmitKey = request.getMethod() + "/" + request.getRequestURI() + sb.toString();
            String ipAddress = IpUtil.getIpAddress(request);
            resubmitKey = SecureUtil.md5(resubmitKey + ipAddress);
        } else {
            resubmitKey = request.getMethod() + "/" + request.getRequestURI();
            String ipAddress = IpUtil.getIpAddress(request);
            if (StringUtils.isBlank(ipAddress)) {
                resubmitKey += ":" + headerKey();
            } else {
                resubmitKey += ":" + ipAddress;
            }
            resubmitKey = SecureUtil.md5(resubmitKey);
        }
        boolean flag = redisTemplate.opsForValue().setIfAbsent(resubmitKey, "1", reSubmitAnno.times(), TimeUnit.SECONDS);
        if (!flag) {
            throw new RuntimeException("操作过于频繁，请勿重复提交！");
        }
    }

    private String headerKey() {
        //构建Header对象
        Enumeration<String> headerNames = request.getHeaderNames();
        Map headers = new HashMap();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String header = request.getHeader(key);
            headers.put(key, header);
        }
        return SecureUtil.md5(headers.toString());
    }
}
