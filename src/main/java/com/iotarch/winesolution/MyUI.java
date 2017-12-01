package com.iotarch.winesolution;

import java.io.IOException;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iotarch.winesolution.ui.MainUI;
import com.iotarch.winesolution.ui.view.HumidityView;
import com.iotarch.winesolution.ui.view.MainView;
import com.iotarch.winesolution.ui.view.SoilMoistureView;
import com.iotarch.winesolution.ui.view.TemperatureView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
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
@Viewport("width=device-width")
@Push
public class MyUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5448376722040086504L;
	Navigator navigator;

	
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
   
    	try {
			FirebaseConfiguration firebaseConfiguration = new FirebaseConfiguration();
		} catch (IOException e1) {
	
			e1.printStackTrace();
		}

    	
    	MainUI ui = new MainUI();  
   
    	navigator = new Navigator(this,ui.getContentPanel());
    	navigator.addView(MainView.NAME,MainView.class);
    	navigator.addView(TemperatureView.NAME,TemperatureView.class);
    	navigator.addView(HumidityView.NAME, HumidityView.class);
    	navigator.addView(SoilMoistureView.NAME,SoilMoistureView.class);

    	ui.getTemperatureButton().addClickListener(e->navigator.navigateTo(TemperatureView.NAME));
    	ui.getSoilMositureButton().addClickListener(e->navigator.navigateTo(SoilMoistureView.NAME));
    	ui.getHumidityButton().addClickListener(e->navigator.navigateTo(HumidityView.NAME));
    	
        setContent(ui);
    }
    

    

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2570440240535445379L;
    }
    
    
    
    
}
