package com.jonas.gas.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {




	@Id
	public short id;
	public String label;
	
	
	public Service() {
		super();
	}

	

	public Service(String label) {
		super();
		this.label = label;
	}



	public Service(short id, String label) {
		super();
		this.id = id;
		this.label = label;
	}


	public short getId() {
		return id;
	}


	public void setId(short id) {
		this.id = id;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		Service other = (Service) obj;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}





	
	
	
	
}
