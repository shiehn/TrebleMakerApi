package com.treblemaker.dal.interfaces;

import com.treblemaker.model.RhythmicAccents;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRhythmicAccentsDal extends CrudRepository<RhythmicAccents, Integer> {
}
