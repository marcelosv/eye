package br.com.eye.monitor.data;

import java.util.HashMap;
import java.util.Map;

public class VersionsTotal {
	private Map<String, CountVersion> lista = new HashMap<String, CountVersion>();
	private int total;

	public Map<String, CountVersion> getLista() {
		return lista;
	}

	public void setLista(Map<String, CountVersion> lista) {
		this.lista = lista;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
