package br.com.eye.monitor.processv2.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureAndNo {

	private String name;
	private double average; // media
	private String[] tags;
	private Map<String, FeatureAndNoVersion> featuresAndNoVersion;
	private List<Long> timerList;
	
	public FeatureAndNo() {
		setFeaturesAndNoVersion(new HashMap<String, FeatureAndNoVersion>());
		timerList = new ArrayList<Long>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getAverage() {
		return average;
	}
	
	public void setAverage(double average) {
		this.average = average;
	}
	
	public Map<String, FeatureAndNoVersion> getFeaturesAndNoVersion() {
		return featuresAndNoVersion;
	}
	
	public void setFeaturesAndNoVersion(
			Map<String, FeatureAndNoVersion> featuresAndNoVersion) {
		this.featuresAndNoVersion = featuresAndNoVersion;
	}

	public List<Long> getTimerList() {
		return timerList;
	}

	public void addTimerList(Long timer) {
		timerList.add(timer);
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
}
