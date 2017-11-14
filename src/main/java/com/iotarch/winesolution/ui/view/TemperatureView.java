package com.iotarch.winesolution.ui.view;

import org.vaadin.viritin.label.MLabel;

import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TemperatureView extends VerticalLayout implements View {
	
	public static final String NAME="temperature";
	
	public TemperatureView() {
	
		MLabel lable = new MLabel("This is the Lable")
				.withStyleName(ValoTheme.LABEL_BOLD,ValoTheme.LABEL_H1);
		
		addComponent(lable);
		
	}

}
