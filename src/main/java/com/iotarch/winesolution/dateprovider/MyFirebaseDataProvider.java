package com.iotarch.winesolution.dateprovider;

import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iotarch.winesolution.entity.AbstractFirebaseEntity;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.Registration;

public class MyFirebaseDataProvider<T extends AbstractFirebaseEntity> extends AbstractDataProvider<T, SerializablePredicate<T>> implements ValueEventListener{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1981377433979445413L;
	
	private LinkedHashMap<String, T> data = new LinkedHashMap<>();
	private CopyOnWriteArrayList<T> tArrayList = new CopyOnWriteArrayList<>();
	DatabaseReference reference;
	Class<T> type;
	AtomicInteger registeredListeners = new AtomicInteger(0);
	

	public MyFirebaseDataProvider(DatabaseReference reference, Class<T> type) {
		this.reference = reference;
		this.type = type;
	}

	@Override
	public void onCancelled(DatabaseError arg0) {


		
	}

//	@Override
//	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
//		T added = snapshot.getValue(type);
//        String key = snapshot.getKey();
//        added.setKey(key);
//
//        data.put(key, added);
//        refreshAll();
//		
//	}
//
//	@Override
//	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
//	
//		 T updated = snapshot.getValue(type);
//	     String key = snapshot.getKey();
//	     updated.setKey(key);
//
//	     data.put(key, updated);
//	     refreshItem(updated);
//		
//	}
//
//	@Override
//	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
//		
//		
//	}
//
//	@Override
//	public void onChildRemoved(DataSnapshot snapshot) {
//	        data.remove(snapshot.getKey());
//	        refreshAll();
//	}

	@Override
	public boolean isInMemory() {
		
		return false;
	}

	@Override
	public int size(Query<T, SerializablePredicate<T>> query) {
		return data.size();
	}

	@Override
	public Stream<T> fetch(Query<T, SerializablePredicate<T>> query) {
		return data.values().stream();
	}

	@Override
	public Object getId(T item) {
		return item.getKey();
	}

	@Override
	public Registration addDataProviderListener(DataProviderListener<T> listener) {
		if (registeredListeners.incrementAndGet() == 1) {
            registerFirebaseListener();
        }
        Registration realRegistration = super.addDataProviderListener(listener);
        return () -> {
            realRegistration.remove();
            if (registeredListeners.decrementAndGet() == 0) {
                unregisterFirebaseListener();
            }
        };
	}

	private void unregisterFirebaseListener() {
		 reference.removeEventListener(this);
	}

	private void registerFirebaseListener() {
		
		 reference.addValueEventListener(this);
	}

	@Override
	public void onDataChange(DataSnapshot dataSnapshot) {
		
		dataSnapshot.getChildren().forEach(x->{
			String key=x.getKey();
			T added = x.getValue(type);
			added.setKey(key);
			
			tArrayList.add(added);
			
		});
		
		for(T t:tArrayList) {
		
			data.put(t.getKey(),t);
		
		}
		
		refreshAll();
	}

	
	
}
