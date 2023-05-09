package com.damon.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damon.oauth.pojo.Client;
import com.damon.oauth.pojo.ClientPage;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 4:16
 */
public interface ClientMapper extends BaseMapper<Client> {

    List<Client> findList(ClientPage clientPage);

}
