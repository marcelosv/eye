package br.com.eye.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import br.com.eye.commons.Send;
import br.com.eye.commons.data.SonarData;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
	private RabbitMessagingTemplate rabbit;
	
	@Autowired
	private MappingJackson2MessageConverter messageConverter;
	
	@Value("${eye.url}")
    private String eyeLink;
	
	@Value("${eye.user.elasticsearch}")
    private String user;
    
    @Value("${eye.pass.elasticsearch}")
	private String pass;
    
    @RabbitListener(queues = "${eye.messaging.queue}", group="eye-kibana")
    public void receiveMessage(SonarData sonarData) {
        
    	new Send(sonarData, eyeLink, user, pass).run();
        
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
    
}