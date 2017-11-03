package br.com.eye.messaing.config;

import br.com.eye.messaing.component.SpringBootMetricsCollector;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class PrometheusMetricsConfiguration {

	@Bean
	public SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
		SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector( publicMetrics );
		springBootMetricsCollector.register();
		return springBootMetricsCollector;
	}
}
