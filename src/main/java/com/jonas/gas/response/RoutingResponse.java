package com.jonas.gas.response;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.graphhopper.PathWrapper;
import com.graphhopper.Trip.Leg;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint3D;
public class RoutingResponse
{

	
	ArrayList<GHPoint3D> pointList = new ArrayList<>();
	double distance;
	double ascend;
	BigDecimal fare;
	private double routeWeight;
	
	public RoutingResponse(PathWrapper path)
	{
		
		PointList pointList = path.getPoints();
		
		
		for(GHPoint3D point : pointList)
		{
			this.pointList.add(new GHPoint3D(point.lat, point.lon, point.ele));
			
		}
		
		this.distance = path.getDistance();
		this.ascend = path.getAscend();
		fare = path.getFare();
//		for (Leg leg : path.getLegs())
//		{
//			leg.
//		}
		this.routeWeight = path.getRouteWeight();
				
		
	}

	public ArrayList<GHPoint3D> getPointList()
	{
		return pointList;
	}

	public void setPointList(ArrayList<GHPoint3D> pointList)
	{
		this.pointList = pointList;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public double getAscend()
	{
		return ascend;
	}

	public void setAscend(double ascend)
	{
		this.ascend = ascend;
	}

	public BigDecimal getFare()
	{
		return fare;
	}

	public void setFare(BigDecimal fare)
	{
		this.fare = fare;
	}

	public double getRouteWeight()
	{
		return routeWeight;
	}

	public void setRouteWeight(double routeWeight)
	{
		this.routeWeight = routeWeight;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(ascend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((fare == null) ? 0 : fare.hashCode());
		result = prime * result + ((pointList == null) ? 0 : pointList.hashCode());
		temp = Double.doubleToLongBits(routeWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RoutingResponse other = (RoutingResponse) obj;
		if (Double.doubleToLongBits(ascend) != Double.doubleToLongBits(other.ascend))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (fare == null)
		{
			if (other.fare != null)
				return false;
		} else if (!fare.equals(other.fare))
			return false;
		if (pointList == null)
		{
			if (other.pointList != null)
				return false;
		} else if (!pointList.equals(other.pointList))
			return false;
		if (Double.doubleToLongBits(routeWeight) != Double.doubleToLongBits(other.routeWeight))
			return false;
		return true;
	}

	
		
	
}
