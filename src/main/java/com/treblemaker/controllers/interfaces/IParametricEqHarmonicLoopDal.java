package com.treblemaker.controllers.interfaces;

import com.treblemaker.model.parametriceq.ParametricEqHarmonicLoop;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableAutoConfiguration
@Repository
public interface IParametricEqHarmonicLoopDal extends CrudRepository<ParametricEqHarmonicLoop, Integer> {

    ParametricEqHarmonicLoop findOneByHarmonicLoopId(int harmonicLoopId);
}