package com.jonas.gas.dao;

import org.springframework.data.repository.CrudRepository;

import com.jonas.gas.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long> {

	@Override
	default <S extends Service> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
