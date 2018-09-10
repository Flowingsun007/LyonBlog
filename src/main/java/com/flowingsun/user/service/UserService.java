package com.flowingsun.user.service;

import com.flowingsun.article.vo.PageNotice;
import com.flowingsun.user.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    String UserRegister(User user,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String UserLogin(User user, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String UserLogout(HttpServletRequest request);

    User findUserByUserToken(User userInput);

    User getUserByUserId(Long userId);

    PageNotice userActivate(Integer code, String userphone,HttpServletRequest request);

    boolean setDefaultUserRole(String userphone);

    User getUserInfo(HttpServletRequest request);

}
