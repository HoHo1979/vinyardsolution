package com.iotarch.winesolution.ui.view;

import org.vaadin.viritin.button.MButton;

import com.iotarch.winesolution.helper.StringHelper;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainView extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 657279728047498702L;
	public static final String NAME="";
	
	MButton temperatureButton;
	MButton humidityButton;
	MButton soilButton;
	MButton reportButton;
	MButton settingButton;
	MButton logoutButton;

	public MainView() {
		
		Navigator navigator = UI.getCurrent().getNavigator();
		
		temperatureButton = new MButton(StringHelper.TEMPERATURE)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.SUN_O)
				.withWidth("100%")
				.withListener(e->navigator.navigateTo(TemperatureView.NAME));
		
		humidityButton = new MButton(StringHelper.HUMIDITY)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.CLOUD_O)
				.withWidth("100%")
				.withListener(e->navigator.navigateTo(HumidityView.NAME));
		
		soilButton = new MButton(StringHelper.SOIL_MOISTURE)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.FILL)
				.withWidth("100%")
				.withListener(e->navigator.navigateTo(SoilMoistureView.NAME));
		
		reportButton = new MButton(StringHelper.REPORT)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.BAR_CHART_H)
				.withEnabled(false)
				.withWidth("100%");
//				.withListener(e->navigator.navigateTo(SoilMoistureView.NAME));
		
		
		settingButton = new MButton(StringHelper.SETTING)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.COG_O)
				.withEnabled(false)
				.withWidth("100%");
//				.withListener(e->navigator.navigateTo(SoilMoistureView.NAME));
		
		logoutButton = new MButton(StringHelper.LOGOUT)
				.withStyleName(ValoTheme.BUTTON_HUGE)
				.withIcon(VaadinIcons.SIGN_OUT)
				.withEnabled(false)
				.withWidth("100%");
//				.withListener(e->navigator.navigateTo(SoilMoistureView.NAME));
		
		
		addComponents(temperatureButton,humidityButton,soilButton,reportButton,settingButton,logoutButton);
		
		setSpacing(true);
		
		
		
	}
	



}
