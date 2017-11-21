package com.iotarch.winesolution.entity;

public abstract class AbstractFirebaseEntity {
	
	String key;
	
	Long time;

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
	
	
	
}
