package br.com.eye.monitor.process;

import java.util.Map;

import br.com.eye.monitor.data.SoftwareBean;

public interface DbSystensProcess {

	Map<String, SoftwareBean> getDB();

	void createDB(Map<String, SoftwareBean> sistemas);

}
