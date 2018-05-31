package com.treblemaker.dal.interfaces;

import com.treblemaker.model.stations.Station;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStationDal extends CrudRepository<Station, Integer> {
    List<Station> findAll();
}