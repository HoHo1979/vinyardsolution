package com.iotarch.winesolution.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HumidityView extends VerticalLayout implements View {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025736417349283883L;
	
	public static final String NAME="humidityView";

	public HumidityView() {
		addComponent(new Label("This is Humidity View"));
	}


}
