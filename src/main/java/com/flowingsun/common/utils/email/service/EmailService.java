package com.flowingsun.common.utils.email.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

//这里用Component注解也可以
@Service("emailService")
public class EmailService {
    private static final String HOST = "smtp.163.com";
    private static final Integer PORT = 25;
    private static final Integer ALI_PORT = 465;
    private static final String USERNAME = "flowingsun007@163.com";
    private static final String PASSWORD = "wy920726zly";
    private static final String EMAILFORM = "flowingsun007@163.com";
    private static JavaMailSenderImpl mailSender = createAliMailSender();

    /**
     * @author lyon
     * @date   2018/12/22 13:43
     * @return org.springframework.mail.javamail.JavaMailSenderImpl
     * @detail 阿里云服务器禁用了25端口，所以此处需要设置465端口
     */
    private static JavaMailSenderImpl createAliMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(ALI_PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.port",ALI_PORT.toString());
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback","false");
        prop.setProperty("mail.smtp.socketFactory.port",ALI_PORT.toString());
        sender.setJavaMailProperties(prop);
        return sender;
    }

    /**
     * 发送邮件
     * @param [request, toEmail, userName, randomCode, userphone]
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     * @detail Windows和Mac上的分支，用mailSender即可，部署阿里云的server分支需要用aliMailSender
     */
    public static void sendHtmlMail(HttpServletRequest request, String toEmail, String userName, Integer randomCode, String userphone) throws MessagingException,UnsupportedEncodingException {
        String subject = "Lyon's Blog——注册激活";
        String content = "<html><p>亲爱的：" + userName +
                ",感谢您注册了Lyon's Blog,"+
                "请于30分钟内点击<a href=" + request.getHeader("Origin") +"/user/register/activate?code="+randomCode + "&userphone="+userphone+">链接</a>以激活注册！</p></html>";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(EMAILFORM, "稳稳地幸福(Lyon)");
        messageHelper.setTo(toEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(content,true);
        mailSender.send(mimeMessage);
    }

    /**
     * 发送邮件
     *
     * @param mailMap 收件人与邮件内容集合
     * @throws MessagingException 异常
     */
    public static void sendHtmlMail(Map<String, String> mailMap) throws MessagingException{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8编码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(EMAILFORM);
        Iterator<String> iterator = mailMap.keySet().iterator();
        while (iterator.hasNext()) {
            messageHelper.setTo(iterator.next());
            messageHelper.setText(mailMap.get(iterator.next()), true);
            mailSender.send(mimeMessage);
        }
    }
}
