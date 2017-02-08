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
@ImportAutoConfiguration(value=Config.class)
public class ApplicationTest {

	@Autowired
	private Componente componente;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class, args);
	}

	@Sensor(description="EndPoint", tags="test", type=TypesData.API_ENDPOINT)
	@RequestMapping("/test")
	public void testar() {
		componente.executar1();
		componente.executar2();
		componente.executar3();
	}

	@Sensor(description="EndPoint", tags="testError", type=TypesData.API_ENDPOINT)
	@RequestMapping("/testError")
	public void testError() {
		componente.executarException();
	}

}
