package br.com.eye.monitor.process;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.data.VersionsTotal;

@Component
public class DataSaveProcessImpl extends DataSaveProcessAbstract implements DataSaveProcess {

	@Autowired
	private RedisOperations<String, Object> redis;

	@Autowired
	private DbSystensProcess dbSystensProcess;

	@Autowired
	private DataSaveProcessAdd dataSaveProcessAdd;

	@Autowired
	private DataSaveProcessFeaturesAndPerc dataSaveProcessFeaturesAndPerc;

	@Autowired
	private DataSaveProcessLoadSoftware dataSaveProcessLoadSoftware;

	@Autowired
	private DataSaveProcessFeaturesOfNumbers dataSaveProcessFeaturesAndNo;

	@Autowired
	private DataSaveProcessNumbersOfError dataSaveProcessNumbersOfError;

	@Autowired
	private DataSaveProcessVersionsAndTotal dataSaveProcessVersionsAndTotal;

	@Override
	public void add(SonarData sonarData) {
		dataSaveProcessAdd.add(sonarData);
	}

	@Override
	public Map<String, VersionsTotal> getFeaturesAndPerc() {
		return dataSaveProcessFeaturesAndPerc.getFeaturesAndPerc();
	}

	@Override
	public String[] getSoftware() {
		return dataSaveProcessLoadSoftware.getSoftware();
	}

	@Override
	public Map<String, SoftwareBean> getFeaturesAndNo() {
		return dataSaveProcessFeaturesAndNo.getFeaturesAndNo();
	}

	@Override
	public Map<String, String> getNumbersOfError() {
		return dataSaveProcessNumbersOfError.getNumbersOfError();
	}

	@Override
	public Map<String, VersionsTotal> getVersionsAndTotal() {
		return dataSaveProcessVersionsAndTotal.getVersionsAndTotal();
	}



}
