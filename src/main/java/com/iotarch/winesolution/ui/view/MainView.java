package com.iotarch.winesolution.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 657279728047498702L;
	public static final String NAME="";

	public MainView() {
		addComponent(new Label("This is Main View"));
	}


}
