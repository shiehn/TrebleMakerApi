package com.treblemaker.dal.interfaces;

import com.treblemaker.model.SynthTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISynthTemplateDal extends CrudRepository<SynthTemplate, Integer> {

    List<SynthTemplate> findAll();
}
