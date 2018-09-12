package com.jonas.gas.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jonas.gas.model.Address;



@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

}
