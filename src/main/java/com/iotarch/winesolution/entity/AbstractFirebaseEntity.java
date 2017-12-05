package com.iotarch.winesolution.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class AbstractFirebaseEntity {
	
	String key;
	
	Long time;
	
	String sensorName;
	
	public AbstractFirebaseEntity() {
		
	}
	
	public AbstractFirebaseEntity(String sensorName) {
		this.sensorName=sensorName;
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	
	
	
	
}
