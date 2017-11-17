package com.iotarch.winesolution.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.iotarch.winesolution.helper.StringHelper;

public class Temperature {
	
	
	public static final String TIME="time";
	public static final String TEMP="temp";
	public static final String HEADER_TIME=StringHelper.TIME;
	public static final String HEADER_TEMP=StringHelper.TEMP;
	
	
	String key;
	long time;
	double temp;
	
	public Temperature(Long time, Double temp) {
		this.time=time;
		this.temp=temp;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getDate() {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").format(new Date(time));
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	
	
}
