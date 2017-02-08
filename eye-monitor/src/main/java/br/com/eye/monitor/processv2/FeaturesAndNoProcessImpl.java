package br.com.eye.monitor.processv2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.process.DbSystensProcess;
import br.com.eye.monitor.processv2.json.FeatureAndNo;
import br.com.eye.monitor.processv2.json.FeatureAndNoVersion;

@Service
public class FeaturesAndNoProcessImpl implements FeaturesAndNoProcess {

	@Autowired
    private DbSystensProcess dbSystensProcess;
	
	@Override
	public Map<String, FeatureAndNo> getFeaturesAndNo(String system, String client) {
		
		Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();
		
		if( softwares == null ){
			return null;
		}
		
		if( !softwares.containsKey(system) ){
			return null;
		}
		
		return process(softwares.get(system), client);
	}

	private Map<String, FeatureAndNo> process(SoftwareBean softwareBean, String client) {

		Map<String, List<SonarData>> features = softwareBean.getFuncionalidade();
		if( features == null ){
			return null;
		}
		
		Map<String, FeatureAndNo> featuresReturn = new HashMap<String, FeatureAndNo>();
		
		for(String key : features.keySet()){
			FeatureAndNo featureAndNo = new FeatureAndNo();
			featureAndNo.setName(key);
			
			
			List<SonarData> itensSonar = features.get(key);
			
			// usado para calcular media da velocidade
			long timerAmount = 0;
			boolean sonarExist = false;
			for(SonarData sonar : itensSonar){
				
				if( client != null && !client.equals(sonar.getClient()) ){
					// se tiver passado o cliente, vai ser obertado todos os clientrs diferentes
					continue;
				}
				
				sonarExist = true;

				featureAndNo.setTags(sonar.getTags());
				
				// recuperando a versao ou gerando uma nova
				FeatureAndNoVersion version = null;
				if( featureAndNo.getFeaturesAndNoVersion().containsKey(sonar.getVersion()) ){
					version = featureAndNo.getFeaturesAndNoVersion().get(sonar.getVersion());
				}else{
					version = new FeatureAndNoVersion();
					featureAndNo.getFeaturesAndNoVersion().put(sonar.getVersion(), version);
				}
				
				version.addAccessNumber();
				
				// add a informação de erro.
				if( sonar.isError() ){
					version.addErrorNumber();
					version.getErrors().add(sonar.getException());
				}
				
				featureAndNo.addTimerList(sonar.getTimeExec());
				timerAmount += sonar.getTimeExec();
			}
			
			if( !sonarExist ){
				continue;
			}
			
			featureAndNo.setAverage(timerAmount / featureAndNo.getTimerList().size());
			featuresReturn.put(key, featureAndNo);
		}
		
		return featuresReturn;
	}

}

