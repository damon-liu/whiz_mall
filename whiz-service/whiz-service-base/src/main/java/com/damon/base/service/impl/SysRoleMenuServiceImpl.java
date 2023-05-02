package com.damon.base.service.impl;

import com.damon.base.entity.SysRoleMenu;
import com.damon.base.mapper.SysRoleMenuMapper;
import com.damon.base.service.ISysRoleMenuService;
import com.damon.common.entity.SysMenu;
import com.damon.common.service.impl.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 8:11
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends SuperServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int save(Long roleId, Long menuId) {
        return sysRoleMenuMapper.save(roleId, menuId);
    }

    @Override
    public int delete(Long roleId, Long menuId) {
        return sysRoleMenuMapper.delete(roleId, menuId);
    }

    @Override
    public List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type) {
        return sysRoleMenuMapper.findMenusByRoleIds(roleIds, type);
    }

    @Override
    public List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type) {
        return sysRoleMenuMapper.findMenusByRoleCodes(roleCodes, type);
    }
}
