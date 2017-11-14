package com.iotarch.winesolution;

import javax.servlet.annotation.WebServlet;

import com.iotarch.winesolution.ui.MainUI;
import com.iotarch.winesolution.ui.view.TemperatureView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	Navigator navigator;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
    	
    	MainUI ui = new MainUI();
    	
    	navigator = new Navigator(this,ui.getContent());
    	navigator.addView(TemperatureView.NAME,TemperatureView.class);
    	navigator.navigateTo(TemperatureView.NAME);
    	
        setContent(ui);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
