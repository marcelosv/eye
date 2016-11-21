package br.com.eye.monitor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.eye.monitor.config.RedisConfig;
import br.com.eye.monitor.websocket.WebSocketExecute;

@RestController
@Controller
public class MonitorController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private static boolean SYSTEM_ENABLED = true;
	
	@Autowired
	private WebSocketExecute webSocketExecute;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping(path = "refresh")
	public void refresh() {
		webSocketExecute.runAll();
	}

	@RequestMapping(path = "active/{active}")
	public void ativar(@PathVariable("active") String ativo) {
		SYSTEM_ENABLED = "S".equals(ativo);
	}

	@RequestMapping(path = "active")
	public boolean ativar() {
		return SYSTEM_ENABLED;
	}

	@RequestMapping(path = "receive", method = RequestMethod.POST)
	public void receive(@RequestBody String sonarData) {

		if( !SYSTEM_ENABLED){
			LOG.debug("desativado...");
			return;
		}

		LOG.debug("receive...");
		
		redisTemplate.convertAndSend(RedisConfig.NAME_LIST_FORM_PROCESS, sonarData);
	}
}
