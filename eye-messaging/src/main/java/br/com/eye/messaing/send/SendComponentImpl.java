package br.com.eye.messaing.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eye.commons.data.SonarData;
import br.com.eye.commons.send.SendComponent;
import br.com.eye.messaing.Send;

@Component
public class SendComponentImpl implements SendComponent {

	@Autowired
	private Send send;
	
	@Override
	public void send(SonarData sonarData, String eyeLink, String user,
			String pass) {
		send.run(sonarData, eyeLink, user, pass);
	}

}
