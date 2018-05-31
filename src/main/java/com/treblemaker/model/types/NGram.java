package com.treblemaker.model.types;

import javax.persistence.*;

@Entity
@Table(name = "n_grams")
public class NGram {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "horizontal_timeslot_id")
    private int horizontalTimeslotId;

    @Column(name = "ng_one")
    private Integer ngOne;

    @Column(name = "ng_two")
    private Integer ngTwo;

    @Column(name = "ng_target")
    private Integer ngTarget;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHorizontalTimeslotId() {
        return horizontalTimeslotId;
    }

    public void setHorizontalTimeslotId(int horizontalTimeslotId) {
        this.horizontalTimeslotId = horizontalTimeslotId;
    }

    public Integer getNgOne() {
        return ngOne;
    }

    public void setNgOne(Integer ngOne) {
        this.ngOne = ngOne;
    }

    public Integer getNgTwo() {
        return ngTwo;
    }

    public void setNgTwo(Integer ngTwo) {
        this.ngTwo = ngTwo;
    }

    public Integer getNgTarget() {
        return ngTarget;
    }

    public void setNgTarget(Integer ngTarget) {
        this.ngTarget = ngTarget;
    }
}
