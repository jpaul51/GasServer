package com.jonas.gas.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	long id;
	
	LocalTime startingTime;
	LocalTime closeTime;
	@Embedded
	List<DayOfWeek> closedDays;

	public Schedule() {
		super();
		
		
	}

	public LocalTime getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(LocalTime startingTime) {
		this.startingTime = startingTime;
	}

	public LocalTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalTime closeTime) {
		this.closeTime = closeTime;
	}

	public List<DayOfWeek> getClosedDays() {
		return closedDays;
	}

	public void setClosedDays(List<DayOfWeek> closedDays) {
		this.closedDays = closedDays;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((closeTime == null) ? 0 : closeTime.hashCode());
		result = prime * result + ((closedDays == null) ? 0 : closedDays.hashCode());
		result = prime * result + ((startingTime == null) ? 0 : startingTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (closeTime == null) {
			if (other.closeTime != null)
				return false;
		} else if (!closeTime.equals(other.closeTime))
			return false;
		if (closedDays == null) {
			if (other.closedDays != null)
				return false;
		} else if (!closedDays.equals(other.closedDays))
			return false;
		if (startingTime == null) {
			if (other.startingTime != null)
				return false;
		} else if (!startingTime.equals(other.startingTime))
			return false;
		return true;
	}
	
	
	
	
}
