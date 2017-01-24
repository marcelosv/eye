package br.com.msv.eye.test;

import org.springframework.stereotype.Component;

import br.com.eye.IdentifyClient;

@Component
public class IdentifyClientImpl implements IdentifyClient {

	@Override
	public String client() {
		return "cliente1";
	}

}
