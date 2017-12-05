package com.iotarch.winesolution.ui.view;

import java.text.DecimalFormat;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iotarch.winesolution.FirebaseConfiguration;
import com.iotarch.winesolution.dateprovider.MyFirebaseCRUDDataProvider;
import com.iotarch.winesolution.entity.HumidityEntity;
import com.iotarch.winesolution.entity.SensorTypeEnum;
import com.iotarch.winesolution.entity.SoilMositureSensorEntity;
import com.iotarch.winesolution.entity.TempHumEntity;
import com.iotarch.winesolution.entity.TemperatureEntity;
import com.iotarch.winesolution.helper.StringHelper;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataProviderSeries;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.board.Board;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

public class TemperatureView extends VerticalLayout implements View, ValueEventListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME="temperature";	
	final int maxReading = 30;
	Chart currentTemperatureChart;
	Chart temperatureChart;
	Board board;
	Grid<TemperatureEntity> temperatureGrid;
	CopyOnWriteArrayList<TemperatureEntity> timeTemperatureList;
	CopyOnWriteArrayList<HumidityEntity> timeHumidityList;
	Grid<HumidityEntity> humidityGrid;
	Configuration conf;
	
	
	ListDataProvider<TemperatureEntity> tempDataProvider;
	ListDataProvider<HumidityEntity> humiDataProvider;
	
	MyFirebaseCRUDDataProvider<SoilMositureSensorEntity> sensorProvider;

	ListSeries gaugeSeries = new ListSeries("Temperature",0);
	
	double currentTemperature;
	
	public TemperatureView() {
		
		timeTemperatureList = new CopyOnWriteArrayList<>();
		
		timeHumidityList = new CopyOnWriteArrayList<>();
		
		humiDataProvider = new ListDataProvider<>(timeHumidityList);
		
		tempDataProvider = new ListDataProvider<>(timeTemperatureList);
		
		createBoard();
		
		initTemperatureChart();

		createCurrentTemperatureChart();
		
		createHumidityChart();
		
		createTemperatureChart();
		
		createTemperatureGrid();
		
		temperatureChart.drawChart(conf);
		
		ComboBox<SoilMositureSensorEntity> mySensorCombo = createSensorComboBox();
		
		
		board.addRow(mySensorCombo);
		
		board.addRow(currentTemperatureChart,temperatureChart);
		
		board.addRow(temperatureGrid);

		
		
		addComponent(board);
	}

	private ComboBox<SoilMositureSensorEntity> createSensorComboBox() {
		
		ComboBox<SoilMositureSensorEntity> mySensorCombo = new ComboBox<>();
		
		
		mySensorCombo.setItemCaptionGenerator(new ItemCaptionGenerator<SoilMositureSensorEntity>() {
			
			@Override
			public String apply(SoilMositureSensorEntity item) {
				
				return item.getSensorName();
			}
		});
		
		DatabaseReference reference = FirebaseConfiguration.getFirebaseDB().child("Sensors");
		
		CopyOnWriteArrayList<SoilMositureSensorEntity> items = new CopyOnWriteArrayList<>();
		
		ListDataProvider<SoilMositureSensorEntity> listDataProvider = new ListDataProvider<>(items);
		
		mySensorCombo.setDataProvider(listDataProvider);
		
		mySensorCombo.addValueChangeListener(new ValueChangeListener<SoilMositureSensorEntity>() {
			
			@Override
			public void valueChange(ValueChangeEvent<SoilMositureSensorEntity> event) {
				
				System.out.println(event.getValue().getKey());
				
			}
		});
		
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				
				dataSnapshot.getChildren().forEach(x->{
					
					
					SoilMositureSensorEntity entity=x.getValue(SoilMositureSensorEntity.class);
					
					if(entity.getSensorType().equals(StringHelper.TEMPERATURE)) {
					
						entity.setKey(x.getKey());
					
						items.add(entity);
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
			public void onCancelled(DatabaseError arg0) {
				
				
			}
		});
		return mySensorCombo;
	}

	private void initTemperatureChart() {
		
		temperatureChart = new Chart();
		
		temperatureChart.setWidth("400px");
		
		conf = temperatureChart.getConfiguration();
		
		
		temperatureChart.setTimeline(true);
		
		conf.setTitle(StringHelper.TEMPERATURE);
		conf.setSubTitle(StringHelper.TEMPERATURE_CHART_SUBTITLE);
		
		conf.getxAxis().setType(AxisType.DATETIME);
	}

	private void createHumidityChart() {
		
		
		YAxis y2 = new YAxis();
		y2.setGridLineWidth(0);
        AxisTitle title = new AxisTitle("Humidity");
        Style style = new Style();
        style.setColor(SolidColor.BLUEVIOLET);
        title.setStyle(style);
        y2.setTitle(title);
        Labels labels = new Labels();
        labels.setFormatter("this.value +' %'");
        style = new Style(); 
        style.setColor(SolidColor.PURPLE);
        labels.setStyle(style);
        y2.setLabels(labels);
        y2.setOpposite(false);
        y2.setLineWidth(2);  
        
        conf.addyAxis(y2);
        	

        DataProviderSeries<HumidityEntity>series = new DataProviderSeries<>(humiDataProvider,HumidityEntity::getHumidity);
        
        series.setName("Humidity");
        
        series.setX(HumidityEntity::getTime);
        
        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        plotOptionsColumn.setColor(SolidColor.LIGHTSKYBLUE);
    
        series.setPlotOptions(plotOptionsColumn);
		
		conf.addSeries(series);
		
		
	}


	private void createBoard() {
		
		board = new Board();
		board.setWidth("100%");
		board.setHeightUndefined();
		
	}

	private void createTemperatureGrid() {
		
		temperatureGrid = new Grid<TemperatureEntity>();
		
		temperatureGrid.setWidth("100%");
	
		temperatureGrid.addColumn(x->x.getDate()).setCaption(TemperatureEntity.HEADER_TIME);
		temperatureGrid
			.addColumn(x->x.getTemp(),new NumberRenderer(new DecimalFormat("##.## '°C'")))
			.setCaption(TemperatureEntity.HEADER_TEMP);
		
			
	
		temperatureGrid.setDataProvider(tempDataProvider);
	
		
		
	}

	private void createTemperatureChart() {
		
	
		YAxis y = new YAxis();
		
		AxisTitle title = new AxisTitle("Temperature");
        Style style = new Style();
        style.setColor(SolidColor.RED);
        y.setTitle(title);
        Labels labels = new Labels();
        labels.setFormatter("this.value +'°C'");
        style = new Style();
        style.setColor(SolidColor.BLACK);
        labels.setStyle(style);
        y.setLabels(labels);	
        y.setOpposite(true);
        
        
        conf.addyAxis(y);
		
        conf.getNavigator().getSeries().setColor(SolidColor.ROSYBROWN);
        conf.getNavigator().setMargin(50);
       
        DataProviderSeries<TemperatureEntity> series = new DataProviderSeries<>(tempDataProvider,TemperatureEntity::getTemp);

		series.setName("Temperature");
		series.setX(TemperatureEntity::getTime);
		
		PlotOptionsLine line = new PlotOptionsLine();
	    line.setColor(SolidColor.BROWN);

		series.setPlotOptions(line);
			
//		RangeSelector rangeSelector = new RangeSelector();	
//		RangeSelectorButton dayButton = new RangeSelectorButton(RangeSelectorTimespan.DAY, "1d");
//		RangeSelectorButton monthButton = new RangeSelectorButton(RangeSelectorTimespan.MONTH,"1m");		
//		rangeSelector.setButtons(dayButton,monthButton);
//		rangeSelector.setSelected(1);
//		ButtonTheme theme = new ButtonTheme();
//		Style sty = new Style();
//		sty.setColor(new SolidColor("#0766d8"));
//		sty.setFontWeight(FontWeight.BOLD);
//		theme.setStyle(style);
//		rangeSelector.setButtonTheme(theme);	
//		conf.setRangeSelector(rangeSelector);
		
		DatabaseReference reference = FirebaseConfiguration.getFirebaseDB().child("MyTemp");
		
		reference.addValueEventListener(this);
		

	    conf.addSeries(series);

	}


	private void createCurrentTemperatureChart() {
		
		currentTemperatureChart = new Chart(ChartType.GAUGE);
		
		currentTemperatureChart.setWidth("300px");
		
		Configuration conf = currentTemperatureChart.getConfiguration();
		conf.setTitle(StringHelper.CURRENT_TEMPERATURE);
		conf.getPane().setStartAngle(-135);
		conf.getPane().setEndAngle(135);
		
		YAxis yaxis = new YAxis();
		yaxis.setTitle("°C");

		// The limits are mandatory
		yaxis.setMin(0);
		yaxis.setMax(50);

		// Other configuration
		yaxis.getLabels().setStep(1);
		yaxis.setTickInterval(5);
		yaxis.setTickLength(10);
		yaxis.setTickWidth(1);
		yaxis.setMinorTickInterval("1");
		yaxis.setMinorTickLength(5);
		yaxis.setMinorTickWidth(1);
		yaxis.setPlotBands(new PlotBand[]{
		        new PlotBand(0, 20,  SolidColor.GREEN),
		        new PlotBand(20, 35,  SolidColor.YELLOW),
		        new PlotBand(35, 50, SolidColor.RED)});
		yaxis.setGridLineWidth(0); // Disable grid

		conf.addyAxis(yaxis);
		
		conf.addSeries(gaugeSeries);
	}

	@Override
	public void onCancelled(DatabaseError arg0) {
		
		
	}


	@Override
	public void onDataChange(DataSnapshot dataSnapshots) {
		
		timeTemperatureList.removeAll(timeTemperatureList);
		timeHumidityList.removeAll(timeHumidityList);
		
		for(DataSnapshot dataSnapshot:dataSnapshots.getChildren()) {
		
			TempHumEntity tempHumEntity=dataSnapshot.getValue(TempHumEntity.class);
			
			TemperatureEntity temperatureEntity = 
					new TemperatureEntity(tempHumEntity.getTime(),Double.valueOf(tempHumEntity.getTemperature()));
			
			HumidityEntity humidityEntity =
					new HumidityEntity(tempHumEntity.getTime(),Double.valueOf(tempHumEntity.getHumidity()));
			
			timeTemperatureList.add(temperatureEntity);
			timeHumidityList.add(humidityEntity);
		
		}
		
		
		TemperatureEntity temperatureEntity=timeTemperatureList.get(timeTemperatureList.size()-1);

		
		getUI().access(new Runnable() {
			
			@Override
			public void run() {
				
				gaugeSeries.updatePoint(0, temperatureEntity.getTemp());
				
				
			}
		});
		
		
		//If put two refershAll at the same access, will cause currentError
		getUI().access(new Runnable() {
			
			@Override
			public void run() {
				humiDataProvider.refreshAll();
				
			}
		});
		
		getUI().access(new Runnable() {
			
			@Override
			public void run() {
				
				tempDataProvider.refreshAll();
				
			}
		});
		
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		
		FirebaseConfiguration.getFirebaseDB().removeEventListener(this);
		
		View.super.beforeLeave(event);
	}





}
