package com.iotarch.winesolution.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SoilMoistureReadingEntity extends AbstractFirebaseEntity {

	String moisture;
	String status;
	
	Long time;
	
	public SoilMoistureReadingEntity() {
		
	}
	
	public SoilMoistureReadingEntity(String moisture, String status) {
		this.moisture = moisture;
		this.status = status;
	}
	
	@Override
	public Long getTime() {	
		return time;
	}

	@Override
	public void setTime(Long time) {
		this.time=time;
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
		if(time==null)
			return "";
		
		return new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(time));
	}
	
	
	
	
}
