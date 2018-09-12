package com.jonas.gas.config;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.jonas.gas.dao.AddressRepository;
import com.jonas.gas.dao.GasRepository;
import com.jonas.gas.dao.ScheduleRepository;
import com.jonas.gas.dao.ServiceRepository;
import com.jonas.gas.dao.StationRepository;
import com.jonas.gas.model.Address;
import com.jonas.gas.model.Gas;
import com.jonas.gas.model.Schedule;
import com.jonas.gas.model.Service;
import com.jonas.gas.model.Station;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@Component	
public class GasSaxParser extends DefaultHandler {


	final String STATION_TAG="pdv";
	final String ADDRESS_TAG="adresse";
	final String CITY_TAG="ville";
	final String SCHEDULE_TAG="ouverture";
	final String SERVICE_LIST_TAG="services";
	final String SERVICE_TAG="service";
	final String PRICE_TAG="prix";


	final String ID_ATTRIBUTE="id";
	final String CP_ATTRIBUTE="cp";
	final String LATITUDE_ATTRIBUTE="latitude";
	final String LONGITUDE_ATTRIBUTE="longitude";
	final String ROAD_ATTRIBUTE="pop";
	final String START_OPEN_ATTRIBUTE="debut";
	final String END_OPEN_ATTRIBUTE="fin";
	final String CLOSED_DAYS_ATTRIBUTE="saufjour";

	final String NAME_ATTRIBUTE="nom";
	final String ID_PRICE_ATTRIBUTE="id";
	final String UPDATE_ATTRIBUTE="maj";
	final String VALUE_ATTRIBUTE="valeur";




	String stationXmlFileName;
	List<Station> stations;
	Station stationTmp;
	@Autowired
	GeometryFactory geometryFactory;
	private Address addressTmp;
	private String tmpValue;
	List<Gas> gasListTmp;
	List<Service> serviceListTmp;
	
	ArrayList<String> serviceStrings = new ArrayList<>();
	Set<Service> serviceSet = new HashSet<Service>();
	
	@Autowired
	private StationRepository stationRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private GasRepository gasRepository;
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private ServiceRepository serviceRepository;



	public GasSaxParser()
	{
		
	}
	
	public void loadXml(String stationXmlFileName)
	{
		this.stationXmlFileName = stationXmlFileName;
		
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(Config.class);
		geometryFactory= context.getBean("geometryFactory", GeometryFactory.class);


		stations = new ArrayList<Station>();

		parseDocument();

		printDatas();
		System.out.println("SERVICE SIZE: "+serviceSet.size());
		serviceSet.forEach(T ->{
			System.out.println("SERVICE: "+T.id+", "+T.label);
		});

		
		serviceRepository.saveAll(serviceSet);
		stationRepository.saveAll(stations);
	}


