package br.com.eye.messaing.send;

import br.com.eye.commons.send.SendComponent;
import br.com.eye.data.TypesData;
import io.prometheus.client.Counter;

import java.util.HashMap;
import java.util.Map;

public class SendManualPrometheusImpl implements SendManualPrometheus {

	private static final Counter eyeManual = Counter.build()
			.name( SendComponent.EYE_PROMETHEUS + "MANUAL" )
			.labelNames( "descricao" )
			.help( "Item interceptado" ).register();

	private static final Map<TypesData, Counter> mapCounter = new HashMap();

	@Override
	public void send(String descricao) {
		mapCounter.get( eyeManual ).labels( descricao ).inc();
	}

}
