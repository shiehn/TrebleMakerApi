package com.treblemaker.dal.interfaces;

import com.treblemaker.model.types.NGram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InGramsDal extends CrudRepository<NGram, Long> {

}
