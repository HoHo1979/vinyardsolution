package com.iotarch.winesolution.ui.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iotarch.winesolution.FirebaseConfiguration;
import com.iotarch.winesolution.entity.HumidityEntity;
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
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.RangeSelector;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.board.Board;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

public class TemperatureView extends VerticalLayout implements View, ValueEventListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME="temperature";	
	final int maxReading = 30;
	final String DEGREE  = "\u00b0";
	Chart currentTemperatureChart;
	Chart temperatureChart;
	Board board;
	Grid<TemperatureEntity> temperatureGrid;
	List<TemperatureEntity> timeTemperatureList;
	List<HumidityEntity> timeHumidityList;
	Grid<HumidityEntity> humidityGrid;
	Configuration conf;
	
	ListDataProvider<TemperatureEntity> tempDataProvider;
	ListDataProvider<HumidityEntity> humiDataProvider;

	
	double currentTemperature;
	
	public TemperatureView() {
		
		long time = new Date().getTime();
		
		createTimeTempeartureData(time,maxReading);
		
		createHumidityData(time,maxReading);
		
		humiDataProvider = new ListDataProvider<>(timeHumidityList);
		
		tempDataProvider = new ListDataProvider<>(timeTemperatureList);
		
		createBoard();
		
		initTemperatureChart();

		createCurrentTemperatureChart();
		
		createHumidityChart();
		
		createTemperatureChart();
		
		createTemperatureGrid();
		
		temperatureChart.drawChart(conf);
		
		board.addRow(currentTemperatureChart,temperatureChart);
		
		board.addRow(temperatureGrid);

		addComponent(board);
	}

	private void initTemperatureChart() {
		temperatureChart = new Chart();
	
		conf = temperatureChart.getConfiguration();
		
		
		temperatureChart.setTimeline(true);
		
		conf.setTitle(StringHelper.TEMP);
		conf.setSubTitle(StringHelper.TEMPERATURE_CHART_SUBTITLE);
		conf.getNavigator().setEnabled(false);
		
		RangeSelector rangeSelector = new RangeSelector();
		rangeSelector.setSelected(1);
		conf.setRangeSelector(rangeSelector);
		
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
        
//        timeHumidityList.forEach(h->{
//        	DecimalFormat dFormat= new DecimalFormat("##.#");
//			Double humDobule2Decimal = new Double(dFormat.format(h.getHumidity()));        	
//        	DataSeriesItem item = new DataSeriesItem(h.getTime(),humDobule2Decimal);
//        	series.add(item);
//        	
//        });
        
		
		conf.addSeries(series);
		
		
	}

	private void createHumidityData(long t, int maxReading) {
		
		timeHumidityList = new ArrayList<>();
		
//		for(int i=0;i<maxReading;i++) {
//			
//			t=t+900000;//1000*60*15
//			
//			double humidity;
//			
//			while(true) {
//				
//				humidity = new Random().nextDouble()*100;
//				
//				if(humidity>40 && humidity<85) {
//					break;
//				}
//				
//			}
//
//			timeHumidityList.add(new HumidityEntity(t,humidity));
			
//		}
		
		
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
			.addColumn(x->x.getTemp(),new NumberRenderer(new DecimalFormat("##.## 'C'")))
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
		
      
        
        DataProviderSeries<TemperatureEntity> series = new DataProviderSeries<>(tempDataProvider,TemperatureEntity::getTemp);

		series.setName("Temperature");
		series.setX(TemperatureEntity::getTime);

		PlotOptionsLine line = new PlotOptionsLine();
	    line.setColor(SolidColor.BROWN);
	        	
		series.setPlotOptions(line);
		
		
		
		DatabaseReference reference = FirebaseConfiguration.getFirebaseDB().child("MyTemp");
		
		reference.limitToLast(20).addValueEventListener(this);
		
		
		
//		timeTemperatureList.forEach(x->{
//			
//			DecimalFormat dFormat= new DecimalFormat("##.##");
//			Double tempDobule2Decimal = new Double(dFormat.format(x.getTemp()));
//			DataSeriesItem item = new DataSeriesItem(x.getTime(), tempDobule2Decimal);
//			series.add(item);
//		});
//		
	    
	    conf.addSeries(series);

	}

	private void createTimeTempeartureData(long t, int maxReading) {
		
		timeTemperatureList = new ArrayList<>();

//		for(int i=0;i<maxReading;i++) {
//			
//			t=t+900000;//1000*60*15
//			
//			timeTemperatureList.add(new TemperatureEntity(t,new Random().nextDouble()*50));
//			
//		}
	}

	private void createCurrentTemperatureChart() {
		
		currentTemperatureChart = new Chart(ChartType.GAUGE);
		
		List<Double> temperatureList = new ArrayList<>(); 
		
		temperatureList.add(33.33);
		
		DataProvider<Double,?> temDataProvider = new ListDataProvider<>(temperatureList);
		
		DataProviderSeries<Double> series = new DataProviderSeries<>(temDataProvider,Double::doubleValue);
		
		Configuration conf = currentTemperatureChart.getConfiguration();
		conf.setTitle(StringHelper.CURRENT_TEMPERATURE);
		conf.getPane().setStartAngle(-135);
		conf.getPane().setEndAngle(135);
		
		YAxis yaxis = new YAxis();
		yaxis.setTitle(DEGREE+"C");

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
		
		
		DatabaseReference temRef = FirebaseConfiguration.getFirebaseDB().child("MyTemp");
    	
    	temRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot tempSnapShots) {
				
				 for (DataSnapshot tempSnapShot: tempSnapShots.getChildren()) {
			            String temperature = (String) tempSnapShot.child("temperature").getValue();
			            String humidity = (String) tempSnapShot.child("humidity").getValue();
			            long time = (long) tempSnapShot.child("time").getValue();
//			            System.out.println("Temperature "+temperature+" Humidity "+humidity+" Time "+new Date(time));
			            temperatureList.removeAll(temperatureList);
			            temperatureList.add(new Double(temperature));
			            
				 }
				 
				 /*This does not update until I touch the grid, My Guess is it cannot find which View to match to.
				  * 
				  * UI.getCurrent().access(new Runnable(){
				  * 
				  * @Override
					public void run() {
						
						temDataProvider.refreshAll();
						
					}
				  * 
				  * }
				  */
				 
			
				//This works to update the UI.
				getUI().access(new Runnable() {
					
					@Override
					public void run() {
						
						temDataProvider.refreshAll();
						
					}
				});
				 
			}
			
			@Override
			public void onCancelled(DatabaseError arg0) {
				
				
			}
		});
    	
		
		conf.addSeries(series);
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
			
//			System.out.println(tempHumEntity.getTemperature()+tempHumEntity.getHumidity()+ new Date(tempHumEntity.getTime()));
			
			TemperatureEntity temperatureEntity = 
					new TemperatureEntity(tempHumEntity.getTime(),Double.valueOf(tempHumEntity.getTemperature()));
			
			HumidityEntity humidityEntity =
					new HumidityEntity(tempHumEntity.getTime(),Double.valueOf(tempHumEntity.getHumidity()));
			
			timeTemperatureList.add(temperatureEntity);
			timeHumidityList.add(humidityEntity);
		
		}
		
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
