package nwafu.cie.shrp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EmailServiceTest {

    @Resource
    private EmailService emailService;

    @Test
    void sendSimpleMessage() {
        String email = "373363602@qq.com";
        String subject = "Chr22 Complete";
        String text = "Project (123123) Chr22 Complete.";
        emailService.sendSimpleMessage(email, subject, text);
    }
}