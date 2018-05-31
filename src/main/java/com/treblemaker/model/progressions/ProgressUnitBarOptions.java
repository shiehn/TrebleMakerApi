package com.treblemaker.model.progressions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treblemaker.model.HarmonicLoop;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"harmonicLoopOptions"})
public class ProgressUnitBarOptions {

    private List<HarmonicLoop> harmonicLoopOptions = new ArrayList<>();

    public List<HarmonicLoop> getHarmonicLoopOptions() {
        return harmonicLoopOptions;
    }

    public void setHarmonicLoopOptions(List<HarmonicLoop> harmonicLoopOptions) {
        this.harmonicLoopOptions = harmonicLoopOptions;
    }
}
