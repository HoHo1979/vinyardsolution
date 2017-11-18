package com.iotarch.winesolution.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SoilMoistureView extends VerticalLayout implements View {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6094238691037619821L;
	
	
	public static final String NAME="soilMoistureView";

	public SoilMoistureView() {
		addComponent(new Label("This is Soil Moisture View"));
	}


}
