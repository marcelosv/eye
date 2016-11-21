package br.com.eye.monitor.process;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;
import org.springframework.stereotype.Component;

@Component
public class DataSaveProcessAddImpl implements DataSaveProcessAdd {

	@Autowired
	private RedisOperations<String, Object> redis;

	@Autowired
	private DbSystensProcess dbSystensProcess;

	@Override
	public void add(SonarData sonarData) {

		Map<String, SoftwareBean> sistemas = dbSystensProcess.getDB();

		if (sistemas == null) {
			sistemas = new TreeMap<String, SoftwareBean>();
			dbSystensProcess.createDB(sistemas);
		}

		if (!sistemas.containsKey(sonarData.getServer())) {
			sistemas.put(sonarData.getServer(), new SoftwareBean());
		}

		sistemas.get(sonarData.getServer()).add(sonarData);
		dbSystensProcess.createDB(sistemas);
	}


}
