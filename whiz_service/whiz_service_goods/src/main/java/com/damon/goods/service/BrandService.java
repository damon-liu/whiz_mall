package com.damon.goods.service;

import com.damon.goods.pojo.Brand;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-10 2:49
 */
public interface BrandService {

    List<Brand> findAll();


    Brand findById(Integer id);


    void add(Brand brand);

    void update(Brand brand);

    void delete(Integer id);

    List<Brand> findList(Map<String, Object> searchMap);

    Page<Brand> findPage(int page, int size);

    Page<Brand> findPage(Map<String, Object> searchMap, int page, int size);

}
