package com.iotarch.winesolution.ui.view;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.google.firebase.database.DatabaseReference;
import com.iotarch.winesolution.FirebaseConfiguration;
import com.iotarch.winesolution.component.MySensorCRUDComponent;
import com.iotarch.winesolution.dateprovider.MyFirebaseCRUDDataProvider;
import com.iotarch.winesolution.dateprovider.MyFirebaseDataProvider;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.iotarch.winesolution.entity.SoilMoistureReadingEntity;
import com.iotarch.winesolution.entity.SoilMositureSensorEntity;
import com.iotarch.winesolution.helper.GoogleMapAPI;
import com.vaadin.board.Board;
import com.vaadin.board.Row;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.Renderer;

public class SoilMoistureView extends VerticalLayout implements View {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6094238691037619821L;
	
	Board board;
	
	public static final String NAME="soilMoistureView";
	
	
	MyFirebaseDataProvider<SoilMoistureReadingEntity> mySensorReadingFirebaseDataProvider;
	
	MyFirebaseCRUDDataProvider<SoilMositureSensorEntity> mySensorFirebaseDataProvier;
	
	Grid<SoilMositureSensorEntity> sensorGrid;
	
	Grid<SoilMoistureReadingEntity> sensorReadingGrid;
	
	Binder<SoilMositureSensorEntity> soilMositureBinder;
	
	GoogleMap googleMap;
	
	List<SoilMositureSensorEntity> soilMositureSensors;
	
	MySensorCRUDComponent mySensorCRUDComponent;

	public SoilMoistureView() {
		
		

		mySensorCRUDComponent = new MySensorCRUDComponent(new SoilMositureSensorEntity());
		mySensorCRUDComponent.setMargin(new MarginInfo(false,false,true,true));
		
		soilMositureBinder = mySensorCRUDComponent.getSoilMositureBinder();
		
		soilMositureSensors = new CopyOnWriteArrayList<SoilMositureSensorEntity>();
		
		DatabaseReference sensorReference = FirebaseConfiguration.getFirebaseDB().child("Sensors");
		
		mySensorFirebaseDataProvier = new MyFirebaseCRUDDataProvider<>(sensorReference,SoilMositureSensorEntity.class);

		sensorGrid = new Grid<SoilMositureSensorEntity>(SoilMositureSensorEntity.class);
		sensorGrid.removeColumn("key");
		sensorGrid.removeColumn("time");
		sensorGrid.removeColumn("lat");
		sensorGrid.removeColumn("lon");
		sensorGrid.removeColumn("sTypeEnum");
		
		sensorGrid.setSelectionMode(SelectionMode.SINGLE);
		sensorGrid.addItemClickListener(new ItemClickListener<SoilMositureSensorEntity>() {

			@Override
			public void itemClick(ItemClick<SoilMositureSensorEntity> event) {
				
				SoilMositureSensorEntity soilMositureSensorEntity=event.getItem();
				
				if(event.getMouseEventDetails().isDoubleClick()) {
					
					
					//remove Item from the database
					System.out.println(soilMositureSensorEntity.getKey());
					
				}else {
					
					String key=event.getItem().getKey();
					soilMositureSensorEntity.setKey(key);
					updateSensorReadingGrid(key);
					soilMositureBinder.setBean(soilMositureSensorEntity);
					
				}
				
			}
		});
		
		sensorReadingGrid = new Grid<SoilMoistureReadingEntity>();

		sensorReadingGrid.addColumn(SoilMoistureReadingEntity::getMoisture)
						 .setCaption("Reading");	
		sensorReadingGrid.addColumn(SoilMoistureReadingEntity::getStatus).setCaption("Status");
		sensorReadingGrid.addColumn(SoilMoistureReadingEntity::getDate).setCaption("Time");
		
		
		
		
		sensorGrid.setDataProvider(mySensorFirebaseDataProvier);
			
//		sensorReadingGrid = new Grid<SoilMoistureReadingEntity>(SoilMoistureReadingEntity.class);
//		sensorReadingGrid.removeColumn("key");
		
		board = new Board();
				
		createGoogleMap();

		board.addRow(new MVerticalLayout(googleMap).withMargin(new MarginInfo(false, false, true, false)));
		
//		VerticalLayout v1 = new VerticalLayout(sensorGrid);
	
//		v1.setMargin(new MarginInfo(true, false, false, false));
		
		sensorGrid.setWidth("400px");
		sensorGrid.setHeight("300px");
		mySensorCRUDComponent.setWidth("300px");

		Row row=board.addRow(sensorGrid,mySensorCRUDComponent);
		
		sensorReadingGrid.setWidth("400px");
		sensorReadingGrid.setHeight("300px");
		
		board.addRow(sensorReadingGrid);
        
		addComponent(board);
	}


	public void updateSensorReadingGrid(String key) {
		
		if(key!=null) {
		
		DatabaseReference sensorDataReference=FirebaseConfiguration.getFirebaseDB().child("SoilMoisture").child(key);
		
		mySensorReadingFirebaseDataProvider = new MyFirebaseDataProvider<>(sensorDataReference,SoilMoistureReadingEntity.class);
	
		sensorReadingGrid.setDataProvider(mySensorReadingFirebaseDataProvider);

		
		}
		
		
	}


	private void createGoogleMap() {
		
		//Get your own GooleMapAPIKEY
		googleMap = new GoogleMap(GoogleMapAPI.KEY, null, null);
        // uncomment to enable Chinese API.
        //googleMap.setApiUrl("maps.google.com.au");
        googleMap.setCenter(new LatLon(-33.867, 151.206));
        googleMap.setZoom(10);
        googleMap.setSizeFull();
        
       
        
        
        
        for(SoilMositureSensorEntity sensor:soilMositureSensors) {
        
        //	 System.out.println(sensor.getSensorName()+" "+sensor.getLatLon().getLat()+" "+sensor.getLatLon().getLon());
        	 GoogleMapMarker gMapMarker = new GoogleMapMarker(sensor.getSensorName(), new LatLon(sensor.getLat(),sensor.getLon()), true);
             googleMap.addMarker(gMapMarker);
             
        }
        
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16); 	
       
	}


}
