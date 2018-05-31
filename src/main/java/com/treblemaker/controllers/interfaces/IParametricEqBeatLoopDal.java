package com.treblemaker.controllers.interfaces;

import com.treblemaker.model.parametriceq.ParametricEqBeatLoop;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableAutoConfiguration
@Repository
public interface IParametricEqBeatLoopDal extends CrudRepository<ParametricEqBeatLoop, Integer> {

    ParametricEqBeatLoop findOneByBeatLoopId(int beatLoopId);
}
