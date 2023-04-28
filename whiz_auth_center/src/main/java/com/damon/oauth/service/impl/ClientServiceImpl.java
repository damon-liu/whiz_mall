package com.damon.oauth.service.impl;


import com.damon.entity.PageResult;
import com.damon.oauth.dao.ClientMapper;
import com.damon.oauth.pojo.Client;
import com.damon.oauth.service.ClientService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

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
    public PageResult<Client> listClient(Map<String, Object> params, boolean isPage) {
        Page<Client> page;
        if (isPage) {
            page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        } else {
            page = new Page<>(1, -1);
        }
        Example example = createExample(params);
        List<Client> list = clientMapper.findList(page, params);
        return (Page<Brand>) brandMapper.selectByExample(example);
    }

    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Client.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 品牌名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 品牌的首字母
            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
                criteria.andEqualTo("letter", searchMap.get("letter"));
            }
        }
        return example;
    }
}
