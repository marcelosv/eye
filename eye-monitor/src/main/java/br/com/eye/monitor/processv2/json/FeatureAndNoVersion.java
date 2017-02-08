package br.com.eye.monitor.processv2.json;

import java.util.ArrayList;
import java.util.List;

public class FeatureAndNoVersion {

	private String version;
	private int accessNumber;
	private int errorNumber;
	private List<String> errors;
	
	public FeatureAndNoVersion() {
		errors = new ArrayList<String>();
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getAccessNumber() {
		return accessNumber;
	}

	public void addAccessNumber() {
		this.accessNumber++;
	}
	
	public void setAccessNumber(int accessNumber) {
		this.accessNumber = accessNumber;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void addErrorNumber() {
		this.errorNumber++;
	}
	
	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
