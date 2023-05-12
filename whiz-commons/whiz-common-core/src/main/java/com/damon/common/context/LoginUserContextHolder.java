package com.damon.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.damon.common.entity.SysUser;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 3:45
 */
public class LoginUserContextHolder {

    private static final ThreadLocal<SysUser> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(SysUser user) {
        CONTEXT.set(user);
    }

    public static SysUser getUser() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
