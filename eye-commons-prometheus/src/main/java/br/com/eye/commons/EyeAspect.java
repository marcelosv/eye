package br.com.eye.commons;

import br.com.eye.annotations.EyeIgnoreExcetion;
import br.com.eye.annotations.Sensor;
import br.com.eye.commons.data.SonarData;
import br.com.eye.commons.send.SendComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class EyeAspect extends EyeAbstract {

	private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

	@Value("${spring.application.name:}")
	private String appName;

	@Value("${spring.application.version:}")
	private String appVersion;

	@Value("${eye.disabled:}")
	private String disabled;

	@Value("${spring.cloud.consul.discovery.instanceId:}")
	private String instanceId;

	@Value("${eye.tags.active:}")
	private String tagsActive;

	@Autowired
	private ApplicationContext context;

	private SendComponent sendComponent;

	@Around("@annotation(br.com.eye.annotations.Sensor) && execution(* *(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.debug( "Método interceptado pelo Sonar." );

		// metodo anotado
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		LOGGER.debug( "Metodo: " + method.getName() );

		Sensor sensor = method.getAnnotation( Sensor.class );

		Abort abort = new Abort();
		if (abort.isAbortSensor( sensor, disabled, tagsActive )) {
			LOGGER.debug( "ignorado interceptação" );
			return joinPoint.proceed();
		}

		long tempoInicial = System.currentTimeMillis();

		Object returnObject = null;

		SonarData sonarData = new SonarData();

		sonarData.setDescription( sensor.description() );
		sonarData.setType( sensor.type().getValue() );
		sonarData.setTypeFmt( sensor.type().name() );
		sonarData.setServer( appName );
		sonarData.setVersion( appVersion );

		ColectData identifyClient = getIdentifyClient();

		if (identifyClient != null) {
			identifyClient.colectData( sonarData );
		}

		sonarData.setInstanceId( instanceId );

//		injectArgs( sonarData, joinPoint );

		try {
			returnObject = joinPoint.proceed();
		} catch (Throwable throwable) {

			LOGGER.debug( "Erro captado pelo sonar: " + throwable.getMessage() );

			boolean isError = true;

			if (throwable.getClass().getInterfaces() != null) {
				for (Class clazz : throwable.getClass().getInterfaces()) {
					if (clazz.equals( EyeIgnoreExcetion.class )) {
						isError = false;
					}
				}
			}

			if( isError ) {

				StringBuilder sb = new StringBuilder(  );
				sb.append( throwable.getMessage() );
				if( throwable.getCause() != null){
					sb.append( " - " );
					sb.append( throwable.getCause().getMessage() );
				}

				sonarData.setError( isError );
				sonarData.setMessageError( sb.toString() );
				sonarData.setException( throwable.getClass().getSimpleName() );
			}

			throw throwable;
		} finally {

			LOGGER.debug( "Finalizando Sonar.." );

			try {
				sendComponent.send( sonarData, sensor.type() );
			} catch (Exception ex) {
				// dont need code because if this code generate error, dont stop code origin the system.
			}
		}

		return returnObject;
	}

	/*private void injectArgs(SonarData sonarData, ProceedingJoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		Object[] args = joinPoint.getArgs();

		if (args == null) {
			return;
		}

		for (Object arg : args) {
			if (arg instanceof Number) {
				sb.append( ((Number) arg).doubleValue() );
			} else if (arg instanceof String) {
				sb.append( arg.toString() );
			} else {
				sb.append( "ignored" );
			}
			sb.append( ", " );
		}

		sonarData.setArgs( sb.toString() );
	}*/

	private ColectData getIdentifyClient() {
		try {
			return context.getBean( ColectData.class );
		} catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}
}
