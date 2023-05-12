package com.damon.oauth.controller;

import cn.hutool.json.JSONUtil;
import com.damon.common.entity.Result;
import com.damon.oauth.pojo.PayloadDto;
import com.damon.oauth.service.JwtTokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-10 6:37
 */
@RestController
@RequestMapping("/keyPair")
public class KeyPairController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @ApiOperation("使用非对称加密（RSA）算法生成token")
    @RequestMapping(value = "/rsa/generate", method = RequestMethod.GET)
    @ResponseBody
    public Result generateTokenByRSA() {
        try {
            PayloadDto payloadDto = jwtTokenService.getDefaultPayloadDto();
            String token = jwtTokenService.generateTokenByRSA(JSONUtil.toJsonStr(payloadDto), jwtTokenService.getDefaultRSAKey());
            return Result.succeed(token);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return Result.failed();
    }

    @ApiOperation("使用非对称加密（RSA）算法验证token")
    @RequestMapping(value = "/rsa/verify", method = RequestMethod.GET)
    @ResponseBody
    public Result verifyTokenByRSA(String token) {
        try {
            PayloadDto payloadDto = jwtTokenService.verifyTokenByRSA(token, jwtTokenService.getDefaultRSAKey());
            return Result.succeed(payloadDto);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return Result.failed();
    }

    @ApiOperation("获取非对称加密（RSA）算法公钥")
    @RequestMapping(value = "/rsa/publicKey", method = RequestMethod.GET)
    @ResponseBody
    public Object getRSAPublicKey() {
        RSAKey key = jwtTokenService.getDefaultRSAKey();
        return new JWKSet(key).toJSONObject();
    }
}
