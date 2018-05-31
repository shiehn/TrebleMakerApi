package com.treblemaker.controllers.dto;

public class Row {

    private String colOne;
    private String colTwo;
    private String colThree;
    private String colFour;
    private String colFive;

    public Row(String one, String two, String three, String four, String five){

        setColOne(one);
        setColTwo(two);
        setColThree(three);
        setColFour(four);
        setColFive(five);
    }

    public String getColOne() {
        return colOne;
    }

    public void setColOne(String colOne) {
        this.colOne = colOne;
    }

    public String getColTwo() {
        return colTwo;
    }

    public void setColTwo(String colTwo) {
        this.colTwo = colTwo;
    }

    public String getColThree() {
        return colThree;
    }

    public void setColThree(String colThree) {
        this.colThree = colThree;
    }

    public String getColFour() {
        return colFour;
    }

    public void setColFour(String colFour) {
        this.colFour = colFour;
    }

    public String getColFive() {
        return colFive;
    }

    public void setColFive(String colFive) {
        this.colFive = colFive;
    }
}