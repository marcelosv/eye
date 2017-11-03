package br.com.eye.commons.send;

import br.com.eye.commons.data.SonarData;
import br.com.eye.data.TypesData;

public interface SendComponent {

	String EYE_PROMETHEUS = "eye_prometheus2_";

	void send(SonarData sonarData, TypesData type);
	
}
