package com.damon.storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damon.storage.entity.Storage;
import com.damon.storage.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zlt
 * @date 2019/9/14
 */
@Slf4j
@Service
public class StorageService {
    @Autowired
    private StorageMapper storageMapper;

    /**
     * 减库存
     * 
     * @param commodityCode 商品编号
     * @param count 数量
     */
    //@Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageMapper.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);
        storageMapper.updateById(storage);
    }
}
