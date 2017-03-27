package br.com.eye;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.eye.annotations.Sensor;

public class Abort {

	private static final String TRUE = "true";
	
	public boolean isAbortSensor(Sensor sensor, String disabled, String tagsActive){
    	
    	if(TRUE.equals(disabled)){
    		return true;
    	}
    	
    	if( StringUtils.isEmpty(tagsActive)){
    		return false;
    	}
    	
    	Map<String, Boolean> tagsSensor = getTags(sensor);
    	
    	String[] tagsConfig = tagsActive.split(",");
    	for(String tag : tagsConfig){
    		if( StringUtils.isEmpty(tag) ){
    			continue;
    		}
    		
    		if( tagsSensor.containsKey(tag.trim()) ){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
	private Map<String, Boolean> getTags(Sensor sensor) {

		Map<String, Boolean> argsMap = new HashMap<String, Boolean>();
		
		if(StringUtils.isEmpty(sensor.tags())){
			return argsMap;
		}
		
		String[] tags = sensor.tags();
		for(String tag : tags){
			
			if( StringUtils.isEmpty(tag) ){
    			continue;
    		}
			
			argsMap.put(tag.trim(), true);
		}
		
		return argsMap;
	}
	
}
