package com.damon.oauth.dao;

import com.damon.oauth.pojo.Client;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 4:16
 */
public interface ClientMapper extends Mapper<Client> {

    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );

}