	private void printDatas() {

		// System.out.println(bookL.size());

		int i=0;
		for (Station tmpB : stations) {
			if(i==5)
				break;
			try
			{
				System.out.println("STATION: "+tmpB.getId());
				System.out.println("STATION: "+tmpB.getAddress().getStreet());
				System.out.println("STATION: "+tmpB.getGas().size());
				System.out.println("STATION: "+tmpB.getServices().size());
				System.out.println("STATION: "+tmpB.getServices().size());
			System.out.println(tmpB.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			i++;
		}

	}


	private void parseDocument() {

		// parse

		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {

			SAXParser parser = factory.newSAXParser();

			parser.parse(stationXmlFileName, this);

		} catch (ParserConfigurationException e) {

			System.out.println("ParserConfig error");

		} catch (SAXException e) {

			System.out.println("SAXException : xml not well formed");

		} catch (IOException e) {

			System.out.println("IO error");

		}

	}



	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {

		if (elementName.equalsIgnoreCase(STATION_TAG)) {
			
			stationTmp = new Station();
			stationTmp.setServices(new ArrayList<>());
			addressTmp = new Address();
			stationTmp.setGas(new ArrayList<>());
			//			String longitude = attributes.getValue(LONGITUDE_ATTRIBUTE);
			//			String latitude = attributes.getValue(LATITUDE_ATTRIBUTE);

			// geometryFactory = new GeometryFactory();
			
			long id =Long.parseLong( attributes.getValue("id"));
			stationTmp.setId(id);
			
			String lat = attributes.getValue(LATITUDE_ATTRIBUTE);
			String longitude = attributes.getValue(LONGITUDE_ATTRIBUTE);

			if(lat != "" && longitude!="" )
			{
				Coordinate coordinate = new Coordinate(Double.valueOf(attributes.getValue(LATITUDE_ATTRIBUTE))/100000,
						Double.valueOf(attributes.getValue(LONGITUDE_ATTRIBUTE))/100000);
				Point pos = geometryFactory.createPoint(coordinate);
				stationTmp.setPosition(pos);
			}


			stationTmp.setRoad(attributes.getValue(ROAD_ATTRIBUTE).equals("R"));

			addressTmp.setCp(attributes.getValue(CP_ATTRIBUTE));

			// String utm = converter.latLon2UTM(c.getLat(), c.getLng());



			//			Schedule schedule = new Schedule();
			//			schedule.setStartingTime(startingTime);

		}
		else if(elementName.equalsIgnoreCase(SCHEDULE_TAG))
		{
			Schedule schedule = new Schedule();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime openTime = LocalTime.parse(attributes.getValue(START_OPEN_ATTRIBUTE), formatter);
			LocalTime closeTime = LocalTime.parse(attributes.getValue(END_OPEN_ATTRIBUTE), formatter);


		
			
			schedule.setStartingTime(openTime);
			schedule.setCloseTime(closeTime);

			String closedDays = attributes.getValue(CLOSED_DAYS_ATTRIBUTE);
			Pattern pattern = Pattern.compile(";");
			List<String> closedDayList = Arrays.asList(pattern.split(closedDays, -1));

			ArrayList<DayOfWeek> closedDayOfWeekList = new ArrayList<>();
			for(String day: closedDayList)
			{

				switch(day) {
				case "Lundi":
					closedDayOfWeekList.add(DayOfWeek.MONDAY);
					break;
				case "Mardi":
					closedDayOfWeekList.add(DayOfWeek.TUESDAY);
					break;
				case "Mercredi":
					closedDayOfWeekList.add(DayOfWeek.WEDNESDAY);
					break;
				case "Jeudi":
					closedDayOfWeekList.add(DayOfWeek.THURSDAY);
					break;
				case "Vendredi":
					closedDayOfWeekList.add(DayOfWeek.FRIDAY);
					break;
				case "Samedi":
					closedDayOfWeekList.add(DayOfWeek.SATURDAY);
					break;
				case "Dimanche":
					closedDayOfWeekList.add(DayOfWeek.SUNDAY);
					break;
				}

			}
			schedule.setClosedDays(closedDayOfWeekList);
			scheduleRepository.save(schedule);
			stationTmp.setSchedule(schedule);



		}
		else if(elementName.equalsIgnoreCase(PRICE_TAG))
		{
			Gas gas = new Gas();
			gas.setId(Short.parseShort(attributes.getValue(ID_PRICE_ATTRIBUTE)));
			gas.setName(attributes.getValue(NAME_ATTRIBUTE));

			DateTimeFormatter formatterUpdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime updateTime = LocalDateTime.parse(attributes.getValue(UPDATE_ATTRIBUTE), formatterUpdate);

			gas.setLastUpdate(updateTime);
			gasRepository.save(gas);
			stationTmp.getGas().add(gas);
		}



	}

	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		if(element.equalsIgnoreCase("pdv"))
		{
			stationTmp.setAddress(addressTmp);
			addressRepository.save(addressTmp);
			stations.add(stationTmp);
		}
		else if (element.equalsIgnoreCase(ADDRESS_TAG)) {
			addressTmp.setStreet(tmpValue);
		}
		else if(element.equalsIgnoreCase(CITY_TAG))
		{
			addressTmp.setCity(tmpValue);
		}
		else if(element.equalsIgnoreCase(SERVICE_TAG))
		{
			
			Service service = new Service(tmpValue);
			short serviceId=1;
			if(serviceStrings.contains(tmpValue))
			{
				serviceId = (short)( serviceStrings.indexOf(tmpValue)+1);
			}
			else
			{
				serviceId = (short)( serviceStrings.size()+1);
				serviceStrings.add(tmpValue);
			}
			service.setId(serviceId);
			
			serviceSet.add(service);
			stationTmp.getServices().add(service);
		}

	}

	@Override

	public void characters(char[] ac, int i, int j) throws SAXException {

		tmpValue = new String(ac, i, j);

	}

}
