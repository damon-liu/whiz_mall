package com.damon.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * Description: 父类，注意这个类不要让 mp 扫描到！！
 *
 * @author damon.liu
 * Date 2023-04-28 7:25
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以放一些公共的方法


}
