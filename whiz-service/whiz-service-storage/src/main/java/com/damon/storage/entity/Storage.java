package com.damon.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 库存
 *
 * @author zlt
 * @date 2019/9/14
 */


@Data
@Accessors(chain = true)
@TableName("storage_tbl")
public class Storage {


    @TableId(type = IdType.AUTO)
    private Integer id;
    private String commodityCode;
    private Integer count;
}
