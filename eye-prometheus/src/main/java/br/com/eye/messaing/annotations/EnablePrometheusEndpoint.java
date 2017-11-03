package br.com.eye.messaing.annotations;

import br.com.eye.messaing.config.PrometheusEndpointConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PrometheusEndpointConfiguration.class)
public @interface EnablePrometheusEndpoint {

}
