package com.treblemaker.dal.interfaces;

import com.treblemaker.model.AmbienceLoop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAmbienceLoopsDal extends CrudRepository<AmbienceLoop, Long> {

	List<AmbienceLoop> findAll();

	List<AmbienceLoop> findByAudioLengthLessThanEqual(float maxSampleLength);
}
