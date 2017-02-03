package br.com.msv.eye.test;

import org.springframework.stereotype.Component;

import br.com.eye.annotations.Sensor;
import br.com.eye.data.TypesData;

@Component
public class Componente {

	@Sensor(description="Endpoint - Método 1", tags="test - componente", type=TypesData.API_ENDPOINT)
	public void executar1(){
	}
	
	@Sensor(description="Endpoint - Método 2", tags="test - componente", type=TypesData.API_ENDPOINT)
	public void executar2(){
	}
	
	@Sensor(description="Endpoint - Método 3", tags="test - componente", type=TypesData.API_ENDPOINT)
	public void executar3(){
	}

	@Sensor(description="Endpoint - Metodo 4 - Exception", tags="test - componente exception", type=TypesData.SERVICE)
	public void executarException(){

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		throw  new RuntimeException("Error simulado");
	}
}
