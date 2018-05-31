package com.treblemaker.dal.interfaces;

import com.treblemaker.model.BeatLoop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBeatLoopsDal extends CrudRepository<BeatLoop, Long> {

	List<BeatLoop> findByNormalizedLength(int normalizedLength);
	List<BeatLoop> findAll();
}
