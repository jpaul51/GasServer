package com.jonas.gas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jonas.gas.model.Gas;

@Repository
public interface GasRepository extends CrudRepository<Gas, Long>{

}
