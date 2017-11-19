package com.iotarch.winesolution.ui.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.RangeSelector;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.board.Board;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

public class HumidityView extends VerticalLayout implements View {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025736417349283883L;
	
	public static final String NAME="humidityView";

	Board board;
	
	Chart humidityCart;
	
	
	public HumidityView() {
		
		createBoard();
		
		createChart();
		
		getHumidityDate();
		
		
		board.addRow(humidityCart);
		
		addComponent(board);
	}


	private void getHumidityDate() {
		
		
		
		
	}


	private void createChart() {
		
		humidityCart = new Chart(ChartType.CANDLESTICK);
		humidityCart.setTimeline(true);
		Configuration conf = humidityCart.getConfiguration();
		
		RangeSelector rangeSelector = new RangeSelector();
		rangeSelector.setSelected(1);
		conf.setRangeSelector(rangeSelector);

		XAxis xAxis = new XAxis();
		xAxis.setTitle("Time of the Day");
		
		YAxis yAxis = new YAxis();
		yAxis.setTitle("Humidity in Percnetage");
		
		conf.addxAxis(xAxis);
		conf.addyAxis(yAxis);
			
	}


	private void createBoard() {
		
		board = new Board();

	}


}
