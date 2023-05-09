package com.damon.base.service;

import com.damon.base.entity.SysRoleMenu;
import com.damon.common.entity.SysMenu;
import com.damon.common.service.ISuperService;

import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 8:05
 */
public interface ISysRoleMenuService extends ISuperService<SysRoleMenu> {

    int save(Long roleId, Long menuId);

    int delete(Long roleId, Long menuId);

    List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type);

    List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type);
}
