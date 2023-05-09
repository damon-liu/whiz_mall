package com.damon.base.service;


import com.damon.base.entity.SysUserPage;
import com.damon.common.entity.*;
import com.damon.common.service.ISuperService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 7:43
 */
public interface ISysUserService extends ISuperService<SysUser> {

    /**
     * 获取UserDetails对象
     * @param username
     * @return
     */
    LoginAppUser findByUsername(String username);

    LoginAppUser findByOpenId(String username);

    LoginAppUser findByMobile(String username);



    /**
     * 通过SysUser 转换为 LoginAppUser，把roles和permissions也查询出来
     * @param sysUser
     * @return
     */
    LoginAppUser getLoginAppUser(SysUser sysUser);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    SysUser selectByMobile(String mobile);

    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
    SysUser selectByOpenId(String openId);

    /**
     * 用户分配角色
     * @param id
     * @param roleIds
     */
    void setRoleToUser(Long id, Set<Long> roleIds);

    /**
     * 更新密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Result updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 用户列表
     * @param page
     * @return
     */
    PageInfo<SysUser> findUsers(SysUserPage page);


    /**
     * 用户角色列表
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 状态变更
     * @param params
     * @return
     */
    Result updateEnabled(Map<String, Object> params);

    // /**
    //  * 查询全部用户
    //  * @param params
    //  * @return
    //  */
    // List<SysUserExcel> findAllUsers(Map<String, Object> params);

    Result saveOrUpdateUser(SysUser sysUser) throws Exception;

    /**
     * 删除用户
     */
    boolean delUser(Long id);
}
