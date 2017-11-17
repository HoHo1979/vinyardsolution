package com.iotarch.winesolution.ui.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.iotarch.winesolution.entity.Temperature;
import com.iotarch.winesolution.helper.StringHelper;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.DateTimeLabelFormats;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.RangeSelector;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.board.Board;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

public class TemperatureView extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME="temperature";
	final String DEGREE  = "\u00b0";
	Chart temperatureChart;
	Chart humidityChart;
	Board board;
	Grid<Temperature> temperatureGrid;
	List<Temperature> timeTemperatureList;
	
	public TemperatureView() {
		
		createTimeTempeartureData();
		
		createBoard();
		
		createTemperatureChart();
		
		createHumidityChart();
		
		createTemperatureGrid();
		
		CssLayout cssLayout = new CssLayout(temperatureChart);
		CssLayout humLayout = new CssLayout(humidityChart);
		
		board.addRow(cssLayout,humLayout);
		board.addRow(temperatureGrid);
		
		addComponent(board);

		
	}

	private void createBoard() {
		
		board = new Board();
		board.setWidth("100%");
		board.setHeightUndefined();
		
	}

	private void createTemperatureGrid() {
		
		temperatureGrid = new Grid<Temperature>();
		
		
		temperatureGrid.addColumn(x->x.getDate());
		temperatureGrid.addColumn(x->x.getTemp(),new NumberRenderer(new DecimalFormat("##.## 'C'")));
		
		
		ListDataProvider<Temperature> tempDataProvider = new ListDataProvider<>(timeTemperatureList);
		
		temperatureGrid.setDataProvider(tempDataProvider);
	
		
		
		
	}

	private void createHumidityChart() {
		
		humidityChart = new Chart();

		humidityChart.setTimeline(true);
		
		Configuration conf = humidityChart.getConfiguration();
		conf.setTitle("Humidity");
		conf.setSubTitle("Humidity in a Day/15 mins");
		conf.getNavigator().setEnabled(false);
		
		conf.getxAxis().setType(AxisType.DATETIME);
		
		Tooltip tooltip = new Tooltip();
		DateTimeLabelFormats formats = new DateTimeLabelFormats();
		tooltip.setXDateFormat(formats.getHour());
        conf.setTooltip(tooltip);
		
		XAxis xAxis = new XAxis();	
		conf.addxAxis(xAxis);	
		
		
		YAxis y = new YAxis();
        y.setMin(0);
        
        conf.addyAxis(y);
		

		DataSeries series = new DataSeries();
		
		timeTemperatureList.forEach(x->{
			
			DecimalFormat dFormat= new DecimalFormat("##.##");
			Double tempDobule2Decimal = new Double(dFormat.format(x.getTemp()));
			DataSeriesItem item = new DataSeriesItem(x.getTime(), tempDobule2Decimal);
			series.add(item);
		});
		
		RangeSelector rangeSelector = new RangeSelector();
		rangeSelector.setSelected(1);
		conf.setRangeSelector(rangeSelector);
	    
	    conf.setSeries(series);

	    humidityChart.setConfiguration(conf);
	}

	private void createTimeTempeartureData() {
		timeTemperatureList = new ArrayList<>();
		
		long t = new Date().getTime();

		for(int i=0;i<10;i++) {
			
			t=t+900000;//1000*60*15
			
			timeTemperatureList.add(new Temperature(t,new Random().nextDouble()*50));
			
		}
	}

	private void createTemperatureChart() {
		
		temperatureChart = new Chart(ChartType.GAUGE);
		
		Configuration conf = temperatureChart.getConfiguration();
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
		
		ListSeries series = new ListSeries("Temperature", 24);
		conf.addSeries(series);
	}

}
