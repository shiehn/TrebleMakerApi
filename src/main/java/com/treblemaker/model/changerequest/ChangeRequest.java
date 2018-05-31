package com.treblemaker.model.changerequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeRequest {

	public enum Options {
		DECREASE,NEUTRAL,INCREASE;
	}

	private ArrpeggioTemplate arrpeggioTemplates;
	private RhythmLoops rhythmLoops;
	private SynthTemplate synthTemplate;
	
	@JsonProperty("arrpeggioTemplates")
	public ArrpeggioTemplate getArrpeggioTemplates() {
		return arrpeggioTemplates;
	}
	
	@JsonProperty("arrpeggioTemplates")
	public void setArrpeggioTemplates(ArrpeggioTemplate arrpeggioTemplates) {
		this.arrpeggioTemplates = arrpeggioTemplates;
	}
	
	@JsonProperty("rhythmLoops")
	public RhythmLoops getRhythmLoops() {
		return rhythmLoops;
	}
	
	@JsonProperty("rhythmLoops")
	public void setRhythmLoops(RhythmLoops rhythmLoops) {
		this.rhythmLoops = rhythmLoops;
	}
	
	@JsonProperty("synthTemplate")
	public SynthTemplate getSynthTemplate() {
		return synthTemplate;
	}
	
	@JsonProperty("synthTemplate")
	public void setSynthTemplate(SynthTemplate synthTemplate) {
		this.synthTemplate = synthTemplate;
	}
}
