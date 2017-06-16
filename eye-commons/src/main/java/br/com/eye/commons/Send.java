package br.com.eye.commons;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import br.com.eye.commons.EyeAbstract;
import br.com.eye.commons.data.SonarData;

public class Send implements Runnable{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    private SonarData sonarData;
	private String eyeLink;
	
	private String user;
	private String pass;

	private String forceIndex;
	
    public Send(SonarData sonarData, String eyeLink, String user, String pass){
        this.sonarData = sonarData;
		this.eyeLink = eyeLink;
		this.user = user;
		this.pass = pass;
    }

    public Send(SonarData sonarData, String eyeLink, String user, String pass, String forceIndex) {
    	this(sonarData, eyeLink, user, pass);
		this.forceIndex = forceIndex;
	}

	@Override
    public void run() {
        try {
        	
        	HttpHeaders headers = new HttpHeaders();
        	
        	String base64Creds = getAuth();

        	headers.add("Content-Type", "application/json");
        	
        	if( base64Creds != null ){
        		headers.add("Authorization", "Basic " + base64Creds);
        	}
        	
    		HttpEntity<SonarData> entity = new HttpEntity<>(sonarData, headers);
        	
    		RestTemplate restTemplate = new RestTemplate();
    		
    		StringBuilder link = new StringBuilder(eyeLink);
            link.append("/");
            
            if( StringUtils.isEmpty(forceIndex)){
            	link.append(sonarData.getServer().replaceAll(" ", "_"));
            }else{
            	link.append(forceIndex);
            }
            
            link.append("-").append(EyeAbstract.DATE_INDEX.format(new Date()))
            .append("/").append(sonarData.getDescription().replaceAll(" ", "_"))
            .append("/");
            
    		restTemplate.exchange(link.toString(), HttpMethod.POST, entity, Object.class);
        }catch (RuntimeException ex){
            LOGGER.debug("Impossível acessar monitor: "+ex.getMessage());
        }
    }

	private String getAuth() {
		
		if( StringUtils.isEmpty(user) || StringUtils.isEmpty(pass)){
			return null;
		}
		
		String plainCreds = user.concat(":").concat(pass);
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		return new String(base64CredsBytes);
	}
}