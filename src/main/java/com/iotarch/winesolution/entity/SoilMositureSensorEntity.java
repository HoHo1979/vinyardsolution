package com.iotarch.winesolution.entity;

import java.io.Serializable;

import com.google.firebase.database.Exclude;
import com.iotarch.winesolution.helper.StringHelper;

public class SoilMositureSensorEntity extends AbstractFirebaseSensorEntity implements Serializable {

	//String sensorKey;String sensorName;SensorTypeEnum sensorType;LatLon latLon;
	
	
	double lat;
	
	double lon;
	
	String sensorType;

	public SoilMositureSensorEntity() {
	
	}
	

	public SoilMositureSensorEntity(String sensorName, SensorTypeEnum sTypeEnum, double lat, double lon) {
		super(sensorName,sTypeEnum);
		this.lat = lat;
		this.lon = lon;
	}


	public String getSensorType() {
		
		switch (sTypeEnum) {
		case TEMPERATURE:
			return StringHelper.TEMPERATURE;
		case HUMIDITY:
			return StringHelper.HUMIDITY;
		case SOIL_MOISTURE:
			return StringHelper.SOIL_MOISTURE;		
		default:
			return StringHelper.SOIL_MOISTURE;
		}
			
	}


	public void setSensorType(String sensorType) {
		
		switch (sensorType) {
		case StringHelper.TEMPERATURE:
			setsTypeEnum(SensorTypeEnum.TEMPERATURE);
			break;
			
		case StringHelper.SOIL_MOISTURE:
			setsTypeEnum(SensorTypeEnum.SOIL_MOISTURE);
			break;
			
		case StringHelper.HUMIDITY:
			setsTypeEnum(SensorTypeEnum.HUMIDITY);
			break;

		default:
			setsTypeEnum(SensorTypeEnum.SOIL_MOISTURE);
			break;
		}

	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLon() {
		return lon;
	}


	public void setLon(double lon) {
		this.lon = lon;
	}


	
	

	
	
	
	
}
