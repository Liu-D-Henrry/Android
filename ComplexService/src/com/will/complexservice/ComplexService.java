package com.will.complexservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.will.complexservice.IPet.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ComplexService extends Service {

	private PetBinder petBinder;
	private static Map<Person, List<Pet>> pets = new HashMap<Person, List<Pet>>();
	
	static {
		ArrayList<Pet> list1 = new ArrayList<Pet>();
		list1.add(new Pet("Rocket", 4.3));
		list1.add(new Pet("Bruce", 5.1));
		pets.put(new Person(1, "Will", "Will"), list1);
		ArrayList<Pet> list2 = new ArrayList<Pet>();
		list2.add(new Pet("Kitty", 2.3));
		list2.add(new Pet("Garfield", 3.1));
		pets.put(new Person(2, "Tom", "Tom"), list2);
	}
	
	public class PetBinder extends Stub {

		@Override
		public List<Pet> getPets(Person owner) throws RemoteException {
			// TODO Auto-generated method stub
			return pets.get(owner);
		}
		
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return petBinder;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		petBinder = new PetBinder();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
