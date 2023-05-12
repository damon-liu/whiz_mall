package com.damon.oauth.service;

import com.damon.oauth.pojo.PayloadDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import java.text.ParseException;



/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 7:03
 */
public interface JwtTokenService {

    /**
     * 使用RSA算法生成token
     */
    String generateTokenByRSA(String payloadStr, RSAKey rsaKey) throws JOSEException;

    /**
     * 使用RSA算法校验token
     */
    PayloadDto verifyTokenByRSA(String token, RSAKey rsaKey) throws ParseException, JOSEException;

    /**
     * 获取默认payload
     */
    PayloadDto getDefaultPayloadDto();

    /**
     * 获取默认的RSAKey
     */
    RSAKey getDefaultRSAKey();
}
