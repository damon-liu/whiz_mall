package com.damon.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.damon.base.entity.SysRolePage;
import com.damon.common.entity.SysRole;
import com.damon.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:51
 */
public interface SysRoleMapper extends SuperMapper<SysRole> {

    List<SysRole> findList(@Param("r") SysRolePage page);

    List<SysRole> findAll();
}
