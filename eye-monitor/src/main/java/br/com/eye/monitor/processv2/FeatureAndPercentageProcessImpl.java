package br.com.eye.monitor.processv2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.process.DbSystensProcess;

@Service
public class FeatureAndPercentageProcessImpl implements FeatureAndPercentageProcess{

	@Autowired
    private DbSystensProcess dbSystensProcess;
	
	@Autowired
	private CalculatePercentage calculatePercentage;
	
	@Override
	public Map<String, Double> getFeatureAndPercentage(String system,
			String client) {
		
		Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();
		
		if( softwares == null ){
			return null;
		}
		
		if( !softwares.containsKey(system) ){
			return null;
		}
		
		return process(softwares.get(system), client);
	}

	private Map<String, Double> process(SoftwareBean softwareBean, String client) {

		Map<String, List<SonarData>> features = softwareBean.getFuncionalidade();
		if( features == null ){
			return null;
		}
		
		Map<String, Integer> hashCalc = new HashMap<String, Integer>();
		
		
		int amount = 0;
		for(String key : features.keySet()){
			
			if( !hashCalc.containsKey(key) ){
				hashCalc.put(key, 0);
			}
			
			List<SonarData> itensSonar = features.get(key);
		
			for(SonarData sonar : itensSonar){
				
				if( client != null && !client.equals(sonar.getClient()) ){
					// se tiver passado o cliente, vai ser obertado todos os clientrs diferentes
					continue;
				}

				amount++;
				hashCalc.put(key, hashCalc.get(key)+1);
			}
		}
		
		Map<String, Double> versionReturn = new HashMap<String, Double>();
		for(String key : hashCalc.keySet()){
			versionReturn.put(key, calculatePercentage.calculate(amount, hashCalc.get(key)));
		}
		
		return versionReturn;
	}

}
