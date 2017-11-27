package com.iotarch.winesolution.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TempHumEntity extends AbstractFirebaseEntity {

	String temperature;
	String humidity;
	Long time;
	
	public TempHumEntity() {
		
	}
	
	public TempHumEntity(String temperature, String humidity, Long time) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.time = time;
	}
	
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	
	
}
