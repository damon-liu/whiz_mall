package com.damon.oauth.service.impl;


import com.damon.oauth.dao.ClientMapper;
import com.damon.oauth.pojo.Client;
import com.damon.oauth.pojo.ClientPage;
import com.damon.oauth.service.ClientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 5:48
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public PageInfo<Client> pageListClient(ClientPage clientPage) {
        PageHelper.startPage(clientPage.getPageNum(), clientPage.getPageSize());
        List<Client> list = clientMapper.findList(clientPage);
        return new PageInfo<>(list);
    }
}
