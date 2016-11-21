package br.com.eye.config;

import br.com.eye.EyeAspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration of the meYe that will start AspectJ and scan the classes.
 *
 * @author Marcelo de Souza Vieira
 */
@Configuration
@ComponentScan(basePackageClasses = EyeAspect.class)
@EnableAspectJAutoProxy
public class Config {

}
