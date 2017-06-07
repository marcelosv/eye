package br.com.eye.commons.send;

import br.com.eye.commons.data.SonarData;

public interface SendComponent {
	
	void send(SonarData sonarData, String eyeLink, String user, String pass);
	
}
