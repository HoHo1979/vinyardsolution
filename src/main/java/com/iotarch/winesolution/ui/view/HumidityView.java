package com.iotarch.winesolution.ui.view;

import java.util.Date;

import org.vaadin.viritin.button.MButton;

import com.google.firebase.database.DatabaseReference;
import com.iotarch.winesolution.FirebaseConfiguration;
import com.iotarch.winesolution.component.MySensorComboBox;
import com.iotarch.winesolution.dateprovider.MyFirebaseCRUDDataProvider;
import com.iotarch.winesolution.dateprovider.MyFirebaseDataProvider;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.iotarch.winesolution.entity.SoilMositureSensorEntity;
import com.iotarch.winesolution.entity.TempHumEntity;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataProviderSeries;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.RangeSelector;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.board.Board;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;

public class HumidityView extends VerticalLayout implements View{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025736417349283883L;
	
	public static final String NAME="humidityView";

	Board board;
	
	Chart humidityCart;
	
	MyFirebaseDataProvider<TempHumEntity> humidityProvider;
	
	DataProviderSeries<TempHumEntity> sereis;
	
	Grid<TempHumEntity> tempHumGrid;
	
	MySensorComboBox<SoilMositureSensorEntity> sensorComboBox; 
	
	
	public HumidityView() {
		
		
		DatabaseReference ref=FirebaseConfiguration.getFirebaseDB().child("Sensors");
		
		sensorComboBox = new MySensorComboBox<>(ref,SoilMositureSensorEntity.class,SensorTypeEnum.HUMIDITY);
		
		sensorComboBox.setItemCaptionGenerator();
		
		sensorComboBox.setDataProvider();
		
		sensorComboBox.addValueChangeListener(new ValueChangeListener<SoilMositureSensorEntity>() {
			
			@Override
			public void valueChange(ValueChangeEvent<SoilMositureSensorEntity> event) {
				
				Notification.show(event.getValue().getSensorName());
				System.out.println(event.getValue().getSensorType());
			}
		});
		
		
		tempHumGrid = new Grid<>(TempHumEntity.class);
		
		tempHumGrid.removeColumn("key");
		
		Column<TempHumEntity, Long> column=(Column<TempHumEntity, Long>) tempHumGrid.getColumn("time");
		
		column.setRenderer(t->new Date(t),new TextRenderer());
		
		createBoard();
		
		createChart();
		
		getHumidityDate();
		
		tempHumGrid.setDataProvider(humidityProvider);
		
		board.addRow(sensorComboBox);
		
		board.addRow(humidityCart);
		
		board.addRow(tempHumGrid);
		
		addComponent(board);
	}


	private void getHumidityDate() {
		
		
		
		
	}


	private void createChart() {
		
		
		
		humidityCart = new Chart(ChartType.CANDLESTICK);
		humidityCart.setTimeline(true);
		Configuration conf = humidityCart.getConfiguration();
		conf.getxAxis().setType(AxisType.DATETIME);
		
		RangeSelector rangeSelector = new RangeSelector();
		rangeSelector.setSelected(1);
		conf.setRangeSelector(rangeSelector);

		XAxis xAxis = new XAxis();
		xAxis.setTitle("Time of the Day");
		
		YAxis yAxis = new YAxis();
		yAxis.setTitle("Humidity in Percnetage");
		
		conf.addxAxis(xAxis);
		conf.addyAxis(yAxis);
		
		DatabaseReference reference = FirebaseConfiguration.getFirebaseDB().child("MyTemp");
		
		humidityProvider = new MyFirebaseDataProvider<TempHumEntity>(reference, TempHumEntity.class);
		
		
		sereis = new DataProviderSeries<>(humidityProvider);
		sereis.setName("Humidity Series");
		
		sereis.setY(y->{
			return Double.valueOf(y.getHumidity());
		});
		
		sereis.setX(x->{
			
			return new Date(x.getTime());
			
		});
		
		
		PlotOptionsLine line = new PlotOptionsLine();
	    line.setColor(SolidColor.BROWN);
	        	
	    sereis.setPlotOptions(line);
		
		conf.addSeries(sereis);
	
		humidityCart.drawChart();
		
		
	}


	private void createBoard() {
		
		board = new Board();

	}


}
