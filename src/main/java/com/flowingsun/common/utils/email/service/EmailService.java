package com.flowingsun.common.utils.email.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

//这里用Component注解也可以
@Service("emailService")
public class EmailService {
    private static final String HOST = "smtp.163.com";
    private static final Integer PORT = 25;
    private static final String USERNAME = "flowingsun007@163.com";
    private static final String PASSWORD = "wy920726zly";
    private static final String EMAILFORM = "flowingsun007@163.com";
    private static JavaMailSenderImpl mailSender = createMailSender();
    /**
     *@Author Lyon[flowingsun007@163.com]
     *@Date 18/05/9 18:30
     *@Description 发送邮件工具，此处指定的是网易邮件发送服务器
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        //网上找的模板东平西凑，实际上不需要设置这么多属性.....坑爹！
        p.setProperty("mail.smtp.starttls.enable","true");
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param toEmail 接受人
     * @param subject 主题
     * @param content 发送内容
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendHtmlMail(String toEmail,String userName, Integer randomCode,String userphone) throws MessagingException,UnsupportedEncodingException {
        String subject = "Lyon's Blog——注册激活";
        String content = "<html><p>亲爱的：" + userName +
                "感谢您注册了Lyon's Blog,"+
                "请于30分钟内点击<a href=http://localhost:9000/user/register/activate?code="+randomCode + "&userphone="+userphone+">链接</a>以激活注册！</p></html>";
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
