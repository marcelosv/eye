package br.com.eye.monitor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.eye.monitor.components.ServerComponent;

@Component
public class DashboardImpl implements Dashboard {

	@Autowired
	private ServerComponent serverComponent;
	
	@Override
	public void create() {
		
		serverComponent.getAll();
		
	}

}
