package org.loginutils.mgr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    /**
     * 發送密碼重設信件 (整合 Spring Boot Starter Mail)
     * @param to 收件人 Email
     * @param newPassword 新的一次性密碼
     */
    public void sendPasswordResetEmail(String to, String newPassword) {
        log.info("Preparing OTP generic email notification for recipient: {}", to);
        try {
            if (javaMailSender == null) {
                log.warn("JavaMailSender is not provisioned (likely invalid SMTP configuration). Falling back to console logging.");
                log.info("【Mock Fallback Output】 新密碼: [ {} ]", newPassword);
                return;
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@login-utils.org");
            message.setTo(to);
            message.setSubject("系統帳號 - 一次性登入密碼發送 (OTP)");
            message.setText("親愛的使用者您好，\n\n" +
                    "您的密碼已由系統重設指令更新。以下是您的登入一次性密碼 (OTP)：\n\n" +
                    "--------------------------\n" +
                    newPassword + "\n" +
                    "--------------------------\n\n" +
                    "【系統重要聲明】這是一次性密碼，為保障帳號核心安全，登入後系統將強制您導向密碼修改頁面進行新密碼設定。\n" +
                    "請不要將此信件遺留或將密碼洩漏給任何系統外部的第三方。\n\n" +
                    "感謝您的配合。");

            javaMailSender.send(message);
            log.info("OTP Email successfully dispatched to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send SMTP email to {}", to, e);
        }
    }
}
