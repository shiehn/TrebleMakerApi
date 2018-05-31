package com.treblemaker.dal.interfaces;

import com.treblemaker.model.HarmonicLoop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHarmonicLoopsDal extends CrudRepository<HarmonicLoop, Long> {

    List<HarmonicLoop> findByNormalizedLength(int normalized_length);
    List<HarmonicLoop> findAll();
}
