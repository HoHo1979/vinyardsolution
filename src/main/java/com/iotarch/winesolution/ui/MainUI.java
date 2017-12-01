package com.iotarch.winesolution.ui;

import org.vaadin.sliderpanel.SliderPanel;
import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MainUI extends MainUIDesign {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974551182301633758L;
	
//	SliderPanel topSliderPanel;

	public MainUI() {
		
		
//		VerticalLayout verticalLayout = new VerticalLayout();
//		verticalLayout.addComponent(mainmenu);
//		verticalLayout.setWidth("400px");
//		verticalLayout.setHeightUndefined();
//		verticalLayout.setMargin(false);
		
//		topSliderPanel = new SliderPanelBuilder(verticalLayout)
//				.flowInContent(true)
//				.autoCollapseSlider(true)
//				.caption("Menu")
//				.mode(SliderMode.LEFT)
//				.tabPosition(SliderTabPosition.MIDDLE)
//				.fixedContentSize(150)
//				.build();
			
		menuToggle.addClickListener(this::menuToggleClicked);
		
//		addComponent(topSliderPanel,0);
			
	}
	
	public void menuToggleClicked(Event e) {
		
//		topSliderPanel.scheduleToggle(1000);
	
	}
	
	
	public Button getTemperatureButton(){
		return temperatureButton;
	}
	
	
	public Button getHumidityButton() {
		return humidityButton;
	}
	
	public Button getSoilMositureButton() {
		return moistureButton;
	}
	
	public Panel getContentPanel() {
		return contentPanel;
	}
	
	

}
