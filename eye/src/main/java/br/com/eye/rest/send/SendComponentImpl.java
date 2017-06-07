package br.com.eye.rest.send;

import org.springframework.stereotype.Component;

import br.com.eye.commons.Send;
import br.com.eye.commons.data.SonarData;
import br.com.eye.commons.send.SendComponent;

@Component
public class SendComponentImpl implements SendComponent {

	@Override
	public void send(SonarData sonarData, String eyeLink, String user,
			String pass) {
		
		new Thread(new Send(sonarData, eyeLink, user, pass)).start();
		
	}

}
