package com.damon.base.service;


import com.damon.base.entity.SysRolePage;
import com.damon.common.entity.Result;
import com.damon.common.entity.SysRole;
import com.damon.common.service.ISuperService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:50
 */
public interface ISysRoleService extends ISuperService<SysRole> {

    void saveRole(SysRole sysRole) throws Exception;

    void deleteRole(Long id);

    /**
     * 角色列表
     *
     * @param page
     * @return
     */
    PageInfo<SysRole> findRoles(SysRolePage page);

    /**
     * 新增或更新角色
     *
     * @param sysRole
     * @return Result
     */
    Result saveOrUpdateRole(SysRole sysRole) throws Exception;

    /**
     * 查询所有角色
     *
     * @return
     */
    List<SysRole> findAll();
}
