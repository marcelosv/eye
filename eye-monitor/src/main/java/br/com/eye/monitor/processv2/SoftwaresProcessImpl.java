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
public class SoftwaresProcessImpl implements SoftwaresProcess {

	@Autowired
    private DbSystensProcess dbSystensProcess;
	
	@Override
	public List<String> getSoftwares() {
		
		Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();
		
		if( softwares == null ){
			return null;
		}
		
		List<String> list = new ArrayList<String>(softwares.keySet());
		
		Collections.sort(list);
		
		return list;
	}

}
