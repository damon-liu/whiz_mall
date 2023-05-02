package com.damon.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damon.base.entity.SysRoleMenu;
import com.damon.base.mapper.SysMenuMapper;
import com.damon.base.service.ISysMenuService;
import com.damon.base.service.ISysRoleMenuService;
import com.damon.common.entity.SysMenu;
import com.damon.common.service.impl.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:58
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends SuperServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private ISysRoleMenuService roleMenuService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setMenuToRole(Long roleId, Set<Long> menuIds) {
        roleMenuService.delete(roleId, null);

        if (!CollectionUtils.isEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
            menuIds.forEach(menuId -> roleMenus.add(new SysRoleMenu(roleId, menuId)));
            roleMenuService.saveBatch(roleMenus);
        }
    }

    /**
     * 角色菜单列表
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<SysMenu> findByRoles(Set<Long> roleIds) {
        return roleMenuService.findMenusByRoleIds(roleIds, null);
    }

    /**
     * 角色菜单列表
     *
     * @param roleIds 角色ids
     * @param roleIds 是否菜单
     * @return
     */
    @Override
    public List<SysMenu> findByRoles(Set<Long> roleIds, Integer type) {
        return roleMenuService.findMenusByRoleIds(roleIds, type);
    }

    @Override
    public List<SysMenu> findByRoleCodes(Set<String> roleCodes, Integer type) {
        return roleMenuService.findMenusByRoleCodes(roleCodes, type);
    }

    /**
     * 查询所有菜单
     */
    @Override
    public List<SysMenu> findAll() {
        return baseMapper.selectList(
                new QueryWrapper<SysMenu>().orderByAsc("sort")
        );
    }

    /**
     * 查询所有一级菜单
     */
    @Override
    public List<SysMenu> findOnes() {
        return baseMapper.selectList(
                new QueryWrapper<SysMenu>()
                        .eq("type", 1)
                        .orderByAsc("sort")
        );
    }
}
