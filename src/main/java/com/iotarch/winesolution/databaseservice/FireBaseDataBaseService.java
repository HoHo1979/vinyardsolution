package com.iotarch.winesolution.databaseservice;

import java.util.HashMap;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.iotarch.winesolution.entity.AbstractFirebaseEntity;

public class FireBaseDataBaseService<T extends AbstractFirebaseEntity> implements CompletionListener{
	
	DatabaseReference reference;
	AbstractFirebaseEntity entity;
	
	public FireBaseDataBaseService(DatabaseReference ref,T entity){
		
		this.entity=entity;
		this.reference= ref;
		
	}
	
	public String addEntity(String path) {
		
		String key=reference.child(path).push().getKey();
		reference.child(path).child(key).setValueAsync(entity);
		return key;
		
	}
	
	public void updateEntity(String path) {
		
		HashMap<String, Object> toUpdate = new HashMap<>();
	
		toUpdate.put(entity.getKey(), entity);
		
		reference.child(path).updateChildrenAsync(toUpdate);

	}
	
	public void deleteEntity(String path) {
		
		reference.child(path).child(entity.getKey()).removeValue(this);
		
	}
	

	@Override
	public void onComplete(DatabaseError error, DatabaseReference ref) {
		
		
	}

	
}
