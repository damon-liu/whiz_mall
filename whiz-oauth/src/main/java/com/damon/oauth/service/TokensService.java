package com.damon.oauth.service;

import com.damon.oauth.pojo.TokenVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-11 8:34
 */
public interface TokensService {

    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageInfo<TokenVo> listTokens(Map<String, Object> params, String clientId);

}
