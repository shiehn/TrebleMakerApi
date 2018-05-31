package com.treblemaker.dal.interfaces;

import com.treblemaker.model.stations.StationTrack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStationTrackDal extends CrudRepository<StationTrack, Integer> {
    List<StationTrack> findAll();
}