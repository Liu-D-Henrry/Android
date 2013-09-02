package com.will.demoaidlservice;

import java.util.Timer;
import java.util.TimerTask;

import com.will.demoaidlservice.ICat.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AidlService extends Service {

	private static final String TAG = "AidlService";
	
	private CatBinder catBinder;
	private Timer timer = new Timer();
	
	private String[] colors = new String[] {
		"Red",
		"Green",
		"Blue"
	};
	
	private double[] weights = new double[] {
		2.2,
		3.3,
		4.4
	};
	
	private String color;
	private double weight;
	
	public class CatBinder extends com.will.demoaidlservice.ICat.Stub {

		@Override
		public String getColor() throws RemoteException {
			// TODO Auto-generated method stub
			return color;
		}

		@Override
		public double getWeight() throws RemoteException {
			// TODO Auto-generated method stub
			return weight;
		}
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind");
		return catBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate");
		catBinder = new CatBinder();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int rand = (int) (Math.random() * 3);
				color = colors[rand];
				weight = weights[rand];
			}
			
		}, 0, 800);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		timer.cancel();
	}
	
	

}
