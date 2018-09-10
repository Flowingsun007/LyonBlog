package com.flowingsun.common.security;

import com.flowingsun.common.dao.RedisDAO;
import com.flowingsun.common.utils.MD5Utils;
import com.flowingsun.common.utils.loginCheckUtils;
import com.flowingsun.user.dao.UserMapper;
import com.flowingsun.user.entity.Permission;
import com.flowingsun.user.entity.Role;
import com.flowingsun.user.entity.User;
import com.flowingsun.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisDAO redisDAO;



    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //User user = (User)SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(user.getRoleList()!=null){
            for(Role role :user.getRoleList()){
                authorizationInfo.addRole(role.getRole());
                System.out.println("\n----------------------------------permission----------------------------------\n");
                for(Permission permission :role.getPermissionList()){
                    authorizationInfo.addStringPermission(permission.getPermission());
                    System.out.println("用户权限："+permission.getPermission());
                }
            }
        }
         return authorizationInfo;
    }


    /**
     * 认证 登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("\n----------------------------------用户登录认证----------------------------------\n");
        UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken)authenticationToken;
        String username =  usernamePasswordToke.getUsername();
        User userInfo = new User();
        if(loginCheckUtils.checkMobileNumber(username)==true){
            userInfo.setTelephone(username);
        }else if(loginCheckUtils.checkEmail(username)==true){
            userInfo.setUseremail(username);
        }
        User user = userService.findUserByUserToken(userInfo);
        if(user==null){
            System.out.println("\n----------------------------------用户信息：null----------------------------------\n");
            return null;
        }else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getTelephone(), user.getUserpass(), getName());
            //SecurityUtils.getSubject().getSession().setAttribute("userInfo",user);
            SecurityUtils.getSubject().getSession().setAttribute("userId",user.getId());
            user.setRoleList(null);
            String redisUser = redisDAO.setRedisUser(user);
            System.out.println("\n----------------------------------用户信息：存入redis----------------------------------\n"+redisUser);
            return info;
        }

    }
}

