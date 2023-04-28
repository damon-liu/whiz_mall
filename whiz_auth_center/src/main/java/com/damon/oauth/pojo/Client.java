package com.damon.oauth.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zlt
 */

@Table(name="oauth_client_details")
@Data
public class Client implements Serializable {

   private static final long serialVersionUID = -8185413579135897885L;
   // private String clientId;
   // /**
   //  * 应用名称
   //  */
   // private String clientName;
   // private String resourceIds = "";
   // private String clientSecret;
   // private String clientSecretStr;
   // private String scope = "all";
   // private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
   // private String webServerRedirectUri;
   // private String authorities = "";
   // @TableField(value = "access_token_validity")
   // private Integer accessTokenValiditySeconds = 18000;
   // @TableField(value = "refresh_token_validity")
   // private Integer refreshTokenValiditySeconds = 28800;
   // private String additionalInformation = "{}";
   // private String autoapprove = "true";
   // /**
   //  * 是否支持id_token
   //  */
   // private Boolean supportIdToken = true;
   // /**
   //  * id_token有效时间(s)
   //  */
   // @TableField(value = "id_token_validity")
   // private Integer idTokenValiditySeconds = 60;
   //
   // private Long creatorId;

   /**
    * 主键
    */
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
