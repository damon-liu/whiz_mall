package com.damon.account.controller;

import com.damon.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-06 7:19
 */
@Slf4j
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 账号扣钱
     */
    @PostMapping(value = "/account/reduce")
    public Boolean reduce(String userId, Integer money) {
        accountService.reduce(userId, money);
        return true;
    }
}
