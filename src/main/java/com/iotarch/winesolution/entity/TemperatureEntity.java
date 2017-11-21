package com.iotarch.winesolution.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.iotarch.winesolution.helper.StringHelper;

public class TemperatureEntity extends AbstractFirebaseEntity {
	
	
	public static final String TIME="time";
	public static final String TEMP="temp";
	public static final String HEADER_TIME=StringHelper.TIME;
	public static final String HEADER_TEMP=StringHelper.TEMP;
	

	double temp;
	
	public TemperatureEntity() {
		
	}
	
	public TemperatureEntity(Long time, Double temp) {
		this.time=time;
		this.temp=temp;
	}

	
	public String getDate() {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").format(new Date(time));
	}
	
	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	
	
}
