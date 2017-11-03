package br.com.eye.messaing.config;

import br.com.eye.commons.EyeAspect;
import br.com.eye.messaing.annotations.EnablePrometheusEndpoint;
import br.com.eye.messaing.annotations.EnableSpringBootMetricsCollector;
import br.com.eye.messaing.send.SendComponentImpl;
import br.com.eye.messaing.send.SendManualPrometheusImpl;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration of the meYe that will start AspectJ and scan the classes.
 *
 * @author Marcelo de Souza Vieira
 */
@Configuration
@ImportAutoConfiguration(value = { EyeAspect.class, SendComponentImpl.class, InterceptorConfig.class,
		SendManualPrometheusImpl.class })
@EnableAspectJAutoProxy
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class EyeConfig {

}
