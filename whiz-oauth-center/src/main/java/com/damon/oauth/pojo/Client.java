package com.damon.oauth.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zlt
 */

@TableName("oauth_client_details")
@Data
public class Client implements Serializable {

    private static final long serialVersionUID = -8185413579135897885L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 应用标识
     */
    private String clientId;

    /**
     * 资源限定串(逗号分割)
     */
    private String resourceIds;

    /**
     * 应用密钥(bcyt)
     */
    private String clientSecret;

    /**
     * 应用密钥(明文)
     */
    private String clientSecretStr;

    /**
     * 范围
     */
    private String scope;

    /**
     * 5种oauth授权方式(authorization_code，password，refresh_token，client_credentials)
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限
     */
    private String authorities;

    /**
     * access_token有效期
     */
    private Integer accessTokenValidity;

    /**
     * refresh_token有效期
     */
    private Integer refreshTokenValidity;

    /**
     * {}
     */
    private String additionalInformation;

    /**
     * 是否自动授权 是-true
     */
    private String autoapprove;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * update_time
     */
    private Date updateTime;

    /**
     * 应用名称
     */
    private String clientName;

    /**
     * 是否支持id_token
     */
    private int supportIdToken;

    /**
     * id_token有效期
     */
    private Integer idTokenValidity;

    /**
     * 创建人id
     */
    private Integer creatorId;
}
