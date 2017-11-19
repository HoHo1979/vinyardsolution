package com.iotarch.winesolution.entity;

public class HumidityEntity extends AbstractFirebaseEntity {
	
	Long time;
	Double humidity;
	
	
	public HumidityEntity() {
		
	}
	
	public HumidityEntity(Long time, Double humidity) {
		this.time=time;
		this.humidity=humidity;
	}
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	

}
