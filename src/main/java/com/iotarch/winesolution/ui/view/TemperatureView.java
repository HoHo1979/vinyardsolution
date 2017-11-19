package com.iotarch.winesolution.ui.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.iotarch.winesolution.entity.HumidityEntity;
import com.iotarch.winesolution.entity.TemperatureEntity;
import com.iotarch.winesolution.helper.StringHelper;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.DateTimeLabelFormats;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotLine;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.RangeSelector;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.board.Board;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

public class TemperatureView extends VerticalLayout implements View{
	
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
	
	public TemperatureView() {
		
		long time = new Date().getTime();
		
		createTimeTempeartureData(time,maxReading);
		
		createHumidityData(time,maxReading);
		
		createBoard();
		
		createCurrentTemperatureChart();
		
		createTemperatureChart();
		
		createHumidityChart();
		
		createTemperatureGrid();
		
		board.addRow(currentTemperatureChart,temperatureChart);
		
		board.addRow(temperatureGrid);

		addComponent(board);
	}

	private void createHumidityChart() {
		
		Configuration conf=temperatureChart.getConfiguration();
		
		YAxis y2 = new YAxis();
        AxisTitle title = new AxisTitle("Humidity");
        Style style = new Style();
        style.setColor(SolidColor.BLUEVIOLET);
        y2.setTitle(title);
        Labels labels = new Labels();
        labels.setFormatter("this.value +' %'");
        style = new Style();
        style.setColor(SolidColor.PURPLE);
        labels.setStyle(style);
        y2.setLabels(labels);
        conf.addyAxis(y2);
		
		
        DataSeries series = new DataSeries();
        
        series.setName("Humidity");
        
        
        timeHumidityList.forEach(h->{
        	
        	DataSeriesItem item = new DataSeriesItem(h.getTime(), h.getHumidity());
        	series.add(item);
        	
        });
        
        //With this PlotOptionsColumn, the code will not working and error at run time.
//		PlotOptionsColumn column = new PlotOptionsColumn();
//		column.addColor(SolidColor.BLUEVIOLET);
//		column.setPointWidth(10);
		
		
//		series.setPlotOptions(column);
		
		conf.addSeries(series);
		
		
	}

	private void createHumidityData(long t, int maxReading) {
		
		timeHumidityList = new ArrayList<>();
		
		for(int i=0;i<maxReading;i++) {
			
			t=t+900000;//1000*60*15
			
			double humidity;
			
			while(true) {
				
				humidity = new Random().nextDouble()*100;
				
				if(humidity>40 && humidity<85) {
					break;
				}
				
			}

			timeHumidityList.add(new HumidityEntity(t,humidity));
			
		}
		
		
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
		
			
		ListDataProvider<TemperatureEntity> tempDataProvider = new ListDataProvider<>(timeTemperatureList);
		
		temperatureGrid.setDataProvider(tempDataProvider);
	
		
		
	}

	private void createTemperatureChart() {
		
		temperatureChart = new Chart();

		temperatureChart.setTimeline(true);
		
		Configuration conf = temperatureChart.getConfiguration();
		conf.setTitle(StringHelper.TEMP);
		conf.setSubTitle(StringHelper.TEMPERATURE_CHART_SUBTITLE);
		conf.getNavigator().setEnabled(false);
		
	//	conf.getTooltip().setFormatter(_fn_formatter);
		

		conf.getxAxis().setType(AxisType.DATETIME);
		
//		Tooltip tooltip = new Tooltip();
//		String dateTimeFormat = new DateTimeLabelFormats().getHour();
//		tooltip.setXDateFormat(dateTimeFormat);
//		tooltip.setPointFormat("<span style=\"color:{series.color}\">{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
//		tooltip.setValueDecimals(2);
//        conf.setTooltip(tooltip);
		
		XAxis xAxis = new XAxis();	
//		xAxis.getLabels().setFormat("{value:%Y-%m-%d %H:%M}");
		xAxis.setDateTimeLabelFormats(
				new DateTimeLabelFormats("%e. %b", "%b"));
		conf.addxAxis(xAxis);	
	
		YAxis y = new YAxis();
		
		PlotLine plotLine = new PlotLine();
		plotLine.setWidth(2);
		plotLine.setColor(SolidColor.AQUA);		
		y.setPlotLines(plotLine);
        y.setMin(0);
        //Makes Error and won't display
//        Labels yLabels=y.getLabels();
//        yLabels.setFormat("{point.y:%2.2f}");
        
        
//        conf
//        .getTooltip()
//        .setFormatter(
//                "'<b>'+ this.series.name +'</b><br/>\'+ this.x +': '+ this.y +' °C'");
        
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
	    
	    conf.addSeries(series);

	    temperatureChart.setConfiguration(conf);
	}

	private void createTimeTempeartureData(long t, int maxReading) {
		
		timeTemperatureList = new ArrayList<>();

		for(int i=0;i<maxReading;i++) {
			
			t=t+900000;//1000*60*15
			
			timeTemperatureList.add(new TemperatureEntity(t,new Random().nextDouble()*50));
			
		}
	}

	private void createCurrentTemperatureChart() {
		
		currentTemperatureChart = new Chart(ChartType.GAUGE);
		
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
		
		ListSeries series = new ListSeries("Temperature", 24);
		conf.addSeries(series);
	}

}
