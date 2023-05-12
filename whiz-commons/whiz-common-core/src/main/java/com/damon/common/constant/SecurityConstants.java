package com.damon.common.constant;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 9:23
 */
public interface SecurityConstants {

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userid-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 租户信息头(应用)
     */
    String TENANT_HEADER = "x-tenant-header";


    /**
     * 账号类型信息头
     */
    String ACCOUNT_TYPE_HEADER = "x-account-type-header";

    /**
     * rsa公钥
     */
    String RSA_PUBLIC_KEY = "pubkey.txt";

    /**
     * 账号类型参数名
     */
    String ACCOUNT_TYPE_PARAM_NAME = "account_type";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";

    /**
     * 登出URL
     */
    String LOGOUT_URL = "/oauth/remove/token";

    /**
     * 登录页面
     */
    String LOGIN_PAGE = "/login.html";

    /**
     * 登录失败页面
     */
    String LOGIN_FAILURE_PAGE = LOGIN_PAGE + "?error";

    /**
     * OAUTH模式登录处理地址
     */
    String OAUTH_LOGIN_PRO_URL = "/user/login";

    /**
     * 默认token过期时间(1小时)
     */
    Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";
    /**
     * 值同access 过期时间+60
     */
    String ACCESS_BAK = "access_bak:";
    /**
     * redis中应用对应的token集合的key
     */
    String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    /**
     * redis中用户名对应的token集合的key
     */
    String REDIS_UNAME_TO_ACCESS = "uname_to_access:";
}
