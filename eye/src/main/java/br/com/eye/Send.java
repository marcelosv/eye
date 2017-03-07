package br.com.eye;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import br.com.eye.data.SonarData;

class Send implements Runnable{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    private SonarData sonarData;
	private String eyeLink;
	
    public Send(SonarData sonarData, String eyeLink){
        this.sonarData = sonarData;
		this.eyeLink = eyeLink;
    }

    @Override
    public void run() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> vars = new HashMap<String, String>();
            
            StringBuilder link = new StringBuilder(eyeLink);
            link.append("/")
            .append(sonarData.getServer().replaceAll(" ", "_")).append("-").append(EyeAbstract.DATE_INDEX.format(new Date()))
            .append("/").append(sonarData.getDescription().replaceAll(" ", "_"))
            .append("/");
            
            LOGGER.debug(link.toString());
            
            restTemplate.postForObject(link.toString(), sonarData, SonarData.class, vars);
        }catch (RuntimeException ex){
            LOGGER.debug("Impossível acessar monitor: "+ex.getMessage());
        }
    }
}