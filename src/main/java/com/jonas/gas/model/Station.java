package com.jonas.gas.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

@Entity
public class Station
{

	@Id
	long id;

	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	@Column(columnDefinition = "geometry(Point,0)")
	Point position;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Gas> gas;

	@ManyToOne
	Schedule schedule;

	@OneToOne
	Address address;

	@ManyToMany
	List<Service> services;

	boolean road;

	public Station()
	{

	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}

	public List<Gas> getGas()
	{
		return gas;
	}

	public void setGas(List<Gas> gas)
	{
		this.gas = gas;
	}

	public Schedule getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public List<Service> getServices()
	{
		return services;
	}

	public void setServices(List<Service> services)
	{
		this.services = services;
	}

	public boolean isRoad()
	{
		return road;
	}

	public void setRoad(boolean road)
	{
		this.road = road;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((gas == null) ? 0 : gas.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + (road ? 1231 : 1237);
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((services == null) ? 0 : services.hashCode());
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
		Station other = (Station) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (gas == null)
		{
			if (other.gas != null)
				return false;
		} else if (!gas.equals(other.gas))
			return false;
		if (id != other.id)
			return false;
		if (position == null)
		{
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (road != other.road)
			return false;
		if (schedule == null)
		{
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (services == null)
		{
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Station [id=" + id + ", position=" + position.getX() + ", gas=" + gas.size() + ", schedule="
				+ schedule.getStartingTime() + ", address=" + address.city + ", services=" + services.size() + ", road="
				+ road + "]";
	}

}
