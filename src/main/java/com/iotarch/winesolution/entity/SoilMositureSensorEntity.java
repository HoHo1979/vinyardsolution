package com.iotarch.winesolution.entity;

import com.iotarch.winesolution.helper.StringHelper;

public class SoilMositureSensorEntity extends AbstractFirebaseEntity {

	//String sensorKey;String sensorName;SensorTypeEnum sensorType;LatLon latLon;
	
	String sensorName;
	
	SensorTypeEnum sensorType;
	
	double lat;
	
	double lon;
	

	public SoilMositureSensorEntity() {
	
	}
	
	


	@Override
	public String toString() {
		return "SoilMositureSensorEntity [sensorName=" + sensorName + ", sensorType=" + sensorType.name() + ", lat=" + lat
				+ ", lon=" + lon + "]";
	}




	public SoilMositureSensorEntity(String sensorName, SensorTypeEnum sensorType, double lat, double lon) {
		super();
		this.sensorName = sensorName;
		this.sensorType = sensorType;
		this.lat = lat;
		this.lon = lon;
	}




	public String getSensorName() {
		return sensorName;
	}


	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}


	public String getSensorType() {
		
		switch (sensorType) {
		case SOIL_MOISTURE:
			return StringHelper.SOIL_MOISTURE;
		case TEMPERATURE:
			return StringHelper.TEMPERATURE;
		case HUMIDITY:
			return StringHelper.HUMIDITY;
		default:
			return StringHelper.SOIL_MOISTURE;
		}	

	}


	public void setSensorType(String st) {
		
		if(st.equals(StringHelper.HUMIDITY)) {
			sensorType = SensorTypeEnum.HUMIDITY;
		}else if(st.equals(StringHelper.SOIL_MOISTURE)) {
			sensorType = SensorTypeEnum.SOIL_MOISTURE;
		}else if(st.equals(StringHelper.TEMPERATURE)){
			sensorType = SensorTypeEnum.TEMPERATURE;
		}else {
			sensorType=SensorTypeEnum.SOIL_MOISTURE;
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
