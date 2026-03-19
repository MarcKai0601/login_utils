package org.loginutils.mgr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockEmailService {

    /**
     * 模擬寄送密碼重設信件
     * @param to 收件人 Email
     * @param newPassword 新的明碼密碼
     */
    public void sendPasswordResetEmail(String to, String newPassword) {
        log.info("========================================");
        log.info("【Mock Email Service】");
        log.info("寄送新密碼 [{}] 至信箱 [{}]", newPassword, to);
        log.info("========================================");
    }
}
