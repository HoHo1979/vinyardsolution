package com.iotarch.winesolution.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class AbstractFirebaseSensorEntity extends AbstractFirebaseEntity {


	SensorTypeEnum sTypeEnum;

	String sensorName;
	
	
	public AbstractFirebaseSensorEntity() {
		
	}
	
	public AbstractFirebaseSensorEntity(String sensorName, SensorTypeEnum sTypeEnum){
		this.sensorName=sensorName;
		this.sTypeEnum=sTypeEnum;
	}
	
	
	@Exclude
	public SensorTypeEnum getsTypeEnum() {
		return sTypeEnum;
	}

	@Exclude
	public void setsTypeEnum(SensorTypeEnum sTypeEnum) {
		this.sTypeEnum = sTypeEnum;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	@Exclude
	@Override
	public String getKey() {
		
		return super.getKey();
	}

	@Exclude
	@Override
	public void setKey(String key) {
		
		super.setKey(key);
	}
	
	
	
	
	
}
