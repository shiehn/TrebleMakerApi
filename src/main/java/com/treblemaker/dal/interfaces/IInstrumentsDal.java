package com.treblemaker.dal.interfaces;

import com.treblemaker.model.Instruments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInstrumentsDal extends CrudRepository<Instruments, Integer> {
}
