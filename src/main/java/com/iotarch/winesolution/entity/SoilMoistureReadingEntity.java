package com.iotarch.winesolution.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SoilMoistureReadingEntity extends AbstractFirebaseEntity {

	String moisture;
	String status;
	
	public SoilMoistureReadingEntity() {
		
	}
	
	public SoilMoistureReadingEntity(String moisture, String status) {
		this.moisture = moisture;
		this.status = status;
	}
	
	

	public String getMoisture() {
		return moisture;
	}

	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(time));
	}
	
	
	
	
}
