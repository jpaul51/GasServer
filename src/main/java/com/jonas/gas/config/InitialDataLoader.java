package com.jonas.gas.config;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.jonas.gas.GasApplication;
import com.jonas.gas.dao.AddressRepository;
import com.jonas.gas.dao.GasRepository;
import com.jonas.gas.dao.ScheduleRepository;
import com.jonas.gas.dao.ServiceRepository;
import com.jonas.gas.dao.StationRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>
{

	@Autowired
	GasSaxParser saxParser;
	@Autowired
	GraphHopper graphHopper;
	
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	GasRepository gasRepository;
	@Autowired
	ScheduleRepository scheduleRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	StationRepository stationRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0)
	{
		// TODO Auto-generated method stub
		Resource resource = new ClassPathResource("PrixCarburants_instantane.xml");

		if (GasApplication.resetGasDb)
		{
//			addressRepository.deleteAll();
//			gasRepository.deleteAll();
//			scheduleRepository.deleteAll();
//			scheduleRepository.deleteAll();
//			serviceRepository.deleteAll();
//			stationRepository.deleteAll();
			try
			{
				System.out.println("PATH: " + resource.getURI().getPath());

				saxParser.loadXml(resource.getURI().getPath());

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Resource resourceOSM = new ClassPathResource("france-latest.osm.pbf");
		try
		{
			graphHopper.setDataReaderFile(resourceOSM.getURI().getPath());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		graphHopper.setGraphHopperLocation("/mnt/d/OxygenWorkspace/gas/target/classes/");
		graphHopper.setEncodingManager(new EncodingManager("car"));
		graphHopper.importOrLoad();

		// simple configuration of the request object, see the GraphHopperServlet classs
		// for more possibilities.
		GHRequest req = new GHRequest(46.151084, 5.36296, 45.541529, 4.273002).setWeighting("fastest").setVehicle("car")
				.setLocale(Locale.FRANCE);
		GHResponse rsp = graphHopper.route(req);

		// first check for errors
		if (rsp.hasErrors())
		{
			// handle them!
			rsp.getErrors().forEach(T -> {
				System.out.println(T.getMessage());
			});
			return;
		}

		// use the best path, see the GHResponse class for more possibilities.
		PathWrapper path = rsp.getBest();

		// points, distance in meters and time in millis of the full path
		PointList pointList = path.getPoints();
		double distance = path.getDistance();

		
		
		long timeInMs = path.getTime();

		// InstructionList il = path.getInstructions();
		// // iterate over every turn instruction
		// for(Instruction instruction : il) {
		// instruction.getDistance();
		//
		//
		// }

		System.out.println("DISTANCE: " + distance);

		// or get the json
		// List<Map<String, Object>> iList = il.createJson();
		//
		// // or get the result as gpx entries:
		// List<GPXEntry> list = il.createGPXList();
	}

}
