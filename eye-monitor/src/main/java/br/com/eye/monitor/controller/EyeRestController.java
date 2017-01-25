package br.com.eye.monitor.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eye.monitor.processv2.FeatureAndPercentageProcess;
import br.com.eye.monitor.processv2.FeaturesAndNoProcess;
import br.com.eye.monitor.processv2.VersionsAndTotalProcess;
import br.com.eye.monitor.processv2.json.FeatureAndNo;

@RestController
@RequestMapping(path = "v2")
public class EyeRestController {

	private static final String CLIENT_PARAM = "client";
	private static final String SYSTEM_PARAM = "system";
	
	@Autowired
	private FeaturesAndNoProcess featuresAndNoProcess;
	
	@Autowired
	private VersionsAndTotalProcess versionsAndTotalProcess;
	
	@Autowired
	private FeatureAndPercentageProcess featureAndPercentageProcess;
	
	@RequestMapping(path = "/featuresandno/{system}")
	public Map<String, FeatureAndNo> featuresAndNo(
			@Valid @NotNull @PathVariable(SYSTEM_PARAM) String system, 
			@RequestParam(value=CLIENT_PARAM, required=false) String client){
		
		Map<String, FeatureAndNo> featuresAndNo = featuresAndNoProcess.getFeaturesAndNo(system, client);
		return featuresAndNo;
	}
	
	@RequestMapping(path = "/versionsandtotal/{system}")
	public Map<String, Double> versionsAndTotal(
			@Valid @NotNull @PathVariable(SYSTEM_PARAM) String system, 
			@RequestParam(value=CLIENT_PARAM, required=false) String client){
		
		Map<String, Double> versionsAndTotal = versionsAndTotalProcess.getVersionsAndTotal(system, client);
		return versionsAndTotal;
	}
	
	@RequestMapping(path = "/featureandpercentage/{system}")
	public Map<String, Double> featureAndPercentage(
			@Valid @NotNull @PathVariable(SYSTEM_PARAM) String system, 
			@RequestParam(value=CLIENT_PARAM, required=false) String client){
		
		Map<String, Double> featureAndPercentage = featureAndPercentageProcess.getFeatureAndPercentage(system, client);
		return featureAndPercentage;
	}
	
}
