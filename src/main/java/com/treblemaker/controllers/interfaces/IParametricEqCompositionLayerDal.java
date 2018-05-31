package com.treblemaker.controllers.interfaces;

import com.treblemaker.model.parametriceq.ParametricEqCompositionLayer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableAutoConfiguration
@Repository
public interface IParametricEqCompositionLayerDal extends CrudRepository<ParametricEqCompositionLayer, Integer> {

}