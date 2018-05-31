package com.treblemaker.model.parametriceq.eqprediction;

import java.util.ArrayList;
import java.util.List;

import static com.treblemaker.model.IParametricEq.*;

public class ParametricDtoBand {

    private EqBand freq;
    private ParametricDtoRow target;
    private List<ParametricDtoRow> rows = new ArrayList<>();
    private List<String> columns = new ArrayList<>();

    public ParametricDtoBand(){}

    public ParametricDtoBand(EqBand freq){
        this.freq = freq;
    }

    public EqBand getFreq() {
        return freq;
    }

    public void setFreq(EqBand freq) {
        this.freq = freq;
    }

    public ParametricDtoRow getTarget() {
        return target;
    }

    public void setTarget(ParametricDtoRow target) {
        this.target = target;
    }

    public List<ParametricDtoRow> getRows() {
        return rows;
    }

    public void setRows(List<ParametricDtoRow> rows) {
        this.rows = rows;
    }

    public void addRow(ParametricDtoRow row){
        this.rows.add(row);
    }

    public List<String> getColumns() {
        return columns;
    }

    public void addColumn(String column){
        this.columns.add(column);
    }
}
