package br.com.msv.eye.test;

import org.springframework.stereotype.Component;

import br.com.eye.annotations.Sensor;
import br.com.eye.data.TypesData;

@Component
public class Componente {

	@Sensor(description="Method of @Component", tags="test - componente", type=TypesData.API_ENDPOINT)
	public void executar(){
	}

	@Sensor(description="Method of @Componente with exception", tags="test - componente", type=TypesData.SERVICE)
	public void executarException(){

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		throw  new RuntimeException("Error simulado");
	}
}
