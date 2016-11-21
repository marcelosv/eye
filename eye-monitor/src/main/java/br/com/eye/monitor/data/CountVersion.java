package br.com.eye.monitor.data;

import java.io.Serializable;

public class CountVersion implements Serializable {

	private static final long serialVersionUID = 8897953319544086517L;

	private int total = 0;
	private int error = 0;
	private String perc = "";

	public void addTotal() {
		total++;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void addError() {
		error++;
	}
	
	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getPerc() {
		return perc;
	}

	public void setPerc(String perc) {
		this.perc = perc;
	}

}
