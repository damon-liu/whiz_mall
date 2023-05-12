package com.damon.common.oauth.token;

import lombok.Getter;

/**
 * Description: 表单登录的认证信息对象
 *
 * @author damon.liu
 * Date 2023-05-10 4:26
 */
@Getter
public class CustomWebAuthenticationDetails {

    private static final long serialVersionUID = - 1;

    private final String accountType;
    private final String remoteAddress;
    private final String sessionId;

    public CustomWebAuthenticationDetails(String remoteAddress, String sessionId, String accountType) {
        this.remoteAddress = remoteAddress;
        this.sessionId = sessionId;
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; accountType: ").append(this.getAccountType());
        return sb.toString();
    }
}
