package br.com.eye.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.eye.monitor.config.SecurityConfig;

@SpringBootApplication
@ImportAutoConfiguration(value={SecurityConfig.class})
public class ApplicationMonitor {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMonitor.class, args);
	}

}
