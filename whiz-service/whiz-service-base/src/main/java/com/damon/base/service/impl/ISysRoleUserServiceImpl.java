package com.damon.base.service.impl;

import com.damon.base.entity.SysRoleUser;
import com.damon.base.mapper.SysUserRoleMapper;
import com.damon.base.service.ISysRoleUserService;
import com.damon.common.entity.SysRole;
import com.damon.common.service.impl.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 8:14
 */
@Slf4j
@Service
public class ISysRoleUserServiceImpl extends SuperServiceImpl<SysUserRoleMapper, SysRoleUser> implements ISysRoleUserService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteUserRole(Long userId, Long roleId) {
        return sysUserRoleMapper.deleteUserRole(userId, roleId);
    }

    @Override
    public int saveUserRoles(Long userId, Long roleId) {
        return sysUserRoleMapper.saveUserRoles(userId, roleId);
    }

    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return sysUserRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public List<SysRole> findRolesByUserIds(List<Long> userIds) {
        return sysUserRoleMapper.findRolesByUserIds(userIds);
    }
}
