package com.damon.oauth.service;

import com.damon.entity.PageResult;
import com.damon.oauth.pojo.Client;

import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 5:47
 */
public interface ClientService {

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

}
