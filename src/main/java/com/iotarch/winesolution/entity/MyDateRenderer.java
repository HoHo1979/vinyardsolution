package com.iotarch.winesolution.entity;


import com.vaadin.shared.ui.Connect;
import com.vaadin.ui.renderers.AbstractRenderer;
import com.vaadin.client.connectors.AbstractRendererConnector;

public class MyDateRenderer extends AbstractRenderer<Long, String>{

	

	protected MyDateRenderer(Class<String> presentationType) {
		super(presentationType);
	
		
	}
	
	
	

	@Connect(com.iotarch.winesolution.entity.MyDateRenderer.class)
	public static class MyDataRendererConnector extends AbstractRendererConnector<String>{
		
		
		
	}
	
}
