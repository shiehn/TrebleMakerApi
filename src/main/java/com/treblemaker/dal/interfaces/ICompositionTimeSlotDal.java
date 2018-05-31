package com.treblemaker.dal.interfaces;

import com.treblemaker.model.composition.CompositionTimeSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompositionTimeSlotDal extends CrudRepository<CompositionTimeSlot, Integer> {

    List<CompositionTimeSlot> findByRated(int rated);

    List<CompositionTimeSlot> findAll();
}