package br.com.msv.eye.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eye.annotations.Sensor;
import br.com.eye.config.Config;
import br.com.eye.data.TypesData;

@SpringBootApplication
@RestController
//@ComponentScan(basePackages="br.com.msv.eye")
@ImportAutoConfiguration(value=Config.class)
public class ApplicationTest {

	@Autowired
	private Componente componente;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class, args);
	}

	@Sensor(description="Testar metodo", tags="test", type=TypesData.API_ENDPOINT)
	@RequestMapping("/testar")
	public void testar() {
		
		componente.executar();
		componente.executarCris();
		
		System.out.println("testar...");
	}
	
}
