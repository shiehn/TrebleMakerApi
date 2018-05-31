package com.treblemaker.dal.interfaces;

import com.treblemaker.model.ChordSequence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChordSequenceDal extends CrudRepository<ChordSequence, Integer> {

    List<ChordSequence> findAll();
}






