package com.treblemaker.dal.interfaces;

import com.treblemaker.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGenreDal extends CrudRepository<Genre, Integer> {

    List<Genre> findAll();
}
