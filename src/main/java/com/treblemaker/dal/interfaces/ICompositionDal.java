package com.treblemaker.dal.interfaces;

import com.treblemaker.model.composition.Composition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompositionDal extends CrudRepository<Composition, Integer> {

    Composition findByCompositionUid(String compositionUid);

    List<Composition> findAll();
}
