package br.com.eye.messaing;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import br.com.eye.commons.data.SonarData;

@Component
public class Send {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private RabbitMessagingTemplate rabbit;

	@Autowired
	private MappingJackson2MessageConverter messageConverter;

	@Value("${eye.messaging.queue}")
	private String queue;

	public void run(SonarData sonarData, String eyeLink, String user,
			String pass) {
		rabbit.setMessageConverter(messageConverter);
		rabbit.convertAndSend(queue, sonarData);
	}

}