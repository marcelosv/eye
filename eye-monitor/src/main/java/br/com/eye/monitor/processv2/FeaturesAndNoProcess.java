package br.com.eye.monitor.processv2;

import java.util.Map;

import br.com.eye.monitor.processv2.json.FeatureAndNo;

public interface FeaturesAndNoProcess {
	
	Map<String, FeatureAndNo> getFeaturesAndNo(String system, String client);
	
}
