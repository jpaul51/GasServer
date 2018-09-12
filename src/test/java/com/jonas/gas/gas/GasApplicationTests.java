package com.jonas.gas.gas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jonas.gas.dao.StationRepository;
import com.jonas.gas.model.Station;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasApplicationTests {


	 private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	 

	@Autowired StationRepository stationRepository;

	ArrayList<Station> stations;


	@Before
	public void contextLoads() {
		

		ArrayList<Station> stations = (ArrayList<Station>) stationRepository.findAll();
		this.stations = stations;
		System.out.println("STATION1");
		logger.debug("debug test");
        logger.info("info test");
        logger.warn("warn test");
        logger.error("error test");

		//		for(Station station : stations)
		//		{
		//			assert(station.getGas().size()>0);
		//			assert(station.getPosition().getX() != 0);
		//			assert(station.getPosition().getY() != 0);
		////			assert(station.getAddress().getCp().length()==5);
		//		}	



	}

	@After
	public void restoreStreams() {
		
	}

	@Test
	public void stationNotEmpty()
	{
		System.out.println("STATION");
		logger.debug("debug test");
        logger.info("info test");
        logger.warn("warn test");
        logger.error("error test");
		assert(stations.size()>0);
	}






}






























