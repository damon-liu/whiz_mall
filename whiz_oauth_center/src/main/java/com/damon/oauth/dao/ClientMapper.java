package com.damon.oauth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damon.db.mapper.SuperMapper;
import com.damon.oauth.pojo.Client;
import com.damon.oauth.pojo.ClientPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 4:16
 */
public interface ClientMapper extends BaseMapper<Client> {

    List<Client> findList(ClientPage clientPage);

}
