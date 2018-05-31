package com.treblemaker.model.changerequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrpegioPosition {

		private ChangeRequest.Options speed;
		private boolean rerender;

		@JsonProperty("speed")
		public ChangeRequest.Options getSpeed() {
			return speed;
		}

		@JsonProperty("speed")
		public void setSpeed(ChangeRequest.Options speed) {
			this.speed = speed;
		}

		@JsonProperty("rerender")
		public boolean isRerender() {
			return rerender;
		}

		@JsonProperty("rerender")
		public void setRerender(boolean rerender) {
			this.rerender = rerender;
		}
	}
