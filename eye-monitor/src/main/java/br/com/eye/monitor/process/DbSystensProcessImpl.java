package br.com.eye.monitor.process;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;

import br.com.eye.monitor.data.SoftwareBean;
import org.springframework.stereotype.Component;

@Component
public class DbSystensProcessImpl implements DbSystensProcess {

	private static final String DB_SOFTWARES = "EYE_SOFTWARES";
	
	@Autowired
    private RedisOperations<String, Object> redis;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, SoftwareBean> getDB() {
		return (Map<String, SoftwareBean>) redis.opsForValue().get(DB_SOFTWARES);
	}

	@Override
	public void createDB(Map<String, SoftwareBean> sistemas) {
		redis.opsForValue().set(DB_SOFTWARES, sistemas);
	}
	
}
