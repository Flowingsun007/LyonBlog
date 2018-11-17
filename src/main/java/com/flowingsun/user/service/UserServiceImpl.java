package com.flowingsun.user.service;

import com.flowingsun.article.vo.PageNotice;
import com.flowingsun.behavior.dao.CommentMapper;
import com.flowingsun.common.dao.RedisDAO;
import com.flowingsun.common.utils.MD5Utils;
import com.flowingsun.common.utils.email.service.EmailService;
import com.flowingsun.common.utils.loginCheckUtils;
import com.flowingsun.user.dao.*;
import com.flowingsun.user.entity.Permission;
import com.flowingsun.user.entity.User;
import com.flowingsun.user.entity.UserRole;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.apache.log4j.Level.INFO;
import static org.apache.log4j.Level.WARN;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final boolean SUCCESS = true;

    private static final boolean FAIL = false;

    //用户角色id默认值
    private static final Integer REGULAR_USER_ROLE_ID = 4;

    @Autowired
    private RedisDAO redisDAO;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CommentMapper commentMapper;

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/9 17:39
     *@Description 用户注册
     */
    @Override
    @Transactional
    public String UserRegister(User user,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String saltPass = user.getUserpass()+user.getTelephone()+salt;
        String md5pass = MD5Utils.encryptPassword(saltPass);
        user.setUserpass(md5pass);
        user.setSalt(salt);
        String result = "register_fail";
        if(0==userMapper.insertByUserRegister(user)){
            //用户插入失败，可能是手机/邮箱已存在
            logger.warn("用户插入失败，可能是手机/邮箱已存在"+user.toString());
            return result;
        }
        else{
            Random random = new Random(Long.parseLong(user.getTelephone()));
            Integer randomCode = random.nextInt();
            //将根据用户注册手机号生成的随机数放入session，然后发送到用户邮箱，等待用户激活。
            request.getSession().getServletContext().setAttribute(user.getTelephone(),randomCode);
            try {
                emailService.sendHtmlMail(user.getUseremail(),user.getUsername(),randomCode,user.getTelephone());
                result = "register_succ";
                logger.warn("用户提交注册信息，激活邮件发送成功"+user.toString());
            } catch (MessagingException|UnsupportedEncodingException e) {
                logger.error("用户提交注册信息，激活邮件发送失败",e);
                e.printStackTrace();
                if(0==userMapper.deleteByUserphone(user.getTelephone())){
                    logger.warn("数据库删除用户注册信息失败，用户手机号："+user.getTelephone());
                }
                request.getSession().getServletContext().removeAttribute(user.getTelephone());
            } finally {
                return result;
            }
        }
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/9 17:39
     *@Description 用户登录
     * 登录支持用户手机号+密码 或者用户邮箱+密码 两种形式
     * 登录信息后以token = username + password,然后以Shrio中的subject.login(token)来校验
     * 主要登录验证的规则以及角色及权限校验在common/inteceptor/MyRealm类中
     * 和common/security/CustomCredentialsMatcher类中定义
     */
     @Override
     public String UserLogin(User user,HttpServletRequest request){
         String username="";
         if(user.getTelephone()!=null){
             if(loginCheckUtils.checkMobileNumber(user.getTelephone())==true){
                 logger.log(INFO,"用户输入的是手机号，使用手机号+密码登录"+user.toString());
                 user.setTelephone(user.getTelephone());
                 username = user.getTelephone();
             }
         }else if(user.getUseremail()!=null){
             if(loginCheckUtils.checkEmail(user.getUseremail())==true){
                 logger.log(INFO,"用户输入的是邮箱号，使用邮箱号+密码登录"+user.toString());
                 user.setUseremail(user.getUseremail());
                 username = user.getUseremail();
             }
         }
         UsernamePasswordToken token = new UsernamePasswordToken(username, user.getUserpass());
         //token.setRememberMe(true);
         Subject subject = SecurityUtils.getSubject();
         try {
             subject.login(token);
             SecurityUtils.getSubject().getSession().setTimeout(1800000);
             //取用户给文章点赞评论信息存入session...
             return "login_succ";
         } catch (Exception e) {
             SecurityUtils.getSubject().getSession().removeAttribute("userId");
             logger.warn("UserLogin(User user,HttpServletRequest request):login_fail登录失败");
             return "login_fail";
         }
     }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/22 17:01
     *@Description  用户退出登录
     */
    @Override
    public String UserLogout (HttpServletRequest request){
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId!=null){
            request.getSession().removeAttribute("userId");
            if(redisDAO.removeUser(userId)==true){
                logger.info("用户退出登录，清空redis信息成功！");
            }else{
                logger.info("用户退出登录，清空redis信息失败！");
            }
            return "logout_succ";
        }
        return "logout_fail";
    }

    @Override
    public User findUserByUserToken(User userInput) {
        User user=null;
        try {
            user =userMapper.selectByUserToken(userInput);
        }catch (Exception e){
            logger.error("findUserByUserToken(User userInput)方法执行Error：",e);
        }
        return user;
    }

    @Override
    public User getUserByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/06/6 20:22
     *@Description userActivate
     * 【用户注册激活】—— 用户点击邮件里的链接，跳转到此处，然后验证激活信息；激活成功则更改用户状态，设置权限。
     * P.S.在注册时通过（request.getSession().getServletContext().setAttribute(user.getTelephone(),randomCode);）将用户手机号，随机码放入了ServletContext
     */
    @Override
    @Transactional
    public PageNotice userActivate(Integer code, String userphone,HttpServletRequest request) {
        PageNotice pageNotice = new PageNotice();
        try{
            Integer savedCode = (Integer) request.getSession().getServletContext().getAttribute(userphone);
            if(savedCode!=null){
                if(code.equals(savedCode)){
                    //激活信息正确，将数据库中用户状态重置为1，并给用户添加各种权限。
                    if(true==setDefaultUserRole(userphone)){
                        pageNotice.setStatus(1);
                        pageNotice.setNotice("恭喜，激活成功！");
                        return pageNotice;
                    }
                    else{
                        deleteDefaultUserRole(userphone);
                        logger.log(WARN,"数据库激活用户失败，用户手机号："+userphone);
                    }
                }else{
                    if(0==userMapper.deleteByUserphone(userphone)){
                        logger.log(WARN,"数据库删除用户失败，用户手机号："+userphone);
                    }
                }
                pageNotice.setStatus(0);
                pageNotice.setNotice("矮油，激活失败！");
            }else{
                pageNotice.setStatus(0);
                pageNotice.setNotice("激活链接已过期，请尝试直接登录，或重新注册！");
            }
            return pageNotice;
        }catch (Exception e){
            logger.error("userActivate()用户激活失败："+pageNotice.toString()+"+Error：",e);
            return pageNotice;
        }finally {
            request.getSession().getServletContext().removeAttribute(userphone);
        }
    }

    public void deleteDefaultUserRole(String userphone) {
        Long userId = userMapper.selectUseridByUserphone(userphone);
        if(userId!=null){
            Integer result = userRoleMapper.deleteByPrimaryKey(userId);
            System.out.println("deleteDefaultUserRole"+"result:"+result);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDefaultUserRole(String userphone){
        //普通注册用户，默认值为4。
        Integer roleId = REGULAR_USER_ROLE_ID;
        try{
            Long userId = userMapper.selectUseridByUserphone(userphone);
            int userstatus = 1;
            if(1==userMapper.updateUserStatusByUserphone(userstatus,userphone)&&(1==userRoleMapper.insertByUseridRoleid(userId,roleId))){
                return SUCCESS;
            }else{
                return FAIL;
            }
        }catch (Exception e){
            logger.error("设置用户角色失败，setDefaultUserRole()执行Error：",e);
            return FAIL;
        }
    }

    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/06/16 20:21
     *@Description  用于前台登录状态校验。
     * 若用户已登录，则从redis中取用户信息，注入user；
     * 若用户未登录，则直接返回空user对象。
     * 经测验，Long userId2 = (Long)SecurityUtils.getSubject().getSession().
     * getAttribute("userId")所获取的userId2和直接从request.getSession()里获取的userId一样，
     * 本质上getSubject().getSession().getAttribute或者setAttribute()，操作的都是底层的HttpServletRequest request对象
     */
    @Override
    public User getUserInfo(HttpServletRequest request) {
        User user = null;
        Long userId = (Long) request.getSession().getAttribute("userId");
        if(userId!=null){
            user = redisDAO.getRedisUser(userId);
            if(user==null){
                user = userMapper.selectByPrimaryKey(userId);
            }
        }
        return user;
    }


}
