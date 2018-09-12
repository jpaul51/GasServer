package com.jonas.gas.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.util.PointList;
import com.jonas.gas.config.GasProperties;
import com.jonas.gas.model.Station;
import com.jonas.gas.model.osm.Place;
import com.jonas.gas.response.RoutingResponse;
import com.jonas.gas.service.StationService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@RestController
@Configuration
public class MainController
{

	@Autowired
	GraphHopper graphHopper;

	@Autowired
	StationService stationService;
	@Autowired
	GasProperties gasProperties;
	@Autowired
	GeometryFactory geometryFactory;

	@RequestMapping("/greeting")
	public String hello()
	{
		System.out.println("TEST");
		return "hello";
	}

	@RequestMapping("/stations")
	public ArrayList<Station> getStations()
	{
		return stationService.getStations();

	}

	@RequestMapping("/stations/near")
	public ArrayList<Station> getStationsNearMe(@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude)
	{
		GeometryFactory factory = new GeometryFactory();
		return stationService.getStationsNearPosition(factory.createPoint(new Coordinate(latitude, longitude)));

	}
	
	@RequestMapping("/routing/route")
	public RoutingResponse getRoute(@RequestParam("latitudeStart")double latitudeStart,@RequestParam("longitudeStart") double longitudeStart, 
			@RequestParam("latitudeEnd")double latitudeEnd,@RequestParam("longitudeEnd") double longitudeEnd)
	{
		GHRequest req = new GHRequest(latitudeStart,longitudeStart, latitudeEnd, longitudeEnd).setWeighting("fastest").setVehicle("car")
				.setLocale(Locale.FRANCE);
		GHResponse rsp = graphHopper.route(req);
		PathWrapper path = rsp.getBest();

		// points, distance in meters and time in millis of the full path
		PointList pointList = path.getPoints();
		RoutingResponse routingResponse = new RoutingResponse(path);
		
		
		return routingResponse;
	}
	
	
	@RequestMapping("/geocoding")
	public List<Place> getPlace(@RequestParam("q") String photonQuery) throws IOException
	{
		
//		URL url = new URL("");
		String ip="";
		ArrayList<Place> returnedPlaces = new ArrayList<>();
		
		try {

		String query = java.net.URLEncoder.encode(photonQuery, "UTF-8").replace("+", "%20")	;
			
		URL whatismyip = new URL("http://127.0.0.1:2322/api?q="+query);
		InputStream input =  whatismyip.openStream();

//		BufferedReader in = new BufferedReader(input);
	
		
		
		
		ObjectMapper mapper = new ObjectMapper();	
		 JsonNode rootObj  = mapper.readTree(input);
//		 rootObj.path(arg0)
//		 System.out.println(objectMapper.writeValueAsString(rootObj));
		 JsonNode objectList =  rootObj.path("features");
		 ObjectReader reader = mapper.readerFor(new TypeReference<List<Double>>() {
		 });


			
			
			List<JsonNode> coordinatesList = objectList.findValues("coordinates");
	
			
		
			
			List<JsonNode> propertiesList = objectList.findValues("properties");
			int i = 0;
			for(JsonNode oneCoordinateNode : coordinatesList)
			{
				JsonNode propertyObject = propertiesList.get(i);
//				 System.out.println(mapper.writeValueAsString(oneCoordianteNode));
				
//				System.out.println("TEST: "+test.getX());
//				 JsonNode coordinateArray =oneCoordianteNode.findValue("coordinates");
//				 System.out.println(mapper.writeValueAsString(coordinateArray));
				List<Double> coordinates =  reader.readValue(oneCoordinateNode);
				Point p = geometryFactory.createPoint(new Coordinate(coordinates.get(0), coordinates.get(1)));
				System.out.println("POINT: "+p.getX());
				 System.out.println(mapper.writeValueAsString(propertyObject));
				 
				Place place =  mapper.treeToValue(propertyObject, Place.class);
				 place.setCoordinate(p);
				 returnedPlaces.add(place);
//				System.out.println("PLACE: "+place.toString());
				
				i++;
			}
			



		 
		 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //you get the IP as a String
		return returnedPlaces;
	}
	

	
	
	/**
	 * Custom Object mapper that parses jts geometry objects to geojson
	 * @return
	 */
	@Bean
    public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		  mapper.registerModules(new JtsModule());
	       
//	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}
}
