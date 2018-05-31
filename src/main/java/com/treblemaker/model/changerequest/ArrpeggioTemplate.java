package com.treblemaker.model.changerequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrpeggioTemplate {

		private ArrpegioPosition Verse;
		private ArrpegioPosition Bridge;
		private ArrpegioPosition Chorus;

		@JsonProperty("Verse")
		public ArrpegioPosition getVerse() {
			return Verse;
		}

		@JsonProperty("Verse")
		public void setVerse(ArrpegioPosition verse) {
			Verse = verse;
		}

		@JsonProperty("Bridge")
		public ArrpegioPosition getBridge() {
			return Bridge;
		}

		@JsonProperty("Bridge")
		public void setBridge(ArrpegioPosition bridge) {
			Bridge = bridge;
		}

		@JsonProperty("Chorus")
		public ArrpegioPosition getChorus() {
			return Chorus;
		}

		@JsonProperty("Chorus")
		public void setChorus(ArrpegioPosition chorus) {
			Chorus = chorus;
		}
	}
