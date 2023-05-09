package com.damon.base.service;

import com.damon.base.entity.SysRoleUser;
import com.damon.common.entity.SysRole;
import com.damon.common.service.ISuperService;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 8:14
 */
public interface ISysRoleUserService extends ISuperService<SysRoleUser> {

    int deleteUserRole(Long userId, Long roleId);

    int saveUserRoles(Long userId, Long roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    List<SysRole> findRolesByUserIds(List<Long> userIds);
}
