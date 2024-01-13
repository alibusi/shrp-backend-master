package nwafu.cie.shrp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SseControllerTest {

    @Resource
    private SseController sseController;

    @Test
    void sendSseEventsToClients() {
        sseController.sendSseEventsToClients("你好");
    }

}