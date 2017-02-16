package br.com.eye.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.eye.monitor.dashboard.Dashboard;

@SpringBootApplication
@EnableScheduling
public class ApplicationMonitor {

	private static final Logger log = LoggerFactory.getLogger(ApplicationMonitor.class);
	
	@Autowired
	private Dashboard dashboard;
	
	@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
		dashboard.create();
    }
	
	public static void main(String[] args) throws BeansException, InterruptedException {
		ApplicationContext app = SpringApplication.run(ApplicationMonitor.class, args);
		
	}

}
