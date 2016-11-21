package br.com.eye.monitor.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.eye.monitor.process.DataSaveProcess;

@Service
public class WebSocketExecuteImpl implements WebSocketExecute {

	private static final String TOPIC_FEACTURES_PERC = "/topic/feactures_perc";
	private static final String TOPIC_VERSIONS_TOTAL = "/topic/versions_total";
	private static final String TOPIC_FEACTURE_AND_NUMBER = "/topic/feacture_number";
	private static final String TOPIC_SOFTWARES = "/topic/softwares";

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private DataSaveProcess dataSave;

	@Override
	public void runSystems() {
		template.convertAndSend(TOPIC_SOFTWARES,
				dataSave.getSoftware());
	}

	@Override
	public void runAll() {
		
		template.convertAndSend(TOPIC_SOFTWARES,
				dataSave.getSoftware());
		
		template.convertAndSend(TOPIC_FEACTURE_AND_NUMBER,
				dataSave.getFeaturesAndNo());
		
		template.convertAndSend(TOPIC_VERSIONS_TOTAL,
				dataSave.getVersionsAndTotal());
		
		template.convertAndSend(TOPIC_FEACTURES_PERC,
				dataSave.getFeaturesAndPerc());
	}

}
