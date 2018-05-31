package com.treblemaker.controllers.dto;

public class OutputAndProb {

    private String output;
    private int probability;

    public OutputAndProb(String out, int prob){

        setOutput(out);
        setProbability(prob);
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}