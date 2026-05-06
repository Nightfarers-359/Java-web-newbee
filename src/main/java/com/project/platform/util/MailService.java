package com.project.platform.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.from}")
    private String from;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
            log.info("邮件发送成功");
        } catch (Exception e) {
            log.error("邮件发送失败,异常信息:{}", e.getMessage(), e);
            throw new RuntimeException("邮件发送失败", e);
        }

    }
    
    /**
     * 发送邮件
     * @param to 收件人邮箱
     * @param subject 邮件标题
     * @param text 内容
     */
    @Async
    public void sendMailAsync(String to, String subject, String text) {
        sendSimpleMessage(to, subject, text);
    }
}
