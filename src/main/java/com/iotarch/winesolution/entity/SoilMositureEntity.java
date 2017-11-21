package com.iotarch.winesolution.entity;

public class SoilMositureEntity extends AbstractFirebaseEntity {
	
	Double moisture;

	public SoilMositureEntity() {
		
	}
	
	public SoilMositureEntity(Long time,Double moisture){
		this.time=time;
		this.moisture = moisture;
	}
	
	
	public Double getMoisture() {
		return moisture;
	}

	public void setMoisture(Double moisture) {
		this.moisture = moisture;
	}
	
	

}
