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
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/09/12 09:59
     *@Param [principalCollection]
     *@Return org.apache.shiro.authz.AuthorizationInfo
     *@Description doGetAuthorizationInfo
     * 用户角色及权限认证
     * 根据用户的userId查询其所有的角色role即权限permission,最后将信息装入authorizationInfo返回
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = (Long)SecurityUtils.getSubject().getSession().getAttribute("userId");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(userId!=null){
            User user = userService.getUserByUserId(userId);
            if(user.getRoleList()!=null){
                for(Role role :user.getRoleList()){
                    authorizationInfo.addRole(role.getRole());
                    for(Permission permission :role.getPermissionList()){
                        authorizationInfo.addStringPermission(permission.getPermission());
                    }
                }
            }
        }else{
            authorizationInfo=null;
        }
         return authorizationInfo;
    }



    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/09/12 09:54
     *@Param [authenticationToken]
     *@Return org.apache.shiro.authc.AuthenticationInfo
     *@Description doGetAuthenticationInfo
     * 用户登录认证
     * 根据用户输入的手机号/邮箱号实例化一个userInfo对象，根据此对象查数据库
     * 查询到user结果则登录成功,尝试将用户信息放入redis中缓存,否则返回null。
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
        if(user!=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getTelephone(), user.getUserpass(), getName());
            SecurityUtils.getSubject().getSession().setAttribute("userId",user.getId());
            user.setRoleList(null);
            String result = redisDAO.setRedisUser(user);
            if(result!=null){
                System.out.println("\n----------------------------------用户信息：存入redis----------------------------------\n"+result);
            }
            return info;
        }else{
            return null;
        }

    }
}

