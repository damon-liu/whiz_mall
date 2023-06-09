package com.damon.common.feign;

import com.damon.common.constant.ServiceNameConstants;
import com.damon.common.entity.LoginAppUser;
import com.damon.common.entity.SysRole;
import com.damon.common.entity.SysUser;
import com.damon.common.feign.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 2:15
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {

    /**
     * feign rpc访问远程/feign/sysUser/{username}接口
     * 查询用户实体对象SysUser
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/feign/sysUser/userByName/{username}")
    SysUser selectByUsername(@PathVariable("username") String username);

    /**
     * feign rpc访问远程/feign/sysUser-anon/login接口
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/feign/sysUser/userDtoByName", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/feign/sysUser/mobile", params = "mobile")
    LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/feign/sysUser/openId", params = "openId")
    LoginAppUser findByOpenId(@RequestParam("openId") String openId);


    /**
     * 获取带角色的用户信息
     * @param username
     * @return
     */
    @GetMapping(value = "/feign/sysUser/roleUser/{username}")
    SysUser selectRoleUser(@PathVariable("username") String username);

    /**
     * 获取用户的角色
     *
     * @param
     * @return
     */
    @GetMapping("/feign/sysUser/{id}/roles")
    List<SysRole> findRolesByUserId(@PathVariable("id") Long id);
}
