package com.iotarch.winesolution.entity;

public class HumidityEntity extends AbstractFirebaseEntity {
	
	Double humidity;
	
	public HumidityEntity() {
		
	}
	
	public HumidityEntity(Long time, Double humidity) {
		this.time=time;
		this.humidity=humidity;
	}
	

	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	

}
