package com.jonas.gas.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jonas.gas.model.Station;
import com.vividsolutions.jts.geom.Point;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {

	
	
	@Query(value ="SELECT s FROM Station s ORDER BY distance(:position,s.position)"
		 	)
		List<Station> findClosestStop(@Param("position") Point position,Pageable page);
	
	
}
