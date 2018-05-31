package com.treblemaker.model.stations;

import javax.persistence.*;

public class StationDto {

    public StationDto(){}

    public StationDto(Station station) {
        this.id = station.getId();
        this.name = station.getName();
        this.description = station.getDescription();
        this.status = station.getStatus();
        this.bpm = station.getBpm();
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @Column(name = "bpm")
    private int bpm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
