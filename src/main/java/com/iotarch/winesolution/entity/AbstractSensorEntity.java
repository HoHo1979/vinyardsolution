package com.iotarch.winesolution.entity;

import com.vaadin.tapio.googlemaps.client.LatLon;

public class AbstractSensorEntity {
	
	String sensorKey;
	
	String sensorName;
	
	SensorTypeEnum sensorType;
	
	LatLon latLon;


	public AbstractSensorEntity() {
		
	}
	
	public AbstractSensorEntity(String sensorName,SensorTypeEnum sensorType) {
		this.sensorName=sensorName;
		this.sensorType=sensorType;
	}
	
	
	public LatLon getLatLon() {
		return latLon;
	}

	public void setLatLon(LatLon latLon) {
		this.latLon = latLon;
	}
	
	public String getSensorID() {
		return sensorKey;
	}

	public void setSensorID(String sensorID) {
		this.sensorKey = sensorID;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public SensorTypeEnum getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorTypeEnum sensorType) {
		this.sensorType = sensorType;
	}
	
	
	
	

}
