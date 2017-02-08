package br.com.eye.monitor.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;

@Component
public class DataSaveProcessClientsImpl extends DataSaveProcessAbstract implements DataSaveProcessClients {

	@Autowired
	private RedisOperations<String, Object> redis;

	@Autowired
	private DbSystensProcess dbSystensProcess;
	
	@Override
	public Map<String, Map<String, Boolean>> getClients() {

		Map<String, SoftwareBean> sistemas = dbSystensProcess.getDB();
		
		Map<String, Map<String, Boolean>> clients = new HashMap<String, Map<String, Boolean>>();
		
		if (sistemas == null) {
			return null;
		}
		
		
		for (Map.Entry<String, SoftwareBean> entry : sistemas.entrySet()) {
			
			for (String funcChave : entry.getValue().getFuncionalidade()
					.keySet()) {
			
				List<SonarData> listaFunc = entry.getValue()
						.getFuncionalidade().get(funcChave);

				for( SonarData item : listaFunc ){
					
					if( !clients.containsKey(item.getServer()) ){
						clients.put(item.getServer(), new HashMap<String, Boolean>());	
					}

					if( item.getClient() != null ){
						clients.get(item.getServer()).put(item.getClient(), true);
					}
				}

			}

		}
		
		return clients;
	}
}
