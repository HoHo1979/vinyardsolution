package com.iotarch.winesolution.ui.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.iotarch.winesolution.entity.SoilMositureEntity;
import com.iotarch.winesolution.entity.SoilMositureSensorEntity;
import com.iotarch.winesolution.helper.GoogleMapAPI;
import com.vaadin.board.Board;
import com.vaadin.navigator.View;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.VerticalLayout;

public class SoilMoistureView extends VerticalLayout implements View {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6094238691037619821L;
	
	Board board;
	public static final String NAME="soilMoistureView";
	
	
	GoogleMap googleMap;
	
	List<SoilMositureSensorEntity> soilMositureSensors;

	public SoilMoistureView() {
		
		board = new Board();

		createSensorData();
				
		createGoogleMap();
		
		board.addRow(googleMap);
        
		addComponent(board);
	}

	private void createSensorData() {
		
		
		soilMositureSensors = new ArrayList<>();
		
		for(int s=0;s<5;s++) {
			int n=s+1;
			SoilMositureSensorEntity sensor=new SoilMositureSensorEntity("Sensor "+n,SensorTypeEnum.SOIL_MOISTURE);
			
			double x = new Random().nextDouble()/(new Random().nextInt(10)+1);
			
			double lat=-33.867+x;
			double lon=151.206-x;
			
			sensor.setLatLon(new LatLon(lat, lon));
			
			List<SoilMositureEntity> mositureReadings = new ArrayList<>();
			
			for(int sr=0;sr<30;sr++) {
				
				SoilMositureEntity mositureEntity = new SoilMositureEntity(new Date().getTime(),new Random().nextDouble()*new Random().nextInt(50));
				mositureReadings.add(mositureEntity);
				
			}
			
			
			
			sensor.setSoilSensorReadings(mositureReadings);
			
			soilMositureSensors.add(sensor);
		}
			
	}

	private void createGoogleMap() {
		googleMap = new GoogleMap(GoogleMapAPI.KEY, null, null);
        // uncomment to enable Chinese API.
        //googleMap.setApiUrl("maps.google.com.au");
        googleMap.setCenter(new LatLon(-33.867, 151.206));
        googleMap.setZoom(10);
        googleMap.setSizeFull();
        
        
        for(SoilMositureSensorEntity sensor:soilMositureSensors) {
        
        	 System.out.println(sensor.getSensorName()+" "+sensor.getLatLon().getLat()+" "+sensor.getLatLon().getLon());
        	 GoogleMapMarker gMapMarker = new GoogleMapMarker(sensor.getSensorName(), new LatLon(sensor.getLatLon().getLat(),sensor.getLatLon().getLon()), true);
             googleMap.addMarker(gMapMarker);
             
        }
        
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16); 	
       
	}


}
