package com.jonas.gas.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jonas.gas.dao.StationRepository;
import com.jonas.gas.model.Station;
import com.vividsolutions.jts.geom.Point;

@Service
public class StationService {

	
	@Autowired
	StationRepository stationRepository;
	
	public StationService()
	{
		
	}
	
	public ArrayList<Station> getStations()
	{
		
		return (ArrayList<Station>) stationRepository.findAll();
		
	}
	
	public ArrayList<Station> getStationsNearPosition(Point point)
	{
		System.out.println(point.getX()+" --- "+point.getY());
		Pageable page = PageRequest.of(0, 10);
		return (ArrayList<Station>) stationRepository.findClosestStop(point,page);
		
	}
}
