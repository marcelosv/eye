package br.com.eye.monitor.process;

import java.util.Map;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.data.VersionsTotal;



public interface DataSaveProcess {

	void add(SonarData sonarData);

	Map<String, VersionsTotal> getFeaturesAndPerc();

	String[] getSoftware();

	Map<String, SoftwareBean> getFeaturesAndNo();

	Map<String, String> getNumbersOfError();

	Map<String, VersionsTotal> getVersionsAndTotal();

   
}
