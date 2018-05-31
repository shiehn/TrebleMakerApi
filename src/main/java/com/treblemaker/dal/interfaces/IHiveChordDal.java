package com.treblemaker.dal.interfaces;

import com.treblemaker.model.HiveChord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHiveChordDal extends CrudRepository<HiveChord, Integer> {

    List<HiveChord> findAll();
}
