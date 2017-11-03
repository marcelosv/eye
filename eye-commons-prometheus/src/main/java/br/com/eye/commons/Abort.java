package br.com.eye.commons;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
		
		if(sensor.tags()==null){
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
