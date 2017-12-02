package com.iotarch.winesolution.component;

import java.util.HashMap;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.iotarch.winesolution.FirebaseConfiguration;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.iotarch.winesolution.entity.SoilMositureSensorEntity;
import com.iotarch.winesolution.helper.StringHelper;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class MySensorCRUDComponent extends FormLayout{

	SensorTypeEnum sensorType;
	TextField sensorName;
	TextField lat;
	TextField lon;
	
	Binder<SoilMositureSensorEntity> soilMositureBinder;
	
	SoilMositureSensorEntity soilMositureSensorEntity;
	
	public MySensorCRUDComponent(SoilMositureSensorEntity soilMositureSensorEntity) {
		
		lat = new TextField(StringHelper.LAT);
		
		lon = new TextField(StringHelper.LON);
		
		sensorName = new TextField(StringHelper.SENSOR_NAME);
		
		ComboBox<SensorTypeEnum> sensorTypeCombo = new ComboBox<SensorTypeEnum>(StringHelper.SENSOR_TYPE);
		
		sensorTypeCombo.setItems(SensorTypeEnum.values());
		
		sensorTypeCombo.setEmptySelectionAllowed(false);
		
		this.soilMositureSensorEntity=soilMositureSensorEntity;
		
		soilMositureBinder = new Binder<>(SoilMositureSensorEntity.class);
		
		soilMositureBinder.readBean(this.soilMositureSensorEntity);
		
		soilMositureBinder.forField(sensorTypeCombo).withConverter(new Converter<SensorTypeEnum, String>() {

			@Override
			public Result<String> convertToModel(SensorTypeEnum value, ValueContext context) {
		
				String type=context.toString();
				
				switch (value) {
				case SOIL_MOISTURE:
					
					type=StringHelper.SOIL_MOISTURE;
					break;
					
				case TEMPERATURE:
					type=StringHelper.TEMPERATURE;
					break;

				case HUMIDITY:
					type=StringHelper.HUMIDITY;
					break;
					
				default:
					type=StringHelper.SOIL_MOISTURE;
					break;
				}
				
				return Result.ok(type);
			}

			@Override
			public SensorTypeEnum convertToPresentation(String value, ValueContext context) {
				
				SensorTypeEnum sensorTypeEnum=SensorTypeEnum.SOIL_MOISTURE;
				
				switch (value) {
				case StringHelper.SOIL_MOISTURE:
					
					sensorTypeEnum = SensorTypeEnum.SOIL_MOISTURE;
					break;
					
				case StringHelper.TEMPERATURE:
					
					sensorTypeEnum = SensorTypeEnum.TEMPERATURE;
					break;	

				case StringHelper.HUMIDITY:
					
					sensorTypeEnum = SensorTypeEnum.HUMIDITY;	
					break;
				default:
					sensorTypeEnum = SensorTypeEnum.SOIL_MOISTURE;
					break;
				}
				
				return sensorTypeEnum;
			}
		}).bind(SoilMositureSensorEntity::getSensorType,SoilMositureSensorEntity::setSensorType);
		
		soilMositureBinder.forField(lat).withConverter(new StringToDoubleConverter("Cannot Covert Lat"))
			.bind(SoilMositureSensorEntity::getLat,SoilMositureSensorEntity::setLat);
		soilMositureBinder.forField(lon).withConverter(new StringToDoubleConverter("Cannot Covert Lon"))
			.bind(SoilMositureSensorEntity::getLon,SoilMositureSensorEntity::setLon);
		
		soilMositureBinder.bindInstanceFields(this);


		sensorTypeCombo.setItemCaptionGenerator(new ItemCaptionGenerator<SensorTypeEnum>() {
			
			@Override
			public String apply(SensorTypeEnum item) {
				switch (item) {
				case SOIL_MOISTURE:
					return StringHelper.SOIL_MOISTURE;
				case TEMPERATURE:
					return StringHelper.TEMPERATURE;
				case HUMIDITY:
					return StringHelper.HUMIDITY;
				}
				return StringHelper.SOIL_MOISTURE;
			}
		});
		
		sensorTypeCombo.addValueChangeListener(new ValueChangeListener<SensorTypeEnum>() {
			
			@Override
			public void valueChange(ValueChangeEvent<SensorTypeEnum> event) {
				
				sensorType=event.getValue();
			}
		});
		
	
		
		MButton addButton = new MButton(StringHelper.ADD_SENSOR)
				.withEnabled(false)
				.withIcon(VaadinIcons.PLUS)
				.withListener(this::addSensor);
		

		MButton updateButton = new MButton(StringHelper.UPDATE_SENSOR)
				.withEnabled(false)
				.withIcon(VaadinIcons.ARROW_UP)
				.withListener(this::updateSensor);
		
		
		Button newButton = new Button(StringHelper.NEW);
		
		newButton.addClickListener(e->{
			addButton.setEnabled(true);
			soilMositureBinder.setBean(new SoilMositureSensorEntity());
		});
		
		
		MCssLayout buttonLayout=
				new MCssLayout(updateButton,addButton,newButton)
				.withStyleName(ValoTheme.BUTTON_BORDERLESS)
				.withStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
	
		addComponents(sensorName,sensorTypeCombo,lat,lon,buttonLayout);
		
		
		
	}


	public void updateSensor(ClickEvent event) {
		
		SoilMositureSensorEntity soilMositureSensorEntity = getSensorInfo();
		
		HashMap<String,Object> toUpdate = new HashMap<>();
		
		toUpdate.put(soilMositureSensorEntity.getKey(),soilMositureSensorEntity);
		
		FirebaseConfiguration.getFirebaseDB().child("Sensors").updateChildrenAsync(toUpdate);
	
		
	}
	
	public void addSensor(ClickEvent event) {
		
		
		SoilMositureSensorEntity soilMositureSensorEntity = getSensorInfo();
		
		String key=FirebaseConfiguration.getFirebaseDB().child("Sensors").push().getKey();
			
		FirebaseConfiguration.getFirebaseDB().child("Sensors").child(key).setValueAsync(soilMositureSensorEntity);
		
		event.getButton().setEnabled(false);
	}


	private SoilMositureSensorEntity getSensorInfo() {
		
		String sensorName = this.sensorName.getValue();
		Double lat = Double.valueOf(this.lat.getValue());
		Double lon = Double.valueOf(this.lon.getValue());
		
		SoilMositureSensorEntity soilMositureSensorEntity=soilMositureBinder.getBean();
		
		soilMositureSensorEntity.setSensorName(sensorName);
		soilMositureSensorEntity.setLat(lat);
		soilMositureSensorEntity.setLon(lon);
		
		if(soilMositureSensorEntity.getKey()!=null)
			System.out.println(soilMositureSensorEntity.getKey());

		return soilMositureSensorEntity;
	}
	
	public Binder<SoilMositureSensorEntity> getSoilMositureBinder() {
		return soilMositureBinder;
	}


	public void setSoilMositureBinder(Binder<SoilMositureSensorEntity> soilMositureBinder) {
		this.soilMositureBinder = soilMositureBinder;
	}

}
