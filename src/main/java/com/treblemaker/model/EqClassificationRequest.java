package com.treblemaker.model;

public class EqClassificationRequest {

    private String phase;
    private Integer harmonicid;
    private Integer beatid;

    public EqClassificationRequest(String phase,
                                   Integer harmonicid,
                                   Integer beatid){
        this.phase = phase;
        this.harmonicid = harmonicid;
        this.beatid = beatid;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getHarmonicid() {
        return harmonicid;
    }

    public void setHarmonicid(Integer harmonicid) {
        this.harmonicid = harmonicid;
    }

    public Integer getBeatid() {
        return beatid;
    }

    public void setBeatid(Integer beatid) {
        this.beatid = beatid;
    }
}
