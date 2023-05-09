package com.damon.base.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.damon.base.entity.SysRolePage;
import com.damon.base.mapper.SysRoleMapper;
import com.damon.base.mapper.SysRoleMenuMapper;
import com.damon.base.mapper.SysUserRoleMapper;
import com.damon.base.service.ISysRoleService;
import com.damon.common.component.lock.DistributedLock;
import com.damon.common.entity.Result;
import com.damon.common.entity.SysRole;
import com.damon.common.service.impl.SuperServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:55
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends SuperServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final static String LOCK_KEY_ROLECODE = "rolecode:";

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRole(SysRole sysRole) throws Exception {
        String roleCode = sysRole.getCode();
        super.saveIdempotency(sysRole, lock
                , LOCK_KEY_ROLECODE + roleCode, new QueryWrapper<SysRole>().eq("code", roleCode), "角色code已存在");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long id) {
        baseMapper.deleteById(id);
        roleMenuMapper.delete(id, null);
        userRoleMapper.deleteUserRole(null, id);
    }

    @Override
    public PageInfo<SysRole> findRoles(SysRolePage page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<SysRole> list = roleMapper.findList(page);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public Result saveOrUpdateRole(SysRole sysRole) throws Exception {
        if (sysRole.getId() == null) {
            // sysRole.setCreatorId(LoginUserContextHolder.getUser().getId());
            this.saveRole(sysRole);
        } else {
            baseMapper.updateById(sysRole);
        }
        return Result.succeed("操作成功");
    }

    @Override
    public List<SysRole> findAll() {
        return roleMapper.findAll();
    }

}
