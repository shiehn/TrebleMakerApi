package com.treblemaker.dal.interfaces;

import com.treblemaker.model.analytics.AnalyticsVertical;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnalyticsVerticalDal extends CrudRepository<AnalyticsVertical, Integer> {

    List<AnalyticsVertical> findAll();
}
