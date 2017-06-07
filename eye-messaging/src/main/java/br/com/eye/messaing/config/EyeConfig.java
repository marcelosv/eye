package br.com.eye.messaing.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import br.com.eye.commons.EyeAspect;
import br.com.eye.messaing.Send;
import br.com.eye.messaing.send.SendComponentImpl;

/**
 * Configuration of the meYe that will start AspectJ and scan the classes.
 *
 * @author Marcelo de Souza Vieira
 */
@Configuration
@ImportAutoConfiguration(value={EyeAspect.class, SendComponentImpl.class, Send.class})
@EnableAspectJAutoProxy
public class EyeConfig {

	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}
	
}
