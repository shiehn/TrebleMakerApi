package com.treblemaker.dal.interfaces;

import com.treblemaker.model.Characteristics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICharacteristicsDal extends CrudRepository<Characteristics, Integer> {
}
