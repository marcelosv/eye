package br.com.eye.monitor.processv2;

import java.util.Map;

public interface VersionsAndTotalProcess {

	Map<String, Double> getVersionsAndTotal(String system, String client);

}
