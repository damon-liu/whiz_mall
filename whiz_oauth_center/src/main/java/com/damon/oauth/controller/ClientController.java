package com.damon.oauth.controller;

import com.damon.oauth.pojo.Client;
import com.damon.oauth.pojo.ClientPage;
import com.damon.oauth.service.ClientService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 15:13
 */

@RestController
@RequestMapping("/oauth/client")
@Api(tags = "客户端")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/page")
    @ApiOperation(value = "分页列表")
    public PageInfo<Client> pageListClient(@Valid @ModelAttribute ClientPage clientPage) {
        return clientService.pageListClient(clientPage);
    }

}
