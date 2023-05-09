package com.damon.account.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description:
 * @author damon.liu
 * Date 2023-05-06 7:15
 */
@Data
@Accessors(chain = true)
@TableName("account_tbl")
public class Account {
    @TableId
    private Long id;
    private String userId;
    private Integer money;
}
