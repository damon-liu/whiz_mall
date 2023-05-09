package com.damon.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-06 7:26
 */
@Data
@Accessors(chain = true)
@TableName("order_tbl")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;
}
