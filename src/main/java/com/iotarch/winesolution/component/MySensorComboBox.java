package com.iotarch.winesolution.component;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iotarch.winesolution.entity.AbstractFirebaseSensorEntity;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.UI;

public class MySensorComboBox<T extends AbstractFirebaseSensorEntity> extends ComboBox<T>{

	
	Class<T> type;
	List<T> items;
	DatabaseReference ref;
	UI ui;
	SensorTypeEnum sensorType;
	
	public MySensorComboBox(DatabaseReference ref,Class<T> type,SensorTypeEnum sensorType) {
		this.type=type;
		this.ref=ref;
		this.sensorType = sensorType;
		setDataProvider();	
	}
	

	public void setItemCaptionGenerator() {
		
		ItemCaptionGenerator<T> itemCaptionGenerator = new ItemCaptionGenerator<T>() {

			@Override
			public String apply(T item) {
				return item.getSensorName();
			}
		};
		
		super.setItemCaptionGenerator(itemCaptionGenerator);
	}
	
	
	public void setDataProvider() {
		
		items = new CopyOnWriteArrayList<>();
		ListDataProvider<T> listDataProvider = new ListDataProvider<>(items);
		
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				dataSnapshot.getChildren().forEach(x->{
				
					
					
					T t = x.getValue(type);
					
					if(t.getsTypeEnum().equals(sensorType)) {
						t.setKey(x.getKey());
						items.add(t);
					}
					
				});
				
				getUI().access(new Runnable() {
					
					@Override
					public void run() {
						
						listDataProvider.refreshAll();
						
					}
				});
				
			}
			
			
			
			
			@Override
			public void onCancelled(DatabaseError dataSnapshot) {
				
				
			}
		});
			
		
		
		
		super.setDataProvider(listDataProvider);
	}
	
}
