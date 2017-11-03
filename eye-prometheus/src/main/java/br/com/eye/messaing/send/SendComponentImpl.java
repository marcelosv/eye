package br.com.eye.messaing.send;

import br.com.eye.data.TypesData;
import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

import br.com.eye.commons.data.SonarData;
import br.com.eye.commons.send.SendComponent;

import java.util.HashMap;
import java.util.Map;

public class SendComponentImpl implements SendComponent {

	private static final Counter eyeEndPoint = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.API_ENDPOINT.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeUser = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.USER.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeService = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.SERVICE.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeUninformed = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.UNINFORMED.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeQueue = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.QUEUE.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeHql = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.HQL.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Counter eyeSql = Counter.build()
			.name( EYE_PROMETHEUS + TypesData.SQL.name() )
			.labelNames( SonarData.getNames() )
			.help( "Item interceptado" ).register();

	private static final Map<TypesData, Counter> mapCounter = new HashMap();

	static {
		mapCounter.put( TypesData.API_ENDPOINT, eyeEndPoint );
		mapCounter.put( TypesData.SERVICE, eyeService );
		mapCounter.put( TypesData.USER, eyeUser );
		mapCounter.put( TypesData.UNINFORMED, eyeUninformed );
		mapCounter.put( TypesData.QUEUE, eyeQueue );
		mapCounter.put( TypesData.HQL, eyeHql );
		mapCounter.put( TypesData.SQL, eyeSql );
	}

	@Override
	public void send(SonarData sonarData, TypesData type) {
		mapCounter.get( type ).labels( SonarData.getValues( sonarData ) ).inc();
	}
}
