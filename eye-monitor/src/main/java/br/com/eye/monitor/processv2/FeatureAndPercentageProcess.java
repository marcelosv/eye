package br.com.eye.monitor.processv2;

import java.util.Map;

public interface FeatureAndPercentageProcess {

	Map<String, Double> getFeatureAndPercentage(String system, String client);
	
}
