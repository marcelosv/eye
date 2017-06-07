package br.com.eye.rest.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import br.com.eye.commons.EyeAspect;
import br.com.eye.rest.send.SendComponentImpl;

/**
 * Configuration of the meYe that will start AspectJ and scan the classes.
 *
 * @author Marcelo de Souza Vieira
 */
@Configuration
@ImportAutoConfiguration(value={EyeAspect.class, SendComponentImpl.class})
@EnableAspectJAutoProxy
public class EyeConfig {

}
