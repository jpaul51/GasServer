package com.jonas.gas.model.osm;

import java.util.List;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

public class Place
{

	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	Point coordinate;
	@JsonProperty("osm_id")
	String osmId;
	@JsonProperty("osm_type")
	char osmType;

	List<Double> extent;

	String country;
	@JsonProperty("osm_key")
	String osmKey;// place / boundary
	@JsonProperty("osm_value")
	String osmValue;// village /administrative
	@JsonProperty
	String name;
	String city;
	String postcode;
	@JsonProperty
	String state;// region

	String street;

	String housenumber;


	public Place(Point coordinate, String osmId, char osmType, List<Double> extent, String country, String osmKey,
			String osmValue, String name, String city, String postcode, String state)
	{
		super();
		this.coordinate = coordinate;
		this.osmId = osmId;
		this.osmType = osmType;
		this.extent = extent;
		this.country = country;
		this.osmKey = osmKey;
		this.osmValue = osmValue;
		this.name = name;
		this.city = city;
		this.postcode = postcode;
		this.state = state;
	
	}

	public Place()
	{
		super();
	}

	public Point getCoordinate()
	{
		return coordinate;
	}

	public void setCoordinate(Point coordinate)
	{
		this.coordinate = coordinate;
	}

	public String getOsmId()
	{
		return osmId;
	}

	public void setOsmId(String osmId)
	{
		this.osmId = osmId;
	}

	public char getOsmType()
	{
		return osmType;
	}

	public void setOsmType(char osmType)
	{
		this.osmType = osmType;
	}

	public List<Double> getExtent()
	{
		return extent;
	}

	public void setExtent(List<Double> extent)
	{
		this.extent = extent;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getOsmKey()
	{
		return osmKey;
	}

	public void setOsmKey(String osmKey)
	{
		this.osmKey = osmKey;
	}

	public String getOsmValue()
	{
		return osmValue;
	}

	public void setOsmValue(String osmValue)
	{
		this.osmValue = osmValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	
	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}
	

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getHousenumber()
	{
		return housenumber;
	}

	public void setHousenumber(String housenumber)
	{
		this.housenumber = housenumber;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((extent == null) ? 0 : extent.hashCode());
		result = prime * result + ((housenumber == null) ? 0 : housenumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((osmId == null) ? 0 : osmId.hashCode());
		result = prime * result + ((osmKey == null) ? 0 : osmKey.hashCode());
		result = prime * result + osmType;
		result = prime * result + ((osmValue == null) ? 0 : osmValue.hashCode());
		result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (city == null)
		{
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (coordinate == null)
		{
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (country == null)
		{
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (extent == null)
		{
			if (other.extent != null)
				return false;
		} else if (!extent.equals(other.extent))
			return false;
		if (housenumber == null)
		{
			if (other.housenumber != null)
				return false;
		} else if (!housenumber.equals(other.housenumber))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (osmId == null)
		{
			if (other.osmId != null)
				return false;
		} else if (!osmId.equals(other.osmId))
			return false;
		if (osmKey == null)
		{
			if (other.osmKey != null)
				return false;
		} else if (!osmKey.equals(other.osmKey))
			return false;
		if (osmType != other.osmType)
			return false;
		if (osmValue == null)
		{
			if (other.osmValue != null)
				return false;
		} else if (!osmValue.equals(other.osmValue))
			return false;
		if (postcode == null)
		{
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (state == null)
		{
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street == null)
		{
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Place [coordinate=" + coordinate + ", osmId=" + osmId + ", osmType=" + osmType + ", extent=" + extent
				+ ", country=" + country + ", osmKey=" + osmKey + ", osmValue=" + osmValue + ", name=" + name
				+ ", city=" + city + ", postcode=" + postcode + ", state=" + state + ", street=" + street
				+ ", housenumber=" + housenumber + "]";
	}







	
	
	
	
	
}
