package com.damon.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.damon.common.core.enums.DataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 2:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends SuperEntity {
    private static final long serialVersionUID = 4497149010220586111L;
    private String code;
    private String name;
    @TableField(exist = false)
    private Long userId;
    /**
     * 数据权限字段
     */
    private DataScope dataScope;
    private Long creatorId;
}
