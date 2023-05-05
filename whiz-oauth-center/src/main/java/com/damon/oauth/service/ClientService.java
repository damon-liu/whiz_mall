package com.damon.oauth.service;

import com.damon.oauth.pojo.Client;
import com.damon.oauth.pojo.ClientPage;
import com.github.pagehelper.PageInfo;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 5:47
 */
public interface ClientService {

    /**
     * 查询应用列表
     *
     * @param clientPage
     */
    PageInfo<Client> pageListClient(ClientPage clientPage);

}
