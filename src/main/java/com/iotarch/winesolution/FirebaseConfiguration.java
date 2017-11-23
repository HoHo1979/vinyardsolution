package com.iotarch.winesolution;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.api.services.storage.Storage.BucketAccessControls.Get;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vaadin.server.VaadinService;

public class FirebaseConfiguration {
	
	private static FirebaseApp app;
	
	
	public FirebaseConfiguration() throws IOException {
		
		if(app!=null) {
			return;
		}
		
		String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();
		
		FileInputStream serviceAccount = new FileInputStream(basepath+"/WEB-INF/firebase/firebase-admin-temperatureApp.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .setDatabaseUrl("https://temperatureapp-5d5b2.firebaseio.com/")
		  .build();

		app=FirebaseApp.initializeApp(options);
	
	}
	
	
	public static DatabaseReference getFirebaseDB() {
		
		return FirebaseDatabase.getInstance(app).getReference();
	}

	
	
}
