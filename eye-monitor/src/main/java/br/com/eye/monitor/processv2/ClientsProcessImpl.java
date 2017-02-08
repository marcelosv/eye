package br.com.eye.monitor.processv2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.process.DbSystensProcess;

@Service
public class ClientsProcessImpl implements ClientsProcess {

	@Autowired
    private DbSystensProcess dbSystensProcess;
	
	@Override
	public List<String> getClients(String system) {

		Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();
		
		if( softwares == null ){
			return null;
		}
		
		if( !softwares.containsKey(system) ){
			return null;
		}
		
		SoftwareBean soft = softwares.get(system);
		List<String> list = new ArrayList<String>(soft.getClients().keySet());
		
		Collections.sort(list);
		
		return list;
	}

}
