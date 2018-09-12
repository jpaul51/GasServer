package com.jonas.gas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graphhopper.GraphHopper;

@Service
public class RoutingService
{

	@Autowired
	GraphHopper graphHopper;
	
	
	public RoutingService()
	{
		
	}
	
	
	
	
	
}
