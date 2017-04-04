package br.com.eye;

import java.lang.reflect.Method;
import java.util.Date;

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

import br.com.eye.annotations.Sensor;
import br.com.eye.data.SonarData;
import br.com.eye.data.TypesData;

@Aspect
@Component
public class EyeAspect extends EyeAbstract {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.application.name:}")
    private String appName;

    @Value("${spring.application.version:}")
    private String appVersion;

    @Value("${eye.url:}")
    private String eyeLink;

    @Value("${eye.disabled:}")
    private String disabled;

    @Value("${eye.user.elasticsearch:}")
    private String user;
    
    @Value("${eye.pass.elasticsearch:}")
	private String pass;
	
    @Value("${spring.cloud.consul.discovery.instanceId:}")
	private String instanceId;
    
    @Value("${eye.tags.active:}")
	private String tagsActive;
    
    @Autowired
    private ApplicationContext context;
    
	@Around("@annotation(br.com.eye.annotations.Sensor) && execution(* *(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		LOGGER.debug("Método interceptado pelo Sonar.");

		// metodo anotado
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        LOGGER.debug("Metodo: "+method.getName());
        
        Sensor sensor = method.getAnnotation(Sensor.class);
        
        Abort abort = new Abort();
        if(abort.isAbortSensor(sensor, disabled, tagsActive)){
            LOGGER.debug("ignorado interceptação");
            return joinPoint.proceed();
        }

		long tempoInicial = System.currentTimeMillis();

		Object returnObject = null;

        // iniciando sonar
        SonarData sonarData = new SonarData();
        
        if( sensor.type() == TypesData.DEPENDENCY ){
        	
        	String microservice = joinPoint.getArgs()[0].toString();
        	String link = joinPoint.getArgs()[1].toString();
        	
        	sonarData.setmOrigin(appName);
        	sonarData.setmDestiny(microservice);
        	sonarData.setmLink(link);
        }
        
        sonarData.setBuildTimestamp(DATE_IN.format(new Date()));
        
        sonarData.setDescription(sensor.description());
        sonarData.setTags(sensor.tags());
        sonarData.setType(sensor.type().getValue());
        sonarData.setTypeFmt(sensor.type().name());
        sonarData.setTimeInit(new Date().getTime());
        sonarData.setServer(appName);
        sonarData.setVersion(appVersion);
        
        ColectData identifyClient = getIdentifyClient();
        
        if( identifyClient != null ){
        	identifyClient.colectData(sonarData);
        }
        
        sonarData.setInstanceId(instanceId);

        injectMemoryAndConfig(sonarData);
        injectArgs(sonarData, joinPoint);
        
        try {
            returnObject = joinPoint.proceed();
        } catch (Throwable throwable) {
        	
        	LOGGER.debug("Erro captado pelo sonar: "+throwable.getMessage());

            StringBuilder messageStackTrace = new StringBuilder();
            if( throwable.getStackTrace() != null ){
                for(StackTraceElement item : throwable.getStackTrace()){
                    messageStackTrace.append(item.toString()).append("<br>");
                }
            }

            sonarData.setMessageStackTrace(messageStackTrace.toString());
        	sonarData.setError(true);
        	sonarData.setMessageError(throwable.getMessage());
        	sonarData.setException(throwable.getClass().getSimpleName());
            throw throwable;
        }
        finally {
        	
        	LOGGER.debug("Finalizando Sonar..");
        	
            long tempoFinal = System.currentTimeMillis() - tempoInicial;
            sonarData.setTimeExec(tempoFinal);

            new Thread(new Send(sonarData, eyeLink, user, pass)).start();
        }
        
        return returnObject;
    }

	private void injectArgs(SonarData sonarData, ProceedingJoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		Object[] args = joinPoint.getArgs();
		
		if( args == null ){
			return;
		}
		
		for(Object arg : args){
			if( arg instanceof Number ){
				sb.append(((Number)arg).doubleValue());
			}else if( arg instanceof String ){
				sb.append(arg.toString());
			}else{
				sb.append("ignored");
			}
			sb.append(", ");
		}
		
		sonarData.setArgs(sb.toString());
	}

	private void injectMemoryAndConfig(SonarData sonarData) {
		Long[] memorys = MemoryJmxInformation.getMemory();
		
		sonarData.setMemory(memorys[0].longValue());
		sonarData.setMemoryFree(memorys[1].longValue());
	}

	private ColectData getIdentifyClient(){
		try{
			return context.getBean(ColectData.class);
		}catch(NoSuchBeanDefinitionException ex){
			return null;
		}
	}	
}
