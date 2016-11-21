package br.com.msv.eye.test;

import org.springframework.stereotype.Component;

import br.com.eye.annotations.Sensor;
import br.com.eye.data.TypesData;

@Component
public class Componente {

	@Sensor(description="Testar metodo - compoenente", tags="test - componente", type=TypesData.API_ENDPOINT)
	public void executar(){
	}

	@Sensor(description="Func cris", tags="test - componente", type=TypesData.SERVICE)
	public void executarCris(){

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		throw  new RuntimeException(" erro insperado");
	}
}
