package br.com.eye.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import br.com.eye.EyeAspect;

/**
 * Configuration of the meYe that will start AspectJ and scan the classes.
 *
 * @author Marcelo de Souza Vieira
 */
@Configuration
@ImportAutoConfiguration(value={EyeAspect.class})
@EnableAspectJAutoProxy
public class EyeConfig {

}
