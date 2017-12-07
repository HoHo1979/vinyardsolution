package com.iotarch.winesolution.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public abstract class AbstractFirebaseEntity {
	
	@Exclude
	String key;
	
	Long time;
	
	String sensorName;
	
	public AbstractFirebaseEntity() {
		
	}
	

	public String getKey() {
		return key;
	}

	@Exclude
	public void setKey(String key) {
		this.key = key;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	
	
	
	
}
