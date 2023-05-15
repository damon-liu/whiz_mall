package com.damon.base.mangage.controller;

import com.damon.base.service.ISysUserService;
import com.damon.common.entity.LoginAppUser;
import com.damon.common.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-12 7:10
 */
@RestController
@RequestMapping("/feign/sysUser")
public class UserFeignController {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询用户实体对象SysUser
     */
    @GetMapping(value = "/userByName/{username}")
    public SysUser selectByUsername(@PathVariable String username) {
        return sysUserService.findByUsername(username);
    }

    /**
     * 查询用户登录对象LoginUserDto
     */
    @GetMapping(value = "/userDtoByName")
    public LoginAppUser findByUsername(@RequestParam String username) {
        return sysUserService.findByUsername(username);
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/openId", params = "openId")
    public LoginAppUser findByOpenId(String openId) {
        return sysUserService.findByOpenId(openId);
    }

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/mobile", params = "mobile")
    public LoginAppUser findByMobile(String mobile) {
        return sysUserService.findByMobile(mobile);
    }
}
