package nwafu.cie.shrp.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nwafu.cie.shrp.controller.SseController;
import nwafu.cie.shrp.model.entity.Imputation;
import nwafu.cie.shrp.model.entity.Project;
import nwafu.cie.shrp.service.ImputationService;
import nwafu.cie.shrp.service.ProjectService;
import nwafu.cie.shrp.service.impl.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private ProjectService projectService;

    @Resource
    private ImputationService imputationService;

    @Resource
    private SseController sseController;

    @Resource
    private EmailService emailService;


    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"imputation_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        Long imputationId = Long.parseLong(message);
        try {
            imputationService.executeBeagleJar(imputationId);
            sseController.sendSseEventsToClients("你好");
            // 填充完成后发送邮件
            Imputation imputation = imputationService.getById(imputationId);
            Project project = projectService.getById(imputation.getProjectId());
            String email = project.getEmail();
            String chr = imputation.getChr();
            String subject = "Chr" + chr + " Complete";
            String projectName = project.getName();
            String text = "Project (" + projectName + ") Chr" + chr + " Complete";
//            emailService.sendSimpleMessage(email, subject, text);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }

}