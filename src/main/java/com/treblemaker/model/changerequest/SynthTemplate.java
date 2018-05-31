package com.treblemaker.model.changerequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SynthTemplate {

    public List<String> genres = new ArrayList<>();
    public ChangeRequest.Options complexity;
    public ChangeRequest.Options moodDark;
    public ChangeRequest.Options moodLight;
    private boolean rerender;

    @JsonProperty("rerender")
    public boolean isRerender() {
        return rerender;
    }

    @JsonProperty("rerender")
    public void setRerender(boolean rerender) {
        this.rerender = rerender;
    }
}