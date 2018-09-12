package com.jonas.gas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jonas.gas.model.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long>{

}
