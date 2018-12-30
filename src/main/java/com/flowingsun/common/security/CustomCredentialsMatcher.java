package com.flowingsun.common.security;

import com.flowingsun.common.utils.MD5Utils;
import com.flowingsun.common.utils.loginCheckUtils;
import com.flowingsun.user.entity.User;
import com.flowingsun.user.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *@Author Lyon[flowingsun007@163.com]
 *@Date 18/05/29 20:53
 *@Description 自定义密码验证
 * 主要用于登录验证
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private UserService userService;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        try {
            UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
            String username = usertoken.getUsername();
            User userInfo = new User();
            if(loginCheckUtils.checkMobileNumber(username)==true){
                userInfo.setTelephone(username);
            }else if(loginCheckUtils.checkEmail(username)==true){
                userInfo.setUseremail(username);
            }
            String password = String.valueOf(usertoken.getPassword());
            User user = userService.findUserByUserToken(userInfo);
            //saltPass = 原密码password + 手机号 + salt盐
            //saltPass经过MD5加密后才是存在数据库中的密码串(userpass)
            String saltPass = password+user.getTelephone()+user.getSalt();
            Object tokenCredentials = MD5Utils.encryptPassword(saltPass);
            Object accountCredentials =getCredentials(info);
            return equals(tokenCredentials,accountCredentials);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
