package com.treblemaker.model;

import com.treblemaker.model.interfaces.IInfluenceable;

import javax.persistence.*;

@Entity
@Table(name = "characteristics")
public class Characteristics implements IInfluenceable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "characteristic")
    private String characteristic;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}