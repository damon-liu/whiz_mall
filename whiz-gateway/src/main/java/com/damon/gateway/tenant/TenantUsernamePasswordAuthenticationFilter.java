package com.damon.gateway.tenant;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-11 14:13
 */
// @Setter
// public class TenantUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//     private boolean postOnly = true;
//
//     public TenantUsernamePasswordAuthenticationFilter () {
//         super();
//     }
//
//     @Override
//     public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//         if (postOnly && !request.getMethod().equals("POST")) {
//             throw new AuthenticationServiceException(
//                     "Authentication method not supported: " + request.getMethod());
//         }
//
//         String username = obtainUsername(request);
//         String password = obtainPassword(request);
//         String clientId = TenantContextHolder.getTenant();
//
//         if (username == null) {
//             username = "";
//         }
//
//         if (password == null) {
//             password = "";
//         }
//
//         username = username.trim();
//
//         TenantUsernamePasswordAuthenticationToken authRequest = new TenantUsernamePasswordAuthenticationToken(username, password, clientId);
//
//         setDetails(request, authRequest);
//
//         return getAuthenticationManager().authenticate(authRequest);
//     }
// }
