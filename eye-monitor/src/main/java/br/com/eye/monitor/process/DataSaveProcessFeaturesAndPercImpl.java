package br.com.eye.monitor.process;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.data.CountVersion;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.data.VersionsTotal;
import org.springframework.stereotype.Component;

@Component
public class DataSaveProcessFeaturesAndPercImpl extends DataSaveProcessAbstract implements
		DataSaveProcessFeaturesAndPerc {

	@Autowired
	private RedisOperations<String, Object> redis;

	@Autowired
	private DbSystensProcess dbSystensProcess;
	
	@Override
	public Map<String, VersionsTotal> getFeaturesAndPerc() {
		
		Map<String, SoftwareBean> sistemas = dbSystensProcess.getDB();
		
		Map<String, VersionsTotal> retorno = new TreeMap<String, VersionsTotal>();

		if (sistemas == null) {
			return retorno;
		}

		for (Map.Entry<String, SoftwareBean> entry : sistemas.entrySet()) {

			if (!retorno.containsKey(entry.getKey())) {
				retorno.put(entry.getKey(), new VersionsTotal());
			}

			VersionsTotal versoesTotais = retorno.get(entry.getKey());

			for (String funcChave : entry.getValue().getFuncionalidade()
					.keySet()) {

				CountVersion contVersao = new CountVersion();

				List<SonarData> listaFunc = entry.getValue()
						.getFuncionalidade().get(funcChave);
				contVersao.setTotal(listaFunc.size());

				versoesTotais.getLista().put(funcChave, contVersao);

				versoesTotais.setTotal(versoesTotais.getTotal()
						+ contVersao.getTotal());
			}

		}

		for (String chaveSistema : retorno.keySet()) {
			VersionsTotal item = retorno.get(chaveSistema);

			for (String chaveItem : item.getLista().keySet()) {
				CountVersion contVersao = item.getLista().get(chaveItem);
				contVersao.setPerc(calcPorcentagem(item.getTotal(),
						contVersao.getTotal())
						+ "");
			}

		}

		return retorno;
	}
}