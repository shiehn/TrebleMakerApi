package com.treblemaker.model.progressions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.treblemaker.model.types.Composition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.jfugue.pattern.Pattern;

@JsonIgnoreProperties({"jChords", "types", "InitialChord", "gatedLayerGroups"})
public class ProgressionUnit {

    public enum BarCount {
        ONE(1), TWO(2), THREE(3), FOUR(4);

        private final int value;
        BarCount(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ProgressionType {
        VERSE, BRIDGE, CHORUS
    }

    private List<ProgressionUnitBar> progressionUnitBars;

    private int Genre;
    private int BarCount;
    private ProgressionType Type;

    private int Complexity;
    private int MinorToMajor;

    private HashMap<Composition.Layer, Boolean> gatedLayers;

    public ProgressionUnit() {
    }

    public void initBars(int barCount){

        this.setBarCount(barCount);

        progressionUnitBars = new ArrayList<>();

        for (int i = 0; i < getBarCount(); i++) {
            progressionUnitBars.add(new ProgressionUnitBar());
        }

        gatedLayers = new HashMap<>();
    }

    public List<ProgressionUnitBar> getProgressionUnitBars() {
        return progressionUnitBars;
    }

    public void setProgressionUnitBars(List<ProgressionUnitBar> progressionUnitBars){
        this.progressionUnitBars = progressionUnitBars;
    }

    @JsonProperty("Genre")
    public int getGenre() {
        return Genre;
    }

    @JsonProperty("Genre")
    public void setGenre(int genre) {
        Genre = genre;
    }

    @JsonProperty("BarCount")
    public int getBarCount() {
        return BarCount;
    }

    @JsonProperty("BarCount")
    public void setBarCount(int barCount) {
        BarCount = barCount;
    }

    @JsonProperty("Type")
    public ProgressionType getType() {
        return Type;
    }

    @JsonProperty("Type")
    public void setType(ProgressionType type) {
        Type = type;
    }


    @JsonProperty("Complexity")
    public int getComplexity() {
        return Complexity;
    }

    @JsonProperty("Complexity")
    public void setComplexity(int complexity) {
        Complexity = complexity;
    }

    @JsonProperty("MinorToMajor")
    public int getMinorToMajor() {
        return MinorToMajor;
    }

    @JsonProperty("MinorToMajor")
    public void setMinorToMajor(int minorToMajor) {
        MinorToMajor = minorToMajor;
    }

    public HashMap<Composition.Layer, Boolean> getGatedLayers() {
        return gatedLayers;
    }

    public void setGatedLayers(HashMap<Composition.Layer, Boolean> gatedLayerGroups) {
        this.gatedLayers = gatedLayerGroups;
    }

    public boolean isLayerGated(Composition.Layer layer) {
        if (this.getGatedLayers().size() > 0) {
            return this.getGatedLayers().get(layer);
        }

        return false;
    }
}
