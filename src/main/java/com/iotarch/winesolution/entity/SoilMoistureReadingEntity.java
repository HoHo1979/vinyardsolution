package com.iotarch.winesolution.entity;

public class SoilMoistureReadingEntity extends AbstractFirebaseEntity {

	String reading;
	String status;
	
	public SoilMoistureReadingEntity() {
		
	}
	
	public SoilMoistureReadingEntity(String reading, String status) {
		super();
		this.reading = reading;
		this.status = status;
	}
	
	
	public String getReading() {
		return reading;
	}
	public void setReading(String reading) {
		this.reading = reading;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
