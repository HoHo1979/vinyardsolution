package com.iotarch.winesolution.entity;

import java.util.List;

public class SoilMositureSensorEntity extends AbstractSensorEntity {

	//String sensorKey;String sensorName;SensorTypeEnum sensorType;LatLon latLon;
	
	
	List<SoilMositureEntity> soilSensorReadings;
	

	public SoilMositureSensorEntity() {
	
	}
	
	public SoilMositureSensorEntity(String sensorName,SensorTypeEnum sensorTypeEnum) {
		super(sensorName,sensorTypeEnum);
	}
	
	public List<SoilMositureEntity> getSoilSensorReadings() {
		return soilSensorReadings;
	}

	public void setSoilSensorReadings(List<SoilMositureEntity> soilSensorReadings) {
		this.soilSensorReadings = soilSensorReadings;
	}
	
	
	
	
}
