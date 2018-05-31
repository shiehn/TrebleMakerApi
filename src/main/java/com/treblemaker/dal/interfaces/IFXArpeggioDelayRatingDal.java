package com.treblemaker.dal.interfaces;

import com.treblemaker.model.fx.FXArpeggioDelayRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFXArpeggioDelayRatingDal extends CrudRepository<FXArpeggioDelayRating, Integer> {

    List<FXArpeggioDelayRating> findAll();
}
