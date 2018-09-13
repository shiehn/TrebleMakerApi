package com.treblemaker.model.composition;

public class CompositionDto {

    public Integer id;
    public String compositionUid;
    public String date;
    public Integer numOfMelodies;

    public CompositionDto() {
    }

    public CompositionDto(Composition composition) {
        this.id = composition.getId();
        this.compositionUid = composition.getCompositionUid();
        this.date = composition.getDate();
        this.numOfMelodies = composition.getNumOfMelodies();
    }
}
